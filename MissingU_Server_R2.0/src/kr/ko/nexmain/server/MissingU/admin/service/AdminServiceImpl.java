package kr.ko.nexmain.server.MissingU.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.admin.dao.AdminDao;
import kr.ko.nexmain.server.MissingU.admin.model.ApkItem;
import kr.ko.nexmain.server.MissingU.admin.model.MsgDeleteMemberPack;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.BaseService;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.membership.model.MemberRegisterReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.SaveMemberPhotoReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateMemberReqVO;
import kr.ko.nexmain.server.MissingU.membership.service.MembershipService;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.talktome.dao.TalktomeDao;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

@Service
@Transactional
public class AdminServiceImpl extends BaseService implements AdminService {
	
	protected static Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private TalktomeDao talktomeDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MembershipService membershipService;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private MsgUtil msgUtil;
	
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	/** 쪽지함 목록 */
	@Override
	public Map<String, Object> getMsgList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		List<Map<String,Object>> dataList = adminDao.selectMsgList(params);
		Integer totalCnt = adminDao.selectMsgListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	/** 쪽지 대화 */
	@Override
	public Map<String, Object> getMsgConversations(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
				
		List<Map<String,Object>> dataList = adminDao.selectMsgConversation(params);
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	/** 읽음 여부 갱신 */
	@Override
	public int updateReadYn(long msgId) {
		return adminDao.updateReadYn(msgId);
	}

	/** 메세지 삭제 */
	@Override
	public int deleteMsg(long msgId) {
		return adminDao.deleteMsg(msgId);
	}

	/** 메세지 삭제 (대화 통째로 삭제) */
	@Override
	public Map<String,Object> deleteMsgGroup(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String[] members = (String[])params.get("members");
		
		Result result = null;
		if(members != null) {
			
			for(String member : members) {
				
				String[] splits = member.split("[:]");
				MsgDeleteMemberPack pack = new MsgDeleteMemberPack();
				pack.setReceiverId(splits[0]);
				pack.setSenderId(splits[1]);
				
				adminDao.deleteMsgGroup(pack);
			}
			
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 댓글번호 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	
	public void addApkItem(ApkItem apkItem) {
		this.adminDao.addApkItem(apkItem);
	}
	
	public void deleteApkItem(int apkId) {
		this.adminDao.deleteApkItem(apkId);
	}
	
	public List<ApkItem> getApkItemList() {
		return this.adminDao.getApkItem();
	}
	
	/** 페이스매치 조회*/
	public Map<String,Object> getFmList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> fmList = adminDao.selectFmList(params);
		
		responseMap.put("fmList", fmList);
		
		if(fmList != null && fmList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 페이스매치 조회 Count*/
	public Map<String,Object> getFmListCnt(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer totalCnt = adminDao.selectFmListCnt(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(totalCnt != null && totalCnt > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 회원목록 조회*/
	public Map<String,Object> getMemberListNew(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
		
		List<Map<String,Object>> dataList = adminDao.selectMemberListNew(params);
		responseMap.put("dataList", dataList);
		
		Integer totalCnt = adminDao.selectMemberListCntNew(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(dataList != null) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 회원목록 조회*/
	public Map<String,Object> getMemberList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> memberList = adminDao.selectMemberList(params);
		
		responseMap.put("memberList", memberList);
		
		if(memberList != null && memberList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 회원목록 조회 Count*/
	public Map<String,Object> getMemberListCnt(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer totalCnt = adminDao.selectMemberListCnt(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(totalCnt != null && totalCnt > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 톡투미 목록 조회*/
	public Map<String,Object> getTalkToMeList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> talkToMeList = adminDao.selectTalkToMeList(params);
		Integer totalCnt = adminDao.selectTalkToMeListCnt(params);
		
		responseMap.put("totalCnt", totalCnt);
		responseMap.put("talkToMeList", talkToMeList);
		
		
		if(talkToMeList != null && talkToMeList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 톡투미 삭제 */
	public Map<String,Object> deleteTalkToMe(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer talkId = (params.get("talkId") == null || "".equals((String)params.get("talkId"))) 
				? null : Integer.parseInt((String)params.get("talkId"));
		
		Result result = null;
		if(talkId != null) {
			adminDao.deleteTalkToMe(params);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 공지사항ID 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	/** 톡투미 삭제 */
	public Map<String,Object> doDelTalkReply(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer[] replyIds = UTL.explodeInteger(params.get("replyIds") + "", ",");
		
		System.out.println("--------------------------------------------");
		System.out.println(replyIds);
		System.out.println(replyIds.length);
		System.out.println("--------------------------------------------");
		
		Result result = null;
		if(replyIds != null) {
			adminDao.deleteTalkToMeReply(replyIds);
			
			// 댓글수 동기화
			SaveTalkReplyReqVO inputVO = new SaveTalkReplyReqVO();
			inputVO.setTalkId(Integer.parseInt(params.get("talkId") + ""));
			talktomeDao.updateTalktomeReplyCntSync(inputVO);
			
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 댓글번호 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 톡투미 삭제 */
	public Map<String,Object> doDelTalkArr(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String[] talkIds = (String[])params.get("talkIds");
		
		Result result = null;
		if(talkIds != null) {
			adminDao.deleteTalkToMeArr(talkIds);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 댓글번호 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 결제내역 조회*/
	public Map<String,Object> getPayHistList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> payHistList = adminDao.selectPayHistList(params);
		responseMap.put("payHistList", payHistList);
		
		if(payHistList != null && payHistList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 결제내역 조회 Count */
	public Map<String,Object> getPayHistListCnt(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer totalCnt = adminDao.selectPayHistListCnt(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(totalCnt != null && totalCnt > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 포인트사용 내역 조회*/
	public Map<String,Object> getPointUseHistList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> pointUseHistList = adminDao.selectPointUseHistList(params);
		responseMap.put("pointUseHistList", pointUseHistList);
		
		if(pointUseHistList != null && pointUseHistList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 포인트사용 내역 조회 Count */
	public Map<String,Object> getPointUseHistListCnt(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer totalCnt = adminDao.selectPointUseHistListCnt(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(totalCnt != null && totalCnt > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 공지사항 목록 조회*/
	public Map<String,Object> getNoticeList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> noticeList = adminDao.selectNoticeList(params);
		responseMap.put("noticeList", noticeList);
		
		if(noticeList != null && noticeList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 공지사항 목록 조회 Count */
	public Map<String,Object> getNoticeListCnt(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer totalCnt = adminDao.selectNoticeListCnt(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(totalCnt != null && totalCnt > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 공지사항 저장 */
	public Map<String,Object> saveNotice(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer noticeId = (params.get("noticeId") == null || "".equals((String)params.get("noticeId"))) 
				? null : Integer.parseInt((String)params.get("noticeId"));
		
		if(noticeId == null) {
			Integer createdNoticeId = adminDao.insertNotice(params);
			returnMap.put("noticeId", createdNoticeId);
		} else {
			adminDao.updateNotice(params);
		}
		
		//성공
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.update"),
				msgUtil.getMsgText("comm.success.update"));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 공지사항 삭제 */
	public Map<String,Object> deleteNotice(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer noticeId = (params.get("noticeId") == null || "".equals((String)params.get("noticeId"))) 
				? null : Integer.parseInt((String)params.get("noticeId"));
		
		Result result = null;
		if(noticeId != null) {
			adminDao.deleteNotice(params);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 공지사항ID 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 사용자가이드 목록 조회*/
	public Map<String,Object> getGuideList(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		long pageSize = Long.parseLong((String)params.get("pageSize"));
		long startRow = Long.parseLong((String)params.get("startRow"));
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		
		List<Map<String,Object>> guideList = adminDao.selectGuideList(params);
		responseMap.put("guideList", guideList);
		
		if(guideList != null && guideList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 사용자가이드 목록 조회 Count */
	public Map<String,Object> getGuideListCnt(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer totalCnt = adminDao.selectGuideListCnt(params);
		responseMap.put("totalCnt", totalCnt);
		
		if(totalCnt != null && totalCnt > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 사용자가이드 저장 */
	public Map<String,Object> saveGuide(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String actionType = (String)params.get("actionType");
		
		if("add".equals(actionType)) {
			adminDao.insertGuide(params);
			returnMap.put("actionType", actionType);
		} else {
			adminDao.updateGuide(params);
			returnMap.put("actionType", actionType);
		}
		
		//성공
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.update"),
				msgUtil.getMsgText("comm.success.update"));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 사용자가이드 삭제 */
	public Map<String,Object> deleteGuide(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String menuId = (String)params.get("menuId");
		
		Result result = null;
		if(menuId != null) {
			adminDao.deleteGuide(params);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 메뉴ID 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 회원 저장 */
	public Map<String,Object> saveMember(Map<String,Object> params, MultipartFile uploadFile, HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer memberId = (params.get("memberId") == null || "".equals((String)params.get("memberId"))) 
				? null : Integer.parseInt((String)params.get("memberId"));
		
		if(memberId == null) {
			MemberRegisterReqVO vo1 = new MemberRegisterReqVO();
			vo1.setgMemberId(memberId);
			vo1.setgLang("ko");
			vo1.setMemberId(memberId);
			vo1.setStatus((String)params.get("status"));
			vo1.setLoginId((String)params.get("loginId"));
			vo1.setLoginPw((String)params.get("password"));
			vo1.setNickName((String)params.get("nickName"));
			vo1.setSex((String)params.get("sex"));
			vo1.setGcmUseYn((String)params.get("gcmUseYn"));
			vo1.setGcmRegId((String)params.get("gcmRegId"));
			vo1.setBirthDate((String)params.get("birthDate"));
			vo1.setLunarSolarCd((String)params.get("lunarSolarCd"));
			vo1.setBloodTypeCd((String)params.get("bloodTypeCd"));
			vo1.setBirthTime((String)params.get("birthTime"));
			vo1.setAppearanceTypeCd((String)params.get("appearanceTypeCd"));
			vo1.setBodyTypeCd((String)params.get("bodyTypeCd"));
			vo1.setPurposeCd((String)params.get("purposeCd"));
			vo1.setHobbyCd((String)params.get("hobbyCd"));
			vo1.setDrinkingHabitCd((String)params.get("drinkingHabitCd"));
			vo1.setSmokingHabitCd((String)params.get("smokingHabitCd"));
			vo1.setSelfPr((String)params.get("selfPr"));
			vo1.setgCountry((String)params.get("country"));
			vo1.setgPosX((String)params.get("gPosX"));
			vo1.setgPosY((String)params.get("gPosY"));
			vo1.setAreaCd((String)params.get("areaCd"));
			vo1.setLocation((String)params.get("location"));
			
			returnMap = membershipService.doCreateMemeber(vo1);
			
			//본인인증 여부 업데이트
			if( params.get("oneselfCertYn") != null && !"".equals((String)params.get("oneselfCertYn")) ) {
				
				Map<String,Object> innerMap = (Map<String,Object>)returnMap.get("response");
				memberId = Integer.parseInt(innerMap.get("memberId") + "");
				
				MemberAttr attr = new MemberAttr();
				attr.setMemberId(memberId);
				attr.setAttrName(Constants.MemberAttrName.ONESELF_CERTIFICATION);
				attr.setAttrValue((String)params.get("oneselfCertYn"));
				membershipDao.updateMemberAttr(attr);
			}
			
			
		} else {
			UpdateMemberReqVO vo1 = new UpdateMemberReqVO();
			vo1.setgMemberId(memberId);
			vo1.setgLang("ko");
			vo1.setMemberId(memberId);
			vo1.setStatus((String)params.get("status"));
			vo1.setLoginId((String)params.get("loginId"));
			vo1.setLoginPw((String)params.get("password"));
			vo1.setNickName((String)params.get("nickName"));
			vo1.setSex((String)params.get("sex"));
			vo1.setGcmUseYn((String)params.get("gcmUseYn"));
			vo1.setGcmRegId((String)params.get("gcmRegId"));
			vo1.setBirthDate((String)params.get("birthDate"));
			vo1.setLunarSolarCd((String)params.get("lunarSolarCd"));
			vo1.setBloodTypeCd((String)params.get("bloodTypeCd"));
			vo1.setBirthTime((String)params.get("birthTime"));
			vo1.setAppearanceTypeCd((String)params.get("appearanceTypeCd"));
			vo1.setBodyTypeCd((String)params.get("bodyTypeCd"));
			vo1.setPurposeCd((String)params.get("purposeCd"));
			vo1.setHobbyCd((String)params.get("hobbyCd"));
			vo1.setDrinkingHabitCd((String)params.get("drinkingHabitCd"));
			vo1.setSmokingHabitCd((String)params.get("smokingHabitCd"));
			vo1.setSelfPr((String)params.get("selfPr"));
			vo1.setgCountry((String)params.get("country"));
			vo1.setgPosX((String)params.get("gPosX"));
			vo1.setgPosY((String)params.get("gPosY"));
			vo1.setAreaCd((String)params.get("areaCd"));
			vo1.setLocation((String)params.get("location"));
			
			returnMap = membershipService.doUpdateMemeber(vo1);
			
			//본인인증 여부 업데이트
			if( params.get("oneselfCertYn") != null && !"".equals((String)params.get("oneselfCertYn")) ) {
				MemberAttr attr = new MemberAttr();
				attr.setMemberId(memberId);
				attr.setAttrName(Constants.MemberAttrName.ONESELF_CERTIFICATION);
				attr.setAttrValue((String)params.get("oneselfCertYn"));
				membershipDao.updateMemberAttr(attr);
			}
		}
		
		//사진요청 존재시 저장
		if(uploadFile != null) {
			SaveMemberPhotoReqVO inputVO2 = new SaveMemberPhotoReqVO();
			inputVO2.setgMemberId(Integer.parseInt((String)params.get("memberId")));
			inputVO2.setgLang("ko");
			inputVO2.setFileUsageType(Constants.UploadFileUsageType.MAIN_PHOTO);
			inputVO2.setUploadFile(uploadFile);
			returnMap = membershipService.doSaveMemberPhoto(inputVO2, request);
		} else {
			log.info("No file to upload.");
		}
		
		return returnMap;
	}
	
	/** 포인트 부여 */
	public Map<String,Object> givePoint(Map<String,Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer memberId = (params.get("pop_memberId") == null || "".equals((String)params.get("pop_memberId"))) 
				? null : Integer.parseInt((String)params.get("pop_memberId"));
		
		Integer giftPoint = (params.get("giftPoint") == null || "".equals((String)params.get("giftPoint"))) 
				? null : Integer.parseInt((String)params.get("giftPoint"));
		
		Map<String, Object> member = membershipDao.selectSimpleMemberInfoByMemberId(memberId);
		String lang = ((member.containsKey("lang")) ? (String)member.get("lang") : "ko");
		
		//포인트 부여
		boolean rsltFlag = commonService.updatePointInfo(memberId, Constants.EventTypeCd.INCOME, "I003", giftPoint, lang);
		
		Result result = null;
		if(rsltFlag) {
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"포인트가 부여되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"실패");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
/*
	public void deleteItem(Item item) {
		this.itemDao.delete(item);
	}

	public void entryItem(Item item) {
		this.itemDao.create(item);
	}

	public Item getItemByItemId(Integer itemId) {
		return this.itemDao.findByPrimaryKey(itemId);
	}

	public List<Item> getItemByItemName(String itemName) {
		return this.itemDao.findByItemName(itemName);
	}

	public List<Item> getItemList() {
		return this.itemDao.findAll();
	}

	public void updateItem(Item item) {
		this.itemDao.udpate(item);
	}

	public InputStream getPicture(Integer itemId) {
		return this.itemDao.getPicture(itemId);
	}
*/

	@Override
	public Map<String, Object> getMsgBoxList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		List<Map<String,Object>> messageBoxList = adminDao.selectMessageBoxList(params);
		Integer totalCnt = adminDao.selectMessageBoxListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("msgList", messageBoxList);
		
		
		if(messageBoxList != null && messageBoxList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> getMsgBoxConversationFriends(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		List<Map<String,Object>> messageBoxList = adminDao.selectMessageBoxConversationFriends(params);
		Integer totalCnt = adminDao.selectMessageBoxConversationFriendsCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("msgList", messageBoxList);
		
		
		if(messageBoxList != null && messageBoxList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	
	@Override
	public Map<String, Object> getMsgBoxConversationByFriendsId(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> messageBoxList = adminDao.getMsgBoxConversationByFriendsId(params);
		responseMap.put("msgList", messageBoxList);
		
		
		if(messageBoxList != null && messageBoxList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}


	/** 톡투미 삭제 */
	public Map<String,Object> doDelMsgArr(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String[] msgIds = (String[])params.get("msgIds");
		
		Result result = null;
		if(msgIds != null) {
			adminDao.deleteMsgArr(msgIds);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 댓글번호 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	
	
	/**
	 * 1:1 문의 목록
	 */
	@Override
	public Map<String, Object> getManToManQuestionsList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		List<Map<String,Object>> dataList = adminDao.selectManToManQuestionsList(params);
		Integer totalCnt = adminDao.selectManToManQuestionsListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 1:1 문의삭제 */
	public Map<String,Object> doDelManToManArr(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String[] mIds = (String[])params.get("mIds");
		
		Result result = null;
		if(mIds != null) {
			adminDao.deleteManToManArr(mIds);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 댓글번호 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 1:1 문의 관리자 저장 */
	public Map<String,Object> doUpdateManToMan(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Result result = null;
		if(adminDao.updateManToMan(params) > 0) {
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"저장 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"메모 저장에 실패하였습니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
		
	@Override
	public Map<String, Object> getMissionMatchList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		List<Map<String,Object>> dataList = adminDao.selectMissionMatchList(params);
		Integer totalCnt = adminDao.selectMissionMatchListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> saveMissionMatch(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String mId = (String)params.get("mId");
		
		if(StringUtils.isNullOrEmpty(mId)) {
			adminDao.insertMissionMatch(params);
		} else {
			adminDao.updateMissionMatch(params);
		}
		
		//성공
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.update"),
				msgUtil.getMsgText("comm.success.update"));
		returnMap.put("result", result);
		
		return returnMap;
	}

	@Override
	public Map<String, Object> deleteMissionMatch(Map<String, Object> params) {
Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer noticeId = (params.get("mId") == null || "".equals((String)params.get("mId"))) 
				? null : Integer.parseInt((String)params.get("mId"));
		
		Result result = null;
		if(noticeId != null) {
			adminDao.deleteMissionMatch(params);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 미션 ID 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> getMissionMatchStatus(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		List<Map<String,Object>> dataList = adminDao.selectMissionMatchStatus(params);
		Integer totalCnt = adminDao.selectMissionMatchStatusCnt(params);
		Map<String,Object> mission = adminDao.selectMissionMatch(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		responseMap.put("mission", mission);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> joinMissionMatchList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
				
		Map<String, Object> mission = adminDao.selectMissionMatch(params);
		List<Map<String,Object>> dataList = adminDao.selectJoinMissionMatchList(params);
		Integer totalCnt = adminDao.selectJoinMissionMatchListCnt(params);
		
		responseMap.put("mission", mission);
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> joinEditMissionMatch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> joinDeleteMissionMatch(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String[] mJIds = (String[])params.get("mJIds");
		
		Result result = null;
		if(mJIds != null) {
			adminDao.deleteJoinMissionMatch(mJIds);
			//성공
			result = new Result(
					Constants.ReturnCode.SUCCESS,
					"SS",
					"삭제 되었습니다.");
		} else {
			result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"잘못된 참여글번호 입니다.");
		}
		
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> saveJoinMissionMatch(MissionMatchJoinReqVO inputVO) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		if(inputVO.getmJId() == null || inputVO.getmJId().intValue() == 0) {
			inputVO.setmJId(adminDao.insertJoinMissionMatch(inputVO));
		} else {
			adminDao.updateJoinMissionMatch(inputVO);
		}
		
		//성공
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.update"),
				msgUtil.getMsgText("comm.success.update"));
		returnMap.put("result", result);
		returnMap.put("mId", inputVO.getmId());
		returnMap.put("mJId", inputVO.getmJId());
		
		return returnMap;
	}
	
	
	@Override
	public Map<String, Object> joinVoteMissionMatch(Map<String, Object> params) {
		
		
		
		// TODO Auto-generated method stub
		return null;
	}	
}
