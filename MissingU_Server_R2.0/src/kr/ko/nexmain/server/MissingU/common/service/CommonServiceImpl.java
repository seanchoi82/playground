package kr.ko.nexmain.server.MissingU.common.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.chat.dao.ChatDao;
import kr.ko.nexmain.server.MissingU.chat.model.ChatroomMember;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.Constants.ItemGroup;
import kr.ko.nexmain.server.MissingU.common.dao.CommonDao;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.msgbox.dao.MsgBoxDao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class CommonServiceImpl implements CommonService {

	protected static Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private FriendsDao friendsDao;
	@Autowired
	private MsgBoxDao msgBoxDao;
	@Autowired
	private ChatDao chatDao;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private MsgUtil msgUtil;
	
	private Locale gLocale;
	
	/** 포인트 정보 업데이트 */
	public boolean updatePointInfo(Integer memberId, String eventTypeCd, String usageCd, Integer usePoint, String gLang) {
		//포인트 사용내역 Insert
		Map<String,Object> memberMap = membershipDao.selectMemberAndPointInfoByMemberId(memberId);
		String	memberSex = (String)memberMap.get("sex");
		Long	memberPoint = (Long)memberMap.get("point");
		
		boolean	needToUpdate = true;
		
		if(Constants.EventTypeCd.INCOME.equalsIgnoreCase(eventTypeCd)) { //충전
			needToUpdate = true;
		} else { //사용
			if(Constants.SexCode.FEMALE.equalsIgnoreCase(memberSex)) {
				//여자는 무료(선물하기, 윙크 제외)
				if(ItemGroup.WINK.equalsIgnoreCase(usageCd) || ItemGroup.GIFT.equalsIgnoreCase(usageCd)) {
					needToUpdate = true;
				} else {
					needToUpdate = false;
				}
			} else { //남자
				if(hasFreePass(memberId)) {
					//정액권이 있는 경우
					needToUpdate = false;
				} else {
					needToUpdate = true;
				}
			}
		}
		
		
		if(needToUpdate) {
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put("memberId", memberId);
			inputMap.put("eventTypeCd", eventTypeCd);
			inputMap.put("usageCd", usageCd);
			inputMap.put("useDesc", msgUtil.getPropMsg(usageCd, new Locale(gLang)));
			inputMap.put("usePoint", usePoint);
			inputMap.put("remainPoint", memberPoint + usePoint);
			membershipDao.insertIntoPointUsageHist(inputMap);
			//잔여 포인트 업데이트
			Map<String,Object> winkItem = new HashMap<String,Object>();
			winkItem.put("receiverId", memberId);
			winkItem.put("itemCd", Constants.ItemCode.POINT);
			winkItem.put("itemAmount", usePoint);
			friendsDao.updateInventoryToIncreaseItemAmount(winkItem);	//인벤토리 업데이트 : 포인트 사용
		}
		
		return true;
	}
	
	/** 유효한 정액권 소지여부 체크 */
	public boolean hasFreePass(Integer memberId) {
		//정액권 만료일자 체크
		MemberAttr vo = new MemberAttr();
		vo.setMemberId(memberId);
		vo.setAttrName(Constants.MemberAttrName.PASS_EXPIRE_DAY);
		Map<String,Object> passExpireAttr = membershipDao.selectMemberAttrByMemberIdAndName(vo);
		String passExpireDay = (passExpireAttr == null) ? "" : passExpireAttr.get("attrValue") + "";
		
		boolean hasFreePass = false;
		String currentDay = UTL.getCurrentDate();
		log.info("currentDay : {}", currentDay);
		log.info("passExpireDay : {}", passExpireDay);
		if(StringUtils.isNotEmpty(currentDay) && StringUtils.isNotEmpty(passExpireDay)) {
			if(Integer.parseInt(currentDay) <= Integer.parseInt(passExpireDay)) {
				//정액권이 유효한 경우
				log.info("member has free-pass");
				hasFreePass = true;
			}
		}
		
		return hasFreePass;
	}
	
	/** 보유 포인트 및 필요 포인트 조회 */
	public Map<String,Object> getMemberPointAndNeedPoint(Map<String,Object> inputMap, String gLang) {
		gLocale = new Locale(gLang);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String	requestURI = (String) inputMap.get("requestURI");
		Integer	memberId   = (Integer) inputMap.get("memberId");
		
		Map<String,Object> member = membershipDao.selectMemberAndPointInfoByMemberId(memberId);
		String	memberSex  = (String) member.get("sex");
		int		pointNeed = 0;
		Long	memberPoint = (Long) member.get("point");
		
		if(hasFreePass(memberId)) {
			pointNeed = 0;
		} else {
			if(Constants.SexCode.FEMALE.equalsIgnoreCase(memberSex)) {
				//여자는 무료
				pointNeed = 0;
			} else {
				if(Constants.RequestURI.CREATE_ROOM.equals(requestURI)) {				//채팅방 개설
					pointNeed = 100;
				} else if (Constants.RequestURI.ROOM_IN.equals(requestURI)) {			//채팅방 입장

					// 이미 입장되어 있는 경우 포인트 소진에서 제외처리
					try { 
						Integer roomId = Integer.parseInt((String)inputMap.get("roomId"));
						List<ChatroomMember> members = chatDao.selectRoomMemberListByRoomId(roomId);
						boolean exists = false;
						for(ChatroomMember chat : members)  {
							// 입장상태 또는 재입장인 경우 무료, Reject은 나중에 체크
							if((chat.getStatus().equals("A") || chat.getStatus().equals("E") || chat.getStatus().equals("R")) && chat.getMemberId().compareTo(memberId) ==0) {
								exists = true;
								break;
							}
						}
						
						// 에러 없이 재입장이 아닌 경우만 포인트 소진
						if(!exists)
							pointNeed = 100;
						
					}catch(Exception e) {
						pointNeed = 100;
					}
					
				} else if (Constants.RequestURI.SEND_MSG.equals(requestURI)) {			//쪽지 보내기
					pointNeed = 100;
				} else if (Constants.RequestURI.OPEN_MSG.equals(requestURI)) {			//쪽지 개봉
					//받은쪽지 최초 개봉 시만 포인트 차감
					if(inputMap.get("msgId") != null) {
						Long msgId = Long.parseLong((String)inputMap.get("msgId"));
						Map<String,Object> msgObj = msgBoxDao.selectMsgByMsgId(msgId);
						log.info("msgId : {}", msgId);
						if(msgObj != null) {
							Integer receiverId = (Integer)msgObj.get("receiverId");
							String	receiverReadYn = String.valueOf(msgObj.get("receiverReadYn"));
							
							if( (memberId == receiverId) && (!Constants.YES.equalsIgnoreCase(receiverReadYn)) ) {
								pointNeed = 300;
							}
						}
					} else {
						pointNeed = 300;
					}
					
				} else if (Constants.RequestURI.SAVE_TALK.equals(requestURI)) {			//톡투미 글쓰기
					pointNeed = 100;
				} else if (Constants.RequestURI.SAVE_TALK_REPLY.equals(requestURI)) {	//톡투미 댓글쓰기
					pointNeed = 100;
				}
			}
		}
		
		//윙크, 선물하기는 정액권 소지여부와 상관 없음
		if (Constants.RequestURI.SEND_WINK.equals(requestURI)) {			//윙크 보내기
			pointNeed = 300;
		} else if (Constants.RequestURI.SEND_GIFT.equals(requestURI)){		//선물 보내기
			pointNeed = 1000;
		}
		
		returnMap.put("pointNeed", pointNeed);
		returnMap.put("memberPoint", memberPoint);
		
		//리턴메세지 생성
		if(pointNeed > 0) {
			if(memberPoint < pointNeed) {
				//포인트가 부족한 경우
				Result result = new Result(
						Constants.ReturnCode.AUTH_ERROR,
						msgUtil.getMsgCd("comm.authError.lackOfPoint", gLocale),
						msgUtil.getMsgText("comm.authError.lackOfPoint", gLocale));
				returnMap.put("result", result);
			} else {
				StringBuilder sbMsg = new StringBuilder();
				sbMsg.append(msgUtil.getMsgText("comm.authError.needConfirm.prefix", gLocale));
				sbMsg.append(memberPoint);
				sbMsg.append(" | ");
				sbMsg.append(msgUtil.getMsgText("comm.authError.needConfirm.middle", gLocale));
				sbMsg.append(pointNeed);
				sbMsg.append("\n");
				sbMsg.append(msgUtil.getMsgText("comm.authError.needConfirm.suffix", gLocale));
				
				Result result = new Result(
						Constants.ReturnCode.AUTH_ERROR,
						msgUtil.getMsgCd("comm.authError.needConfirm", gLocale),
						sbMsg.toString());
				returnMap.put("result", result);
			}
		} else {
			//포인트가 면제되는 경우(정액권 소유 등)
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 서비스 접근 로그 저장 */
	public boolean saveServiceAccessLog(Map<String,Object> inputMap) {
		boolean resultFlag = true;
		
		commonDao.insertServiceAccessLog(inputMap);
		
		return resultFlag;
	}

}
