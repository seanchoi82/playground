package kr.ko.nexmain.server.MissingU.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.admin.dao.AdminDao;
import kr.ko.nexmain.server.MissingU.admin.model.ApkItem;
import kr.ko.nexmain.server.MissingU.admin.service.AdminService;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.controller.BaseController;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.service.MembershipService;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.dao.MsgBoxDao;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversSendVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.SendMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.service.MsgBoxService;
import kr.ko.nexmain.server.MissingU.talktome.model.GetTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.service.TalktomeService;
import kr.ko.nexmain.server.MissingU.vscheck.service.VersionCheckService;
import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/admin")
public class AdminController extends BaseController {
	
	protected static Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	@Autowired
	private VersionCheckService versionCheckService;
	@Autowired
	private MembershipService membershipService;
	@Autowired
	private TalktomeService talktomeService;
	@Autowired
	private MsgBoxService msgBoxService;
	@Autowired
	private MsgBoxDao msgBoxDao;
	@Autowired
	private MembershipDao membershipDao; 
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private MsgUtil msgUtil;
	
	@Value("#{config['apk.save.path']}")
	private String APK_SAVE_PATH;
	
	@Value("#{config['APPLICATION_ROOT_PATH']}")
	private String APPLICATION_ROOT_PATH;
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	/* 마이그레이션 쿼리
		INSERT INTO mu_messagebox_msg (sender_id, receiver_id, msg_text, receiver_read_yn, receiver_read_date, created_date, updated_date, STATUS, own_member_id )
		SELECT sender_id, receiver_id, msg_text, receiver_read_yn, receiver_read_date, created_date, updated_date, 'A', receiver_id FROM mu_msg;	
		
		INSERT INTO mu_messagebox_msg (sender_id, receiver_id, msg_text, receiver_read_yn, receiver_read_date, created_date, updated_date, STATUS, own_member_id )
		SELECT sender_id, receiver_id, msg_text, receiver_read_yn, receiver_read_date, created_date, updated_date, 'A', sender_id FROM mu_msg;
	*/
	/** 참여한 미션 목록을 가져온다. */
	@RequestMapping(value="/msgConversList.html")
	public ModelAndView msgConversList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "msgConversList()"); 
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		makePaging(params);
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMsgList(params);
		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/msgmgr/admin_msgConversList");
		modelAndView.addAllObjects(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "msgConversList()");
		log.info("{}","==============================================\n");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/sendMsg.html")
	public ModelAndView sendMsg(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================\n");
		log.info("[{} START]", "sendMsg()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		ModelAndView modelAndView;
		try {
			
			String strReceiverList = (params.get("receiverList") == null) ? null : (String)params.get("receiverList");
			log.info("strReceiverList : {}", strReceiverList);
			
			String[] receiverList = null;
			if(strReceiverList != null) {
				receiverList = strReceiverList.split(",");
			}
			
			MsgBoxConversSendVO inputVO = null;
			Map<String,Object> returnMap = null;
			for(String strReceiverId : receiverList) {
				if(StringUtils.hasText(strReceiverId)) {
					//쪽지전송
					inputVO = new MsgBoxConversSendVO();
					inputVO.setgMemberId(Integer.parseInt((String)params.get("gMemberId")));
					inputVO.setTargetMemberId(Integer.parseInt(strReceiverId));
					inputVO.setMsgText((String)params.get("msgText"));
					inputVO.setgLang((String)params.get("gLang"));
					
					returnMap = this.msgBoxService.sendConversMsg(inputVO);
				} else {
					returnMap = msgUtil.getSystemErrorMap(responseMap);
					log.info("MemberId is Empty");
				}
			}
			
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendMsg()");
		log.info("{}","====================== Admin ========================\n");
		return modelAndView;
	}
	
	/**
	 * 메세지 관리 > 최근 메세지 검색
	 * @return
	 */
	@RequestMapping(value="/msgConversConversation.html", method = RequestMethod.GET)
	public ModelAndView msgConversConversation(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "msgConversConversation()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
				
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMsgConversations(params);
		returnMap.put("reqParams", params);
		
		// 회원정보 조회
		CommReqVO vo = new CommReqVO();
		vo.setgMemberId(Integer.parseInt(params.get("senderId").toString()));
		returnMap.put("sender", membershipDao.selectMemberByMemberId(vo));
		
		vo.setgMemberId(Integer.parseInt(params.get("receiverId").toString()));
		returnMap.put("receiver", membershipDao.selectMemberByMemberId(vo));
					
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/msgmgr/admin_msgConversConversation");
		modelAndView.addAllObjects(returnMap);
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "msgConversConversation()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	/**
	 * 메세지 삭제
	 * @return
	 */
	@RequestMapping(value="/deleteMsgConvers.html", method = RequestMethod.POST)
	public ModelAndView deleteMsgConvers(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", " deleteMsgConvers()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String msgId = params.get("msgId") + "";
			
			int updateCnt = adminService.deleteMsg(Long.parseLong(msgId));
			Locale gLocale = Locale.KOREAN;
			Map<String,Object> returnMap = new HashMap<String,Object>();
			if(updateCnt > 0) {
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search", gLocale),
						msgUtil.getMsgText("comm.success.search", gLocale));
				returnMap.put("result", result);
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("comm.fail.search", gLocale),
						msgUtil.getMsgText("comm.fail.search", gLocale));
				returnMap.put("result", result);
			}
			
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", " deleteMsgConvers()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**
	 * 메세지 삭제
	 * @return
	 */
	@RequestMapping(value="/deleteMsgConversAllArray.html", method = RequestMethod.POST)
	public ModelAndView deleteMsgConversAllArray(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "deleteMsgConversAllArray()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String members = params.get("members") + "";
			params.put("members", members.split(","));
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.deleteMsgGroup(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteMsgConversAllArray()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**
	 * 메세지 읽음여부 업데이트
	 * @return
	 */
	@RequestMapping(value="/updateMsgConversReadYn.html", method = RequestMethod.POST)
	public ModelAndView updateMsgConversReadYn(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "updateMsgConversReadYn()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String msgId = params.get("msgId") + "";
			
			//로그인 서비스 호출
			int updateCnt = adminService.updateReadYn(Long.parseLong(msgId));
			Locale gLocale = Locale.KOREAN;
			Map<String,Object> returnMap = new HashMap<String,Object>();
			if(updateCnt > 0) {
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search", gLocale),
						msgUtil.getMsgText("comm.success.search", gLocale));
				returnMap.put("result", result);
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("comm.fail.search", gLocale),
						msgUtil.getMsgText("comm.fail.search", gLocale));
				returnMap.put("result", result);
			}
			
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "updateMsgConversReadYn()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------	
	
	/** 참여한 미션 목록을 가져온다. */
	@RequestMapping(value="/missionMatchJoinList.html")
	public ModelAndView joinMissionMatchList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "missionMatchJoinList()"); 
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		
		makePaging(params);
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.joinMissionMatchList(params);
		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/missionmgr/missionMatchJoinList");
		modelAndView.addAllObjects(returnMap);
		appendCommonCode(modelAndView, Constants.SexCode.class);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchJoinList()");
		log.info("{}","==============================================\n");
		
		return modelAndView;
	}
	
	/** 미션 등록 / 수정 페이지를 호출한다. */
	@RequestMapping(value="/missionMatchJoinEdit.html")
	public ModelAndView joinMissionMatchEdit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "missionMatchJoinEdit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reqParams", params);
		
		if(params.containsKey("mId") && params.get("mId").toString().length() > 0) {
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put("mId",(String)params.get("mId"));
			Map<String,Object> mission = adminDao.selectMissionMatch(inputMap);
			// 모델 생성
			model.put("mission", mission);
			
			if(params.containsKey("mJId") && params.get("mJId").toString().length() > 0) {
				Map<String,Object> inputMap2 = new HashMap<String,Object>();
				inputMap.put("mJId",(String)params.get("mJId"));
				Map<String,Object> missionJoin = adminDao.selectJoinMissionMatch(inputMap);
				// 모델 생성
				model.put("missionJoin", missionJoin);
			}
		}
	
		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/missionmgr/missionMatchJoinEdit");
		modelAndView.addAllObjects(model);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchJoinEdit()");
		log.info("{}","====================== Admin ========================\n");
	
		return modelAndView;
		
	}
	
	/** 참여한 미션을 삭제한다. */
	@RequestMapping(value="/missionMatchJoinDel.html")
	public ModelAndView joinMissionMatchDel(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "missionMatchJoinDel()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String mJIds = params.get("mJIds") + "";
			params.put("mJIds", mJIds.split(","));
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.joinDeleteMissionMatch(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchJoinDel()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/** 참여한 미션을 삭제한다. */
	@RequestMapping(value="/missionMatchJoinSave.html")
	public ModelAndView missionMatchJoinSave(@Valid MissionMatchJoinReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "missionMatchJoinSave()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			// 미션매치 등록
			MultipartFile uploadFile = inputVO.getSaveFile();
			//파라미터에 톡사진이 있는 경우 사진 업로드
			if(uploadFile != null) {
				
				String baseFileContextPath	= Constants.MISSIONMATCH_IMG_SAVE_PATH +"/"+ UTL.getCurrentDate();
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
				
				//원본파일 저장
				File orginal = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName);
				File big = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName);
				File thumnail = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName);
				
				uploadFile.transferTo(orginal);
				inputVO.setUploadfile(baseFileContextPath +"/"+ orgFileName);			//mu_talketome 업데이트를 위한 정보
				
				//큰사이즈 이미지 생성 및 저장
				Thumbnails.of(orginal)
						.size(bigImgWidth, bigImgHeight)
						.toFile(big);
				inputVO.setUploadfileBig(baseFileContextPath +"/"+ bigFileName);			//mu_talketome 업데이트를 위한 정보
				
				//썸네일 이미지 생성 및 저장
				Thumbnails.of(orginal)
						.size(thumbImgWidth, thumbImgHeight)
						.toFile(thumnail);
				inputVO.setUploadfileOrg(baseFileContextPath +"/"+ thumbFileName);			//mu_talketome 업데이트를 위한 정보
			}
						
			Map<String,Object> returnMap = this.adminService.saveJoinMissionMatch(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchJoinSave()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/missionMatchList.html")
	public ModelAndView missionMatchList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "missionMatchInfo()"); 
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		
		makePaging(params);
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMissionMatchList(params);
		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/missionmgr/missionMatchList");
		modelAndView.addAllObjects(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchInfo()");
		log.info("{}","==============================================\n");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/missionMatchEdit.html")
	public ModelAndView missionMatchEdit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "missionMatchEdit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reqParams", params);
		
		if(params.containsKey("mId") && params.get("mId").toString().length() > 0) {
			// 공지사항 내용 조회
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put("mId",(String)params.get("mId"));
			Map<String,Object> mission = adminDao.selectMissionMatch(inputMap);
	
			// 모델 생성
			model.put("mission", mission);
		}
	
		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/missionmgr/missionMatchEdit");
		modelAndView.addAllObjects(model);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchEdit()");
		log.info("{}","====================== Admin ========================\n");
	
		return modelAndView;
	}
	
	@RequestMapping(value="/saveMissionMatch.html")
	public ModelAndView saveMissionMatch(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "saveGuide()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//사용자가이드 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.saveMissionMatch(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveGuide()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value="/missionMatchSates.html")
	public ModelAndView missionMatchSates(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "missionMatchSates()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		// 기본 파라미터
		if(!params.containsKey("is_all"))
			params.put("is_all", "1");
				
		makePaging(params);
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMissionMatchStatus(params);
		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView =  new ModelAndView("admin/missionmgr/missionMatchSates");
		modelAndView.addAllObjects(returnMap);
		
		List<Integer> years = new ArrayList<Integer>();
		List<Integer> months = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		for(int i=cal.get(Calendar.YEAR);i>2012;i--) {
			years.add(i);
		}
		
		for(int i=1;i<13;i++) {
			months.add(i);
		}
		
		modelAndView.addObject("years", years);
		modelAndView.addObject("months", months);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "missionMatchSates()");
		log.info("{}","====================== Admin ========================\n");
	
		return modelAndView;
	}
	
	/**
	 * 공지사항 관리 : 공지사항 삭제
	 * @return
	 */
	@RequestMapping(value="/deleteMissionMatch.html", method = RequestMethod.POST)
	public ModelAndView deleteMissionMatch(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "deleteMissionMatch()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//공지사항 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.deleteMissionMatch(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteMissionMatch()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/main.html", method = RequestMethod.GET)
	public ModelAndView main(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "main()"); 
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		
		log.info("{}", org.springframework.core.SpringVersion.getVersion());
		
		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/admin_main");
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "main()");
		log.info("{}","==============================================\n");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/apkList.html")
	public ModelAndView apkList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		// 상품목록정보 취득
		List<ApkItem> apkItemList = this.adminService.getApkItemList();
		
		CommReqVO inputVO = new CommReqVO();
		inputVO.setgLang(params.get("lang") == null || params.get("lang").toString().length() == 0 ? "ko" : params.get("lang").toString());
		
		Map<String,Object> returnMap = versionCheckService.getUpgradeVersionInfoAndEMRNotification(inputVO, false);

		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apkItemList", apkItemList);

		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/apkmgr/admin_apkList");
		modelAndView.addAllObjects(returnMap);
		modelAndView.addAllObjects(model);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/apkAdd.html", method = RequestMethod.GET)
	public ModelAndView apkAdd() {
		ModelAndView modelAndView = new ModelAndView("admin/apkmgr/admin_apkAdd");
		modelAndView.addObject(new ApkItem());

		return modelAndView;
	}
	
	@RequestMapping(value="/apkAddSubmit.html", method = RequestMethod.POST)
	public ModelAndView apkAddSubmit(@Valid ApkItem apkItem, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("admin/apkmgr/admin_apkAdd");
			modelAndView.getModel().putAll(bindingResult.getModel());
			return modelAndView;
		}
		
		//파일업로드 처리
		MultipartFile apkFile = apkItem.getApkFile();
		String fileName = apkFile.getOriginalFilename();
		try {
			apkFile.transferTo(new File(APK_SAVE_PATH + fileName));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		apkItem.setApkFileName(fileName);
		this.adminService.addApkItem(apkItem);
		
		return new ModelAndView("redirect:apkList.html");
	}
	
	@RequestMapping(value="/apkDelete.html", method = RequestMethod.GET)
	public ModelAndView apkDelete(@RequestParam int apkId) {
		this.adminService.deleteApkItem(apkId);
		
		return new ModelAndView("redirect:apkList.html");
	}
	
	@RequestMapping(value="/apkDownload.apk", method = RequestMethod.GET)
	public ModelAndView apkDownload(@RequestParam String apkFileName) {
		File apkFile = new File(APK_SAVE_PATH + apkFileName);
		
		return new ModelAndView("download", "downloadFile", apkFile);
	}
	
	@RequestMapping(value="/apkUpgradeConfirm.html")
	public ModelAndView apkUpgradeConfirm(@RequestParam Map<String,Object> params, HttpServletRequest request) {
	
		this.versionCheckService.updateUpgradeVersionInfo(params);
		
		return new ModelAndView("redirect:apkList.html");
	}
	
	@RequestMapping(value="/emrNoticeConfirm.html")
	public ModelAndView emrNoticeConfirm(@RequestParam Map<String,Object> params, HttpServletRequest request) {
	
		this.versionCheckService.updateEmrNotice(params);
		
		return new ModelAndView("redirect:apkList.html");
	}
	
	@RequestMapping(value="/apkListRandomChat.html")
	public ModelAndView apkListRandomChat(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		// 상품목록정보 취득
		List<ApkItem> apkItemList = this.adminService.getApkItemList();
		
		CommReqVO inputVO = new CommReqVO();
		inputVO.setgLang(params.get("lang") == null || params.get("lang").toString().length() == 0 ? "ko" : params.get("lang").toString());
		
		Map<String,Object> returnMap = versionCheckService.getUpgradeVersionInfoAndEMRNotificationRandomChat(inputVO, false);

		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apkItemList", apkItemList);

		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/apkmgr/admin_apkListRandomChat");
		modelAndView.addAllObjects(returnMap);
		modelAndView.addAllObjects(model);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/apkAddRandomChat.html", method = RequestMethod.GET)
	public ModelAndView apkAddRandomChat() {
		ModelAndView modelAndView = new ModelAndView("admin/apkmgr/admin_apkAddRandomChat");
		modelAndView.addObject(new ApkItem());

		return modelAndView;
	}
	
	@RequestMapping(value="/apkAddSubmitRandomChat.html", method = RequestMethod.POST)
	public ModelAndView apkAddSubmitRandomChat(@Valid ApkItem apkItem, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("admin/apkmgr/admin_apkAddRandomChat");
			modelAndView.getModel().putAll(bindingResult.getModel());
			return modelAndView;
		}
		
		//파일업로드 처리
		MultipartFile apkFile = apkItem.getApkFile();
		String fileName = apkFile.getOriginalFilename();
		try {
			apkFile.transferTo(new File(APK_SAVE_PATH + fileName));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		apkItem.setApkFileName(fileName);
		this.adminService.addApkItem(apkItem);
		
		return new ModelAndView("redirect:apkListRandomChat.html");
	}
	
	@RequestMapping(value="/apkDeleteRandomChat.html", method = RequestMethod.GET)
	public ModelAndView apkDeleteRandomChat(@RequestParam int apkId) {
		this.adminService.deleteApkItem(apkId);
		
		return new ModelAndView("redirect:apkListRandomChat.html");
	}
	
	@RequestMapping(value="/apkUpgradeConfirmRandomChat.html")
	public ModelAndView apkUpgradeConfirmRandomChat(@RequestParam Map<String,Object> params, HttpServletRequest request) {
	
		this.versionCheckService.updateUpgradeVersionInfoRandomChat(params);
		
		return new ModelAndView("redirect:apkListRandomChat.html");
	}
	
	@RequestMapping(value="/emrNoticeConfirmRandomChat.html")
	public ModelAndView emrNoticeConfirmRandomChat(@RequestParam Map<String,Object> params, HttpServletRequest request) {
	
		this.versionCheckService.updateEmrNoticeRandomChat(params);
		
		return new ModelAndView("redirect:apkListRandomChat.html");
	}
		
	/**
	 * 회원관리 > 회원검색
	 * @return
	 */
	@RequestMapping(value="/memberList.html")
	public ModelAndView memberList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "memberList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		makePaging(params);
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/membermgr/admin-memberList");
		modelAndView.addObject("req", params);
		appendCommonCode(modelAndView, Constants.SexCode.class);
		appendCommonCode(modelAndView, Constants.BloodTypeCode.class);
		appendCommonCode(modelAndView, Constants.MemberStatus.class);
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMemberListNew(params);
		modelAndView.addAllObjects(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "memberList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 쪽지보내기
	 * @return
	 */
	@RequestMapping(value="/sendMessage.html")
	public ModelAndView sendMessage(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/membermgr/admin-sendMessage");
		modelAndView.addObject("req", params);
		return modelAndView;
	}

	/**
	 * 회원관리 > 회원검색
	 * @return
	 */
	@RequestMapping(value="/getMemberList.html", method = RequestMethod.POST)
	public ModelAndView getMmemberList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getMmemberList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------"); 
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//회원목록 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getMemberList(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMmemberList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 회원관리 > 회원검색
	 * @return
	 */
	@RequestMapping(value="/getMemberListCnt.html", method = RequestMethod.POST)
	public ModelAndView getMemberListCnt(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getMemberListCnt()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//회원목록 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getMemberListCnt(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMemberListCnt()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/memberEdit.html", method = RequestMethod.GET)
	public ModelAndView memberEdit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "memberEdit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Integer memberId = null;
		String	actionType = (String)params.get("actionType");
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reqParams", params);
		
		Map<String,Object> member = null;
		if("modify".equalsIgnoreCase(actionType)) {
			memberId = Integer.parseInt((String)params.get("memberId"));
			// 회원정보 조회
			CommReqVO vo = new CommReqVO();
			vo.setgMemberId(memberId);
			member = membershipDao.selectMemberByMemberId(vo);
			
			if(member != null) {
				//회원 존재
				List<Map<String,Object>> memberAttrList = membershipDao.selectMemberAttrByMemberId(vo);
				if(memberAttrList != null && memberAttrList.size() > 0) {
					Map<String,String> memberAttrMap = new HashMap<String,String>();
					for(Map<String, Object> attrMap : memberAttrList){
						memberAttrMap.put((String)attrMap.get("attrName"), (String)attrMap.get("attrValue"));
					}
					member.put("attr", memberAttrMap);
				}
				
//				vo.setSearchType(Constants.ItemGroup.POINT);
				Map<String,Object> pointItem = membershipDao.selectItemInfoByMemberIdAndItemGroup(vo);
				model.put("pointInfo", pointItem); 
				
				
				Map<String,Object> hisParams = new HashMap<String,Object>();
				hisParams.put("pageSize", "10");
				hisParams.put("startRow", "0");
				hisParams.put("cond_memberId", member.get("memberId"));
								
				Map<String,Object> history = this.adminService.getPointUseHistList(hisParams);
				model.put("pointHistory", history.get("response"));
			}
			
			model.put("member", member);
		}
		
		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/membermgr/admin_memberEdit");
		modelAndView.addAllObjects(model);
		
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "memberEdit()");
		log.info("{}","====================== Admin ========================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/deleteMember.html", method = RequestMethod.POST)
	public ModelAndView deleteMember(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================\n");
		log.info("[{} START]", "deleteMember()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		Integer memberId = null;
		
		ModelAndView modelAndView;
		CommReqVO inputVO = new CommReqVO();
		inputVO.setgLang("ko");
		try {
			memberId = Integer.parseInt((String)params.get("memberId"));			
			inputVO.setgMemberId(memberId);
			
			//친구 추가 서비스 호출
			Map<String,Object> returnMap = this.membershipService.deleteMemberReal(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteMember()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	/**********************************************
	회원정보 생성/업데이트
	***********************************************/
	@RequestMapping(value="/saveMember.html", method = RequestMethod.POST)
	public ModelAndView saveMember(@RequestParam Map<String,Object> params, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================\n");
		log.info("[{} START]", "saveMember()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			Map<String,Object> returnMap = this.adminService.saveMember(params, uploadFile, request);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveMember()");
		log.info("{}","====================== Admin ========================\n");
		return modelAndView;
	}
	
	/**********************************************
	회원관리 > 회원 목록 조회 - 쪽지발송
	***********************************************/
	@RequestMapping(value="/sendMsgOld.html")
	public ModelAndView sendMsgOld(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================\n");
		log.info("[{} START]", "sendMsg()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String strReceiverList = (params.get("receiverList") == null) ? null : (String)params.get("receiverList");
			log.info("strReceiverList : {}", strReceiverList);
			
			String[] receiverList = null;
			if(strReceiverList != null) {
				receiverList = strReceiverList.split(",");
			}
			
			SendMsgReqVO inputVO = null;
			Map<String,Object> returnMap = null;
			for(String strReceiverId : receiverList) {
				if(StringUtils.hasText(strReceiverId)) {
					//쪽지전송
					inputVO = new SendMsgReqVO();
					inputVO.setgMemberId(Integer.parseInt((String)params.get("gMemberId")));
					inputVO.setReceiverId(Integer.parseInt(strReceiverId));
					inputVO.setMsg((String)params.get("msgText"));
					inputVO.setgLang((String)params.get("gLang"));
					returnMap = this.msgBoxService.sendMsg(inputVO);
				} else {
					returnMap = msgUtil.getSystemErrorMap(responseMap);
					log.info("MemberId is Empty");
				}
			}
			
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendMsg()");
		log.info("{}","====================== Admin ========================\n");
		return modelAndView;
	}
	
	
	/**
	 * 톡투미 관리 > 톡투미 검색
	 * @return
	 */
	@RequestMapping(value="/talkToMeDelete.html", method = RequestMethod.POST)
	public ModelAndView talkToMeDelete(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "talkToMeDelete()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//공지사항 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.deleteTalkToMe(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "talkToMeDelete()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	/**
	 * 톡투미 관리 > 톡투미 검색
	 * @return
	 */
	@RequestMapping(value="/talkToMeList.html", method = RequestMethod.GET)
	public ModelAndView talkToMeList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getMmemberList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		
		if(!params.containsKey("page"))
			params.put("page", "1");
		
		if(!params.containsKey("startRow"))
			params.put("startRow", "0");
		
		if(!params.containsKey("pageSize"))
			params.put("pageSize", "10");
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getTalkToMeList(params);
		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/talktomemgr/admin_talkToMeList");
		modelAndView.addAllObjects(returnMap);
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMmemberList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 톡투미 관리 > 톡투미 편집
	 * @return
	 */
	@RequestMapping(value="/talkToMeEdit.html")
	public ModelAndView talkToMeEdit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "talkToMeEdit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reqParams", params);
		
		ModelAndView modelAndView = new ModelAndView("admin/talktomemgr/admin_talkToMeEdit");
		
		if(params.containsKey("talkId")) {
			GetTalkReqVO inputVO = new GetTalkReqVO();
			inputVO.setTalkId(Integer.parseInt(params.get("talkId") + ""));
			inputVO.setgLang("ko");
		
			modelAndView.addAllObjects(talktomeService.getTalktome(inputVO));
		}
		
		modelAndView.addAllObjects(model);			
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "talkToMeEdit()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	/********************************************** 
	(마이페이지) 회원사진 신규 업로드/업데이트
	***********************************************/
	@RequestMapping(value="/saveTalk.html", method = RequestMethod.POST)
	public ModelAndView saveTalk(@Valid SaveTalkReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "saveTalk()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		ModelAndView modelAndView;
		
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				log.info(" errors "  + bindingResult.getAllErrors().toString());
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
	
				return modelAndView;
			}
			
			MultipartFile uploadFile = inputVO.getUploadFile();
			//파라미터에 톡사진이 있는 경우 사진 업로드
			if(uploadFile != null) {
				
				String baseFileContextPath	= Constants.TALK_IMG_SAVE_PATH +"/"+ UTL.getCurrentDate();
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
				
				//원본파일 저장
				uploadFile.transferTo(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName));
				inputVO.setTalkPhotoOrgUrl(baseFileContextPath +"/"+ orgFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("bigImgWidth " + bigImgWidth + " bigImgHeight" + bigImgHeight); 
				log.info("save big filename : " + APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName);
				
				//큰사이즈 이미지 생성 및 저장
				Thumbnails
						.of(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName))
						.size(bigImgWidth, bigImgHeight)
						.toFile(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName));
				inputVO.setTalkPhotoBigUrl(baseFileContextPath +"/"+ bigFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("save big file");
				
				//썸네일 이미지 생성 및 저장
				Thumbnails.of(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName))
						.size(thumbImgWidth, thumbImgHeight)
						.toFile(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName));
				inputVO.setTalkPhotoUrl(baseFileContextPath +"/"+ thumbFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("save thumnail file");
			}
				
			Map<String,Object> returnMap = this.talktomeService.doSaveTalkInfoNoUsePoint(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
			log.info("save talk completed");
		}catch(IOException e) {
			log.info("io error : " + e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
			
		} catch (Exception e) {
			log.info("error : " + e.getMessage());
			
			e.printStackTrace();
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		} 
		
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveTalk()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/********************************************** 
	(마이페이지) 회원사진 신규 업로드/업데이트
	***********************************************/
	@RequestMapping(value="/editTalk.html", method = RequestMethod.POST)
	public ModelAndView editTalk(@Valid SaveTalkReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "saveTalk()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		ModelAndView modelAndView;
		
		try {
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				log.info("errors " + bindingResult.getAllErrors().toString());
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
	
				return modelAndView;
			}
			
			MultipartFile uploadFile = inputVO.getUploadFile();
			//파라미터에 톡사진이 있는 경우 사진 업로드
			if(uploadFile != null) {
				String baseFileContextPath	= Constants.TALK_IMG_SAVE_PATH +"/"+ UTL.getCurrentDate();
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
				
				log.info("folder has : " + f.exists());
				
				
				//원본파일 저장
				File file = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName);
				uploadFile.transferTo(file);
				inputVO.setTalkPhotoOrgUrl(baseFileContextPath +"/"+ orgFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("saved file : " + file.exists() + ", " + file.toString());
				
				//큰사이즈 이미지 생성 및 저장
				Thumbnails.of(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName))
						.size(bigImgWidth, bigImgHeight)
						.toFile(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName));
				inputVO.setTalkPhotoBigUrl(baseFileContextPath +"/"+ bigFileName);			//mu_talketome 업데이트를 위한 정보
				
				//썸네일 이미지 생성 및 저장
				Thumbnails.of(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName))
						.size(thumbImgWidth, thumbImgHeight)
						.toFile(new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName));
				inputVO.setTalkPhotoUrl(baseFileContextPath +"/"+ thumbFileName);			//mu_talketome 업데이트를 위한 정보
			}
			
			Map<String,Object> returnMap = this.talktomeService.doEditTalkInfo(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
				
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		} 
		
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveTalk()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
	/**********************************************
	(톡투미) 톡투미 댓글 저장
	***********************************************/
	@RequestMapping(value="/saveTalkReply.html")
	public ModelAndView saveTalkReply(@Valid SaveTalkReplyReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "saveTalkReply()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//로그인 서비스 호출
			inputVO.setUseSavePoint(false);
			Map<String,Object> returnMap = talktomeService.doSaveTalkReply(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveTalkReply()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	/**********************************************
	(톡투미) 톡투미 댓글 저장
	***********************************************/
	@RequestMapping(value="/delTalkReply.html")
	public ModelAndView delTalkReply(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "delTalkReply()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.doDelTalkReply(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveTalkReply()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(톡투미) 톡투미 삭제
	***********************************************/
	@RequestMapping(value="/delTalkArr.html")
	public ModelAndView delTalkArr(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "delTalkReply()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String talkIds = params.get("talkIds") + "";
			params.put("talkIds", talkIds.split(","));
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.doDelTalkArr(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveTalkReply()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	/**
	 * 페이스매치 관리 > 페이스매치 조회(Count)
	 * @return
	 */
	@RequestMapping(value="/getFmListCnt.html", method = RequestMethod.POST)
	public ModelAndView getFmListCnt(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getFmListCnt()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//회원목록 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getFmListCnt(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getFmListCnt()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 페이스매치 관리 > 페이스매치 조회
	 * @return
	 */
	@RequestMapping(value="/getFmList.html", method = RequestMethod.POST)
	public ModelAndView getFmList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getFmList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//회원목록 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getFmList(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getFmList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 결제/포인트 관리 > 결제내역 조회
	 * @return
	 */
	@RequestMapping(value="/getPayHistList.html", method = RequestMethod.POST)
	public ModelAndView getPayHistList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getPayHistList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//결제내역 목록 조회
			Map<String,Object> returnMap = this.adminService.getPayHistList(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getPayHistList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 결제/포인트 관리 > 결제내역 조회 Count
	 * @return
	 */
	@RequestMapping(value="/getPayHistListCnt.html", method = RequestMethod.POST)
	public ModelAndView getPayHistListCnt(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getPayHistListCnt()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//결제내역 목록 조회
			Map<String,Object> returnMap = this.adminService.getPayHistListCnt(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getPayHistListCnt()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 결제/포인트 관리 > 포인트 사용내역 조회
	 * @return
	 */
	@RequestMapping(value="/getPointUseHistList.html", method = RequestMethod.POST)
	public ModelAndView getPointUseHistList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getPointUseHistList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//포인트사용 내역 조회
			Map<String,Object> returnMap = this.adminService.getPointUseHistList(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getPointUseHistList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 결제/포인트 관리 > 포인트 사용내역 조회 Count
	 * @return
	 */
	@RequestMapping(value="/getPointUseHistListCnt.html", method = RequestMethod.POST)
	public ModelAndView getPointUseHistListCnt(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getPointUseHistListCnt()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//포인트사용 내역 조회
			Map<String,Object> returnMap = this.adminService.getPointUseHistListCnt(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getPointUseHistListCnt()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	/**
	 * 공지사항 관리 > 공지사항 목록 조회
	 * @return
	 */
	@RequestMapping(value="/getNoticeList.html", method = RequestMethod.POST)
	public ModelAndView getNoticeList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getNoticeList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//채팅방 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getNoticeList(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getNoticeList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 공지사항 관리 > 공지사항 목록 조회 Count
	 * @return
	 */
	@RequestMapping(value="/getNoticeListCnt.html", method = RequestMethod.POST)
	public ModelAndView getNoticeListCnt(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getNoticeListCnt()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//채팅방 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getNoticeListCnt(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getNoticeListCnt()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 공지사항 관리 > 공지사항 수정
	 * @return
	 */
	@RequestMapping(value="/noticeEdit.html", method = RequestMethod.GET)
	public ModelAndView noticeEdit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "noticeEdit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		String	actionType = (String)params.get("actionType");
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reqParams", params);
		
		if("modify".equalsIgnoreCase(actionType)) {
			// 공지사항 내용 조회
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put("noticeId",(String)params.get("noticeId"));
			Map<String,Object> notice = adminDao.selectNotice(inputMap);

			// 모델 생성
			model.put("notice", notice);
		}

		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/noticemgr/admin_noticeEdit");
		modelAndView.addAllObjects(model);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "noticeEdit()");
		log.info("{}","====================== Admin ========================\n");

		return modelAndView;
	}
	
	/**
	 * 공지사항 관리 : 공지사항 저장
	 * @return
	 */
	@RequestMapping(value="/saveNotice.html", method = RequestMethod.POST)
	public ModelAndView saveNotice(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "saveNotice()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//공지사항 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.saveNotice(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveNotice()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 공지사항 관리 : 공지사항 삭제
	 * @return
	 */
	@RequestMapping(value="/deleteNotice.html", method = RequestMethod.POST)
	public ModelAndView deleteNotice(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "deleteNotice()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//공지사항 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.deleteNotice(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteNotice()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	
	/**
	 * 사용자가이드 관리 > 사용자가이드 목록 조회
	 * @return
	 */
	@RequestMapping(value="/getGuideList.html", method = RequestMethod.POST)
	public ModelAndView getGuideList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getGuideList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//채팅방 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getGuideList(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getGuideList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 사용자가이드 관리 > 사용자가이드 목록 조회 Count
	 * @return
	 */
	@RequestMapping(value="/getGuideListCnt.html", method = RequestMethod.POST)
	public ModelAndView getGuideListCnt(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getGuideListCnt()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//채팅방 리스트 조회
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.getGuideListCnt(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getGuideListCnt()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 사용자가이드 관리 > 사용자가이드 수정
	 * @return
	 */
	@RequestMapping(value="/guideEdit.html", method = RequestMethod.GET)
	public ModelAndView guideEdit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "guideEdit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		String	actionType = (String)params.get("actionType");
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reqParams", params);
		
		if("modify".equalsIgnoreCase(actionType)) {
			// 사용자가이드 내용 조회
			//Map<String,Object> inputMap = new HashMap<String,Object>();
			//inputMap.put("menuId",(String)params.get("menuId"));
			Map<String,Object> guide = adminDao.selectGuide(params);

			// 모델 생성
			model.put("guide", guide);
		}

		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("admin/guidemgr/admin_guideEdit");
		modelAndView.addAllObjects(model);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "guideEdit()");
		log.info("{}","====================== Admin ========================\n");

		return modelAndView;
	}
	
	/**
	 * 사용자가이드 관리 : 사용자가이드 저장
	 * @return
	 */
	@RequestMapping(value="/saveGuide.html", method = RequestMethod.POST)
	public ModelAndView saveGuide(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "saveGuide()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//사용자가이드 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.saveGuide(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveGuide()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 사용자가이드 관리 : 사용자가이드 삭제
	 * @return
	 */
	@RequestMapping(value="/deleteGuide.html", method = RequestMethod.POST)
	public ModelAndView deleteGuide(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "deleteGuide()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//사용자가이드 저장
		ModelAndView modelAndView;
		try {
			Map<String,Object> returnMap = this.adminService.deleteGuide(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteGuide()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	/**
	 * 회원관리 > 회원정보 > 포인트 부여 서비스
	 * @return
	 */
	@RequestMapping(value="/givePoint.html", method = RequestMethod.POST)
	public ModelAndView givePoint(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "givePoint()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//보너스 포인트 부여 서비스
			Map<String,Object> returnMap = this.adminService.givePoint(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "givePoint()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	
	/**
	 * 메세지 관리 > 최근 메세지 검색
	 * @return
	 */
	@RequestMapping(value="/messageBoxList.html", method = RequestMethod.GET)
	public ModelAndView messageBoxList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "messageBoxList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
				
		makePaging(params);
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMsgBoxList(params);
		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/msgmgr/admin_messageBoxList");
		modelAndView.addAllObjects(returnMap);
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "messageBoxList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 메세지 관리 > 최근 메세지 검색
	 * @return
	 */
	@RequestMapping(value="/messageBoxListConvers.html", method = RequestMethod.GET)
	public ModelAndView messageBoxListConvers(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "messageBoxConvers()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
				
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getMsgBoxConversationByFriendsId(params);
		returnMap.put("reqParams", params);
		
		// 회원정보 조회
		CommReqVO vo = new CommReqVO();
		vo.setgMemberId(Integer.parseInt(params.get("senderId").toString()));
		returnMap.put("sender", membershipDao.selectMemberByMemberId(vo));
		
		vo.setgMemberId(Integer.parseInt(params.get("receiverId").toString()));
		returnMap.put("receiver", membershipDao.selectMemberByMemberId(vo));
					
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/msgmgr/admin_messageBoxListConvers");
		modelAndView.addAllObjects(returnMap);
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "messageBoxConvers()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 메세지 관리 > 최근 메세지 검색
	 * @return
	 */
	@RequestMapping(value="/delMsgArr.html", method = RequestMethod.POST)
	public ModelAndView delMsgArr(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "delMsgArr()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String msgIds = params.get("msgIds") + "";
			params.put("msgIds", msgIds.split(","));
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.doDelMsgArr(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "delMsgArr()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**
	 * 메세지 관리 > 최근 메세지 검색
	 * @return
	 */
	@RequestMapping(value="/updateMsgReadYn.html", method = RequestMethod.POST)
	public ModelAndView updateMsgReadYn(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "updateMsgReadYn()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String msgId = params.get("msgId") + "";
			
			//로그인 서비스 호출
			int updateCnt = msgBoxDao.updateMsgAsReadYNToogleByMsgId(Long.parseLong(msgId));
			Locale gLocale = Locale.KOREAN;
			Map<String,Object> returnMap = new HashMap<String,Object>();
			if(updateCnt > 0) {
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search", gLocale),
						msgUtil.getMsgText("comm.success.search", gLocale));
				returnMap.put("result", result);
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("comm.fail.search", gLocale),
						msgUtil.getMsgText("comm.fail.search", gLocale));
				returnMap.put("result", result);
			}
			
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "updateMsgReadYn()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	
	
	/**
	 * 메세지 관리 > 쪽지 대화 목록 - 회원 목록 추출
	 * @return
	 */ 
	@RequestMapping(value="/messageBoxConversation.html", method = RequestMethod.GET)
	public ModelAndView messageBoxConversation(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "messageBoxConversation()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
//				
//		makePaging(params);
//		
//		// 요청 파라미터 추가
//		Map<String,Object> returnMap = this.adminService.getMsgBoxConversationFriends(params);
//		returnMap.put("reqParams", params); 
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/msgmgr/admin_messageBoxConversation");
//		modelAndView.addAllObjects(returnMap);
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "messageBoxConversation()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 쪽지 관리 > 쪽지 발송 회원 목록 조회
	 * @return
	 */
	@RequestMapping(value="/getMsgBoxConversationFriends.html", method = RequestMethod.POST)
	public ModelAndView getMsgBoxConversationFriends(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getMsgBoxConversationFriends()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//회원목록 리스트 조회
		ModelAndView modelAndView;
		try {
			makePaging(params);
			// 요청 파라미터 추가
			Map<String,Object> returnMap = this.adminService.getMsgBoxConversationFriends(params);
			returnMap.put("reqParams", params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMsgBoxConversationFriends()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 쪽지 관리 > 인원별 대화 목록 조회
	 * @return
	 */
	@RequestMapping(value="/getMsgBoxConversationByFriendsId.html", method = RequestMethod.POST)
	public ModelAndView getMsgBoxConversationByFriendsId(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "getMsgBoxConversationByFriendsId()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//회원목록 리스트 조회
		ModelAndView modelAndView;
		try {
			// 요청 파라미터 추가
			Map<String,Object> returnMap = this.adminService.getMsgBoxConversationByFriendsId(params);
			returnMap.put("reqParams", params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap);
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
		}

		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMsgBoxConversationByFriendsId()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 게시판 관리 > 1:1 문의사항 목록
	 * @return
	 */ 
	@RequestMapping(value="/manToManList.html", method = RequestMethod.GET)
	public ModelAndView manToManList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","====================== Admin ========================");
		log.info("[{} START]", "manToManList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
				
		makePaging(params);
		
		Map<String, String> codes = new HashMap<String, String>();
		codes.put("M01001", "버그신고");
		codes.put("M01002", "서비스문의");
		codes.put("M01003", "기능문의");
		codes.put("M01004", "제휴문의");
		codes.put("M01005", "기타");
		
		
		Map<String, String> status = new HashMap<String, String>();
		status.put("0", "요청");
		status.put("1", "처리중");
		status.put("2", "완료");
		status.put("3", "보류");
		
		// 요청 파라미터 추가
		Map<String,Object> returnMap = this.adminService.getManToManQuestionsList(params);
		returnMap.put("reqParams", params);
		returnMap.put("codes", codes);
		returnMap.put("status", status);
		
		//회원목록 리스트 조회
		ModelAndView modelAndView = new ModelAndView("admin/boardmgr/admin_manToManQuestionList");
		modelAndView.addAllObjects(returnMap);
		 
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "manToManList()");
		log.info("{}","====================== Admin ========================\n");
		
		return modelAndView;
	}
	
	/**
	 * 게시판 관리 > 1:1 문의 삭제
	 * @return
	 */
	@RequestMapping(value="/delManToManArr.html", method = RequestMethod.POST)
	public ModelAndView delManToManArr(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "delManToManArr()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			String mIds = params.get("mIds") + "";
			params.put("mIds", mIds.split(","));
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.doDelManToManArr(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "delManToManArr()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**
	 * 게시판 관리 > 1:1 문의 관리자 저장
	 * @return
	 */
	@RequestMapping(value="/updateManToMan.html", method = RequestMethod.POST)
	public ModelAndView updateManToMan(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "updateManToMan()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = adminService.doUpdateManToMan(params);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, "ko");
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "delManToManArr()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
}