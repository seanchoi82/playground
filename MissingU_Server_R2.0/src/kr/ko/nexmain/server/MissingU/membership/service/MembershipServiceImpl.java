package kr.ko.nexmain.server.MissingU.membership.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.mail.MuMailNotifier;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.MailSendParam;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.security.AES128Sync;
import kr.ko.nexmain.server.MissingU.common.security.AES256;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.Thumbnail;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.config.dao.ConfigDao;
import kr.ko.nexmain.server.MissingU.facematch.dao.FaceMatchDao;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.GcmUpdateReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.IssueTempPassReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.ItemSndRcvHistReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.LoginReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.membership.model.MemberRegisterReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.SaveMemberPhotoReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateFmJoinYnReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateMemberReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.dao.MsgBoxDao;
import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(timeout=15)
public class MembershipServiceImpl implements MembershipService {

	protected static Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);
	private Locale gLocale;
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private MsgBoxDao msgBoxDao;
	@Autowired
	private ConfigDao configDao;
	@Autowired
	private FaceMatchDao faceMatchDao;
	@Autowired
	private MsgUtil msgUtil;
	@Autowired
	private MuMailNotifier mailSender;
	
	@Value("#{config['APPLICATION_ROOT_PATH']}")
	private String APPLICATION_ROOT_PATH;
	
	/** 
	 * 로그인 이메일아이디 체크
	 */
	@Transactional(readOnly=true)
	public boolean checkLoginEmailId(CommReqVO reqParams) {
		boolean flag;
		Integer memberCnt = membershipDao.getMemberCntByEmailId(reqParams);
		if(memberCnt == 0) {
			flag = true; //사용가능
		} else {
			flag = false; //사용불가
		}
		return flag;
	}
	
	/** 
	 * 닉네임 체크
	 */
	@Transactional(readOnly=true)
	public boolean checkNickName(CommReqVO inputVO) {
		boolean flag;
		Integer memberCnt = membershipDao.getMemberCntByNickName(inputVO);
		if(memberCnt == 0) {
			flag = true; //사용가능
		} else {
			flag = false; //사용불가
		}
		return flag;
	}
	
	/** 
	 * 로그인
	 */
	public Map<String,Object> doLogin(LoginReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//Input 계정정보로 회원조회
		Map<String,Object> memberMap = membershipDao.getMemberByLoginAccount(inputVO);
		
		if(memberMap != null) {
			
			// 패스워드가 맞을때 정상 진행
			if("Y".equals(memberMap.get("checkPwd"))) {
			
				//회원 존재
				Integer memberId = (Integer)memberMap.get("memberId");
				inputVO.setgMemberId(memberId);
				
				UpdateMemberReqVO vo = new UpdateMemberReqVO();
				vo.setMemberId(memberId);
				vo.setGcmRegId(inputVO.getGcmRegId());
				vo.setLocation(inputVO.getLocation());
				vo.setHpNm(AES128Sync.decode(inputVO.getEtcHn()));
				membershipDao.updateMemberByMemberId(vo);
				
				//회원속성 조회
				List<Map<String,Object>> memberAttrList = membershipDao.selectMemberAttrByMemberId(inputVO);
				
				String	oneselfCertification = "N";	//본인인증 여부
				Integer	lastReadNotiNum = 0;		//최종확인 공지ID
				String	lastLoginDay = "";	//
				if(memberAttrList != null && memberAttrList.size() > 0) {
					for(Map<String, Object> attrMap : memberAttrList){
						if(Constants.MemberAttrName.ONESELF_CERTIFICATION.equals((String)attrMap.get("attrName"))) {
							oneselfCertification = (String)attrMap.get("attrValue");
						} else if(Constants.MemberAttrName.LAST_READ_NOTICE_ID.equals((String)attrMap.get("attrName"))) {
							lastReadNotiNum = Integer.parseInt((String)attrMap.get("attrValue"));
						} else if(Constants.MemberAttrName.LAST_LOGIN_DAY.equals((String)attrMap.get("attrName"))) {
							lastLoginDay = (String)attrMap.get("attrValue");
						}
					}
				}
				
				//일1회 포인트 지급
				/*
				 * 오픈 이벤트 종료 - 더이상 진행 안함
				 * 
				boolean givePointYn = false;
				
				String currentDay = UTL.getCurrentDate();
				log.info("currentDay : {}", currentDay);
				log.info("lastLoginDay : {}", lastLoginDay);
				if(StringUtils.isNotEmpty(currentDay) && StringUtils.isNotEmpty(lastLoginDay)) {
					if(Integer.parseInt(currentDay) > Integer.parseInt(lastLoginDay)) {
						givePointYn = true;
					} else {
						givePointYn = false;
					}
				} else {
					givePointYn = true;
				}
				
				//하루 한 번 500포인트 지급
				if(givePointYn) {
					MemberAttr attr = new MemberAttr();
					attr.setMemberId(memberId);
					attr.setAttrName(Constants.MemberAttrName.LAST_LOGIN_DAY);
					attr.setAttrValue(currentDay);
					membershipDao.updateMemberAttr(attr);
					
					commonService.updatePointInfo(memberId, Constants.EventTypeCd.INCOME, "I002", 10000, inputVO.getgLang());
				}
				*/
				
				
				//본인인증 여부 리턴
				memberMap.put("oneselfCertification",oneselfCertification);
				responseMap.put("member", memberMap);
				
				//Google In-App Billing 관련 KEY
				responseMap.put("licenseKey", Constants.GoogleBilling.LICENSE_KEY);
				
				//미확인 공지사항 수 리턴
				Integer unreadNotiCnt = configDao.selectUnreadNotiCnt(lastReadNotiNum);
				responseMap.put("unreadNotiCnt",unreadNotiCnt);
				
				//미확인 쪽지 수 조회
				Integer unreadMsgCnt = msgBoxDao.selectUnreadMsgCntByMemberId(memberId);
				responseMap.put("unreadMsgCnt",unreadMsgCnt);
				
				//미확인 마이페이지 알림 건수 조회
				Integer unreadMyPageNotiCnt = membershipDao.selectUnreadItemCntByMemberId(memberId);
				responseMap.put("unreadMyPageNotiCnt", unreadMyPageNotiCnt);
				
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("membership.login.ss.success", gLocale),
						msgUtil.getMsgText("membership.login.ss.success", gLocale));
				returnMap.put("result", result);
				returnMap.put("response", responseMap);
			}else{
				//패스워드가 틀릴때 
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("membership.login.le.fail_for_password", gLocale),
						msgUtil.getMsgText("membership.login.le.fail_for_password", gLocale));
				returnMap.put("result", result);
			}
		} else {
			//회원 미존재
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.login.le.fail", gLocale),
					msgUtil.getMsgText("membership.login.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 회원생성
	 */
	public Map<String,Object> doCreateMemeber(MemberRegisterReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		boolean flag;
		flag = this.checkLoginEmailId(inputVO);
		
		Integer memberId = null;
		if(flag) {
			//회원생성
			if(inputVO.getStatus() == null || StringUtils.isEmpty(inputVO.getStatus())) {
				inputVO.setStatus(Constants.MemberStatus.ACTIVE);
			}
			
			inputVO.setHpNm(AES128Sync.decode(inputVO.getEtcHn()));
			memberId = membershipDao.insertIntoMember(inputVO);
		} else {
			//이미 존재하는 ID인 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.createMember.le.exist", gLocale),
					msgUtil.getMsgText("membership.createMember.le.exist", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
		//결과리턴
		if(memberId != null && memberId > 0) {
			
			// 선물 주거나 받는 부분은 이슈가 있어서 회원 생성할때 직접 등록할 필요가 있음 (한번이라도 생성되지 않은 경우 가 이슈가 됨)
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			params.put("itemCd", Constants.ItemCode.WINK);
			membershipDao.insertIntoMemberInventory(params);
			params.put("itemCd", Constants.ItemCode.GIFT_FLOWER);
			membershipDao.insertIntoMemberInventory(params);
			// 마이페이지에서 보낸윙크, 꽃다발 초기화 안되는 이슈 해결용
			
			Map<String,Object> innerMap = new HashMap<String,Object>();
			innerMap.put("memberId", memberId);
			innerMap.put("unreadNotiCnt",1);
			returnMap.put("response", innerMap);
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("membership.createMember.ss.success", gLocale),
					msgUtil.getMsgText("membership.createMember.ss.success", gLocale));
			returnMap.put("result", result);
		} else {
			//기타에러
			Result result = new Result(
					Constants.ReturnCode.OTHER_ERROR,
					msgUtil.getMsgCd("comm.otherError", gLocale),
					msgUtil.getMsgText("comm.otherError", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 회원정보 업데이트
	 */
	public Map<String,Object> doUpdateMemeber(UpdateMemberReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		boolean flag = true;
		//닉네임이 null이 아니면 닉네임 중복체크 수행
		if(inputVO.getNickName() != null) {
			flag = this.checkNickName(inputVO);
		}
		
		
		Integer updateCnt = null;
		if(flag) {
			//회원 업데이트
			updateCnt = membershipDao.updateMemberByMemberId(inputVO);
		} else {
			//이미 존재하는 ID인 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.updateMember.le.nickNameExist", gLocale),
					msgUtil.getMsgText("membership.updateMember.le.nickNameExist", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
		//결과리턴
		if(updateCnt != null && updateCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("membership.updateMember.ss.success", gLocale),
					msgUtil.getMsgText("membership.updateMember.ss.success", gLocale));
			returnMap.put("result", result);
		} else {
			//기타에러
			Result result = new Result(
					Constants.ReturnCode.OTHER_ERROR,
					msgUtil.getMsgCd("comm.otherError", gLocale),
					msgUtil.getMsgText("comm.otherError", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 회원가입
	 */
	public Map<String,Object> doMemberRegister(MemberRegisterReqVO inputVO) {
		boolean flag;
		if(inputVO.getStatus() == null || StringUtils.isEmpty(inputVO.getStatus())) {
			inputVO.setStatus(Constants.MemberStatus.ACTIVE);
		}
		Integer memberId = membershipDao.insertIntoMember(inputVO);
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		if(memberId != null && memberId > 0) {
			flag = true;
			returnMap.put("memberId", memberId);
			returnMap.put("unreadNotiCnt",1);
		} else {
			flag = false;
		}
		
		returnMap.put("rsltFlag", flag);
		return returnMap;
	}
	
	/** 
	 * 마이페이지 조회
	 */
	public Map<String,Object> doGetMyPageInfo(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//Input 계정정보로 회원조회
		Map<String,Object> memberMap = membershipDao.selectMemberByMemberId(inputVO);
		List<Map<String,Object>> memberAttrList = membershipDao.selectMemberAttrByMemberId(inputVO);
		
		if(memberMap != null) {
			//회원 존재
			
			if(memberAttrList != null && memberAttrList.size() > 0) {
				Map<String,String> memberAttrMap = new HashMap<String,String>();
				for(Map<String, Object> attrMap : memberAttrList){
					memberAttrMap.put((String)attrMap.get("attrName"), (String)attrMap.get("attrValue"));
				}
				memberMap.put("attr", memberAttrMap);
			}

			//페이스매치 참여여부 조회
			String fmJoinYn = membershipDao.selectFmJoinYnByMemberId(inputVO);
			memberMap.put("fmJoinYn", (fmJoinYn==null)? "N" : fmJoinYn);
			
			responseMap.put("member", memberMap);
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//회원 미존재
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.getMyPage.le.fail", gLocale),
					msgUtil.getMsgText("membership.getMyPage.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 마이페이지 조회
	 */
	public Map<String,Object> getMyPageMain(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//Input 계정정보로 회원조회
		Map<String,Object> memberMap = membershipDao.selectMemberByMemberId(inputVO);
		
		//회원 존재
		if(memberMap != null) {
			//회원 인벤토리 정보 조회
			inputVO.setSearchType(Constants.ItemGroup.WINK);
			Map<String,Object> winkItem = membershipDao.selectItemInfoByMemberIdAndItemGroup(inputVO);
			responseMap.put("winkInfo", winkItem);
			
			inputVO.setSearchType(Constants.ItemGroup.GIFT);
			Map<String,Object> giftItem = membershipDao.selectItemInfoByMemberIdAndItemGroup(inputVO);
			responseMap.put("giftInfo", giftItem);
			
			inputVO.setSearchType(Constants.ItemGroup.POINT);
			Map<String,Object> pointItem = membershipDao.selectItemInfoByMemberIdAndItemGroup(inputVO);
			responseMap.put("pointInfo", pointItem);
			
			Map<String,Object> friendItem = membershipDao.selectFriendCntByMemberId(inputVO);
			responseMap.put("friendInfo", friendItem);
			
			List<Map<String,Object>> memberAttrList = membershipDao.selectMemberAttrByMemberId(inputVO);
			if(memberAttrList != null && memberAttrList.size() > 0) {
				Map<String,String> memberAttrMap = new HashMap<String,String>();
				for(Map<String, Object> attrMap : memberAttrList){
					memberAttrMap.put((String)attrMap.get("attrName"), (String)attrMap.get("attrValue"));
				}
				responseMap.put("attr", memberAttrMap);
			}
			
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
			
		//회원 미존재
		} else {
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.getMyPage.le.fail", gLocale),
					msgUtil.getMsgText("membership.getMyPage.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 프로필이미지 저장(파일저장, 이미지 크기 변환, 파일정보 업데이트)
	 */
	public Map<String,Object> doSaveMemberPhoto(SaveMemberPhotoReqVO inputVO, HttpServletRequest request) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String fileUsageType = inputVO.getFileUsageType();
		
		try {
			
			boolean resultFlag = true;
			
			if("Y".equals(inputVO.getDeleteFlag())) {
				
				  
				List<MemberAttr> fileAttrList = new ArrayList<MemberAttr>();
				
				MemberAttr memberAttr = new MemberAttr();	
				memberAttr.setMemberId(inputVO.getgMemberId());
				memberAttr.setAttrName(fileUsageType+Constants.ORIGINAL_IMAGE_SUFFIX);
				fileAttrList.add(memberAttr);
				
				memberAttr = new MemberAttr();	
				memberAttr.setMemberId(inputVO.getgMemberId());
				memberAttr.setAttrName(fileUsageType+Constants.BIG_IMAGE_SUFFIX);
				fileAttrList.add(memberAttr);
				
				memberAttr = new MemberAttr();	
				memberAttr.setMemberId(inputVO.getgMemberId());
				memberAttr.setAttrName(fileUsageType);
				fileAttrList.add(memberAttr);
				
				
				for(MemberAttr memberAttrItem : fileAttrList) {
					membershipDao.deleteMemberAttr(memberAttrItem);
				}
				
				//메인사진인 경우 mu_member.main_photo_url 업데이트
				Map<String,Object> fileInfoMap = new HashMap<String,Object>();
				if(Constants.UploadFileUsageType.MAIN_PHOTO.equalsIgnoreCase(fileUsageType)) {
					fileInfoMap.put("memberId", inputVO.getgMemberId());
					fileInfoMap.put("mainPhotoUrl", "");
					membershipDao.updateMemberMainPhotoURL(fileInfoMap);
					
					// 진행중인 페이스매치는 건드리면 안된다.
				}
				
			}else{
				
				//파일업로드 처리
				MultipartFile uploadFile = inputVO.getUploadFile();
				
				String baseFileContextPath	= Constants.PR_IMAGE_SAVE_PATH +"/"+ UTL.getCurrentDate();
				String baseFileName			= inputVO.getgMemberId() +"_"+ UTL.getCurrentDatetime();
				
				//이미지 크기 설정
				int bigImgWidth		= 500;
				int bigImgHeight	= 500;
				int thumbImgWidth	= 100;
				int thumbImgHeight	= 100;
				
				String orgFileName = baseFileName + "_"+ Constants.ORIGINAL_IMAGE_SUFFIX +"."+ Constants.FileExt.JPEG;
				String bigFileName = baseFileName + "_"+ Constants.BIG_IMAGE_SUFFIX +"."+ Constants.FileExt.JPEG;
				String thumbFileName = baseFileName +"."+ Constants.FileExt.JPEG;
				
				//대상폴더 미존재시 폴더 생성
				File f = new File(APPLICATION_ROOT_PATH + baseFileContextPath);
				if(!f.exists()) {
					f.mkdirs();
				}
				
				List<MemberAttr> fileAttrList = new ArrayList<MemberAttr>();
				
				String orgFile = APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName;
				
				//원본파일 저장
				uploadFile.transferTo(new File(orgFile));
				
				log.info("save to upload origin file :::: " + orgFile);
				
				MemberAttr memberAttr = new MemberAttr();	//mu_member_attr 업데이트를 위한 정보
				memberAttr.setMemberId(inputVO.getgMemberId());
				memberAttr.setAttrName(fileUsageType+Constants.ORIGINAL_IMAGE_SUFFIX);
				memberAttr.setAttrValue(baseFileContextPath +"/"+ orgFileName);
				fileAttrList.add(memberAttr);
				
				Thread.sleep(2000);
				
				log.info("before create big file from orgin file ::::: " + APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName);
				
				//큰사이즈 이미지 생성 및 저장
//				Thumbnail.createImage(orgFile, 
//						APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName, 
//						1, 
//						bigImgWidth, 
//						bigImgHeight);
				
				log.info("after create big file from orgin file.");
				Thumbnails.of(new File(orgFile))
						.size(bigImgWidth, bigImgHeight)
						.toFile(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName));
				memberAttr = new MemberAttr();	//mu_member_attr 업데이트를 위한 정보
				memberAttr.setMemberId(inputVO.getgMemberId());
				memberAttr.setAttrName(fileUsageType+Constants.BIG_IMAGE_SUFFIX);
				memberAttr.setAttrValue(baseFileContextPath +"/"+ bigFileName);
				fileAttrList.add(memberAttr);
				
				log.info("completed create big file from orgin file.");
				
				log.info("before create thumb file from orgin file ::: " + APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName);
				
				//썸네일 이미지 생성 및 저장
//				Thumbnail.createImage(orgFile, 
//						APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName, 
//						1, 
//						thumbImgWidth, 
//						thumbImgHeight);
				
				log.info("after create thumb file from orgin file.");
				Thumbnails.of(new File(orgFile))
						.size(thumbImgWidth, thumbImgHeight)
						.toFile(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName));
				memberAttr = new MemberAttr();	//mu_member_attr 업데이트를 위한 정보
				memberAttr.setMemberId(inputVO.getgMemberId());
				memberAttr.setAttrName(fileUsageType);
				memberAttr.setAttrValue(baseFileContextPath +"/"+ thumbFileName);
				fileAttrList.add(memberAttr);
				
				log.info("completed create thumb file from orgin file.");
				
				//멤버속성 업데이트
				
				for(MemberAttr memberAttrItem : fileAttrList) {
					Integer updateCnt = 0;
					updateCnt = membershipDao.updateMemberAttr(memberAttrItem);
					if(updateCnt == 0) {
						resultFlag = false;
					}
				}
			
			
				//메인사진인 경우 mu_member.main_photo_url 업데이트
				Map<String,Object> fileInfoMap = new HashMap<String,Object>();
				if(Constants.UploadFileUsageType.MAIN_PHOTO.equalsIgnoreCase(fileUsageType)) {
					fileInfoMap.put("memberId", inputVO.getgMemberId());
					fileInfoMap.put("mainPhotoUrl", baseFileContextPath +"/"+ thumbFileName);
					Integer updateCnt = membershipDao.updateMemberMainPhotoURL(fileInfoMap);
					if(updateCnt == 0) {
						resultFlag = false;
					}
					
					//진행중인 페이스매치 이벤트아이디 정보 조회
					Map<String,Object> event = faceMatchDao.selectCurrentFmEvent();
					Long eventId = (Long)event.get("eventId");
					
					//페이스매치 사진 업데이트
					if(eventId != null && eventId > 0) {
						Map<String,Object> inputMap = new HashMap<String,Object>();
						inputMap.put("eventId", eventId);
						inputMap.put("gMemberId", inputVO.getgMemberId());
						inputMap.put("photoUrl", baseFileContextPath +"/"+ bigFileName);
						faceMatchDao.insertOrUpdateFmPhoto(inputMap);
					}
				}
			}
			
			if(resultFlag) {
				//업데이트 성공
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("membership.saveFileInfo.ss.success", gLocale),
						msgUtil.getMsgText("membership.saveFileInfo.ss.success", gLocale));
				returnMap.put("result", result);
			} else {
				//업데이트 실패
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("membership.saveFileInfo.le.fail", gLocale),
						msgUtil.getMsgText("membership.saveFileInfo.le.fail", gLocale));
				returnMap.put("result", result);
			}

		} catch (IOException e) {
			log.info("error : " + e);
		} catch (Exception e) {
			
			log.info("error : " + e);
			
			e.printStackTrace();
			Result result = new Result(
					Constants.ReturnCode.SYSTEM_ERROR,
					msgUtil.getMsgCd("comm.systemError", gLocale),
					msgUtil.getMsgText("comm.systemError", gLocale));
			returnMap.put("result", result);
		} 
		
		return returnMap;
	}
	
	/** GCM 정보 업데이트 */
	public Map<String,Object> gcmUpdate(GcmUpdateReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		membershipDao.updateGcmInfo(inputVO);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("membership.gcmUpdate.ss.success", gLocale),
				msgUtil.getMsgText("membership.gcmUpdate.ss.success", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 회원 계정 삭제 */
	public Map<String,Object> deleteMember(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		int updateCnt = membershipDao.updateForMemberCancelation(inputVO.getgMemberId());
		
		if(updateCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("membership.deleteMember.ss.success", gLocale),
					msgUtil.getMsgText("membership.deleteMember.ss.success", gLocale));
			returnMap.put("result", result);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.deleteMember.le.fail", gLocale),
					msgUtil.getMsgText("membership.deleteMember.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap; 
	}
	
	/** 회원 계정 삭제 */
	public Map<String,Object> deleteMemberReal(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		int deleteCnt = membershipDao.deleteMember(inputVO.getgMemberId());
		
		if(deleteCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("membership.deleteMember.ss.success", gLocale),
					msgUtil.getMsgText("membership.deleteMember.ss.success", gLocale));
			returnMap.put("result", result);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.deleteMember.le.fail", gLocale),
					msgUtil.getMsgText("membership.deleteMember.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 아이템 수신내역 조회 서비스 */
	public Map<String,Object> itemReceiveHist(ItemSndRcvHistReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//아이템 수신내역 조회 서비스 호출
		List<Map<String,Object>> itemList = membershipDao.selectItemReceiveHistByItemGroup(inputVO);
		responseMap.put("itemHist", itemList);
		
		//아이템 읽음으로 업데이트
		membershipDao.updateItemSndRcvHistAsRead(inputVO);
		
		if(itemList != null && itemList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 아이템 송신내역 조회 서비스 */
	public Map<String,Object> itemSendHist(ItemSndRcvHistReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//아이템 수신내역 조회 서비스 호출
		List<Map<String,Object>> itemList = membershipDao.selectItemSendHistByItemGroup(inputVO);
		responseMap.put("itemHist", itemList);
		
		if(itemList != null && itemList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 임시 패스워드 발급 */
	public Map<String,Object> issueTempPass(IssueTempPassReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//회원조회
		Map<String,Object> member = membershipDao.selectSimpleMemberByIDAndNickName(inputVO);
		
		
		if(member != null ) {
			//임시패스워드 발급 및 업데이트
			String tempPass = getTempPass(6);
			UpdateMemberReqVO vo = new UpdateMemberReqVO();
			vo.setgMemberId((Integer)member.get("memberId"));
			vo.setMemberId((Integer)member.get("memberId"));
			vo.setLoginPw(tempPass);
			int updateCnt = membershipDao.updateMemberByMemberId(vo);
			if(updateCnt > 0) {
				//임시 패스워드 알림 메일 발송
				String loginId = inputVO.getLoginId();
				if(loginId != null && StringUtils.isNotEmpty(loginId)){
					MailSendParam param = new MailSendParam((Integer)member.get("memberId"), loginId, (String)member.get("nickName"));
					param.setEmailType(Constants.emailType.TEMP_PASSWORD);
					param.setLang(inputVO.getgLang());
					param.setLoginPw(tempPass);
					mailSender.sendEmail(param);
				}
				
				String gcmRegId = (String)member.get("gcmRegId");
				if(StringUtils.isNotBlank(gcmRegId)) {
					//GCM메세지 생성
					StringBuilder sb = new StringBuilder();
					sb.append( msgUtil.getPropMsg("membership.issueTempPass.msg", gLocale));
					sb.append( tempPass );
					sendGCMMsg(gcmRegId, sb.toString(), Constants.ActionType.PASS_MSG);
				} else {
					log.info("Unavailable GCM Registration ID");
					//GCM 등록ID가 없는 경우
					/*
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("membership.issueTempPass.le.wrongRegId", gLocale),
							msgUtil.getMsgText("membership.issueTempPass.le.wrongRegId", gLocale));
					returnMap.put("result", result);
					*/
				}
			}
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("membership.issueTempPass.ss.success", gLocale),
					msgUtil.getMsgText("membership.issueTempPass.ss.success", gLocale));
			returnMap.put("result", result);
			
		} else {
			//미존재 회원
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.issueTempPass.le.notExistMember", gLocale),
					msgUtil.getMsgText("membership.issueTempPass.le.notExistMember", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	public boolean sendGCMMsg(String registrationId, String msg, String action) {
		Map<String,String> gcmParams = new HashMap<String,String>();
		gcmParams.put("msg", msg);
		gcmParams.put("action", action);
		
		UTL.sendGCM(registrationId, gcmParams);
		
		return true;
	}
	
	
	/**
	 * 임시 패스워드 생성 후 리턴
	 * @return
	 */
	private String getTempPass(int length) {
		Random random = new Random();
		
		StringBuilder sbPass = new StringBuilder();
		
		for(int i=0 ; i < length; i++) {
			sbPass.append(random.nextInt(10));
		}
		
		log.info("Temp PW : {}", sbPass.toString());
		
		return sbPass.toString();
	}
	
	
	/** 포인트 내역 조회 서비스 */
	public Map<String,Object> pointUsageHist(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//포인트 사용내역 조회 서비스 호출
		List<Map<String,Object>> itemList = membershipDao.selectPointUsageHist(inputVO);
		responseMap.put("pointHist", itemList);
		
		if(itemList != null && itemList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 페이스매치 참여여부 업데이트 */
	public Map<String,Object> updateFmJoinYn(UpdateFmJoinYnReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		membershipDao.updateFmJoinYn(inputVO);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.update", gLocale),
				msgUtil.getMsgText("comm.success.update", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}

	@Override
	public int updateMemberAttr(MemberAttr attr) {
		
		return membershipDao.updateMemberAttr(attr);
		
	}
	
	
}
