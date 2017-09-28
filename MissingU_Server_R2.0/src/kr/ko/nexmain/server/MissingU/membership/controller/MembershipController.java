package kr.ko.nexmain.server.MissingU.membership.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.security.AES256;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.model.GcmUpdateReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.IssueTempPassReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.ItemSndRcvHistReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.LoginReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.membership.model.MemberRegisterReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.PointPreCheckReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.SaveMemberPhotoReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateFmJoinYnReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateMemberReqVO;
import kr.ko.nexmain.server.MissingU.membership.service.MembershipService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/membership")
public class MembershipController {
	
	protected static Logger log = LoggerFactory.getLogger(MembershipController.class);

	@Autowired
	private MembershipService membershipService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	(회원가입) 로그인 처리
	***********************************************/
	@RequestMapping(value="/login.html")
	public ModelAndView login(@Valid LoginReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "login()");
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
			Map<String,Object> returnMap = this.membershipService.doLogin(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "login()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(회원가입) 회원생성
	***********************************************/
	@RequestMapping(value="/createMember.html")
	public ModelAndView createMember(@Valid MemberRegisterReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "createMember()");
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
			
			//회원가입 서비스 호출
			Map<String,Object> returnMap = this.membershipService.doCreateMemeber(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "createMember()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(회원가입) 회원정보 업데이트
	***********************************************/
	@RequestMapping(value="/updateMember.html")
	public ModelAndView updateMember(@Valid UpdateMemberReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "updateMember()");
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
			
			//회원가입 서비스 호출
			Map<String,Object> returnMap = this.membershipService.doUpdateMemeber(inputVO);
			Result result = (Result)returnMap.get("result");
			
			// 실명인증 처리되는 회원만 본인인증 완료 처리 해줌(레거시와 비교대상이 실명 데이터)
			if(Constants.ReturnCode.SUCCESS.equals(result.getRsltCd()) && inputVO.getName() != null && inputVO.getName().length() > 0) {
				//본인인증여부 업데이트
				MemberAttr input = new MemberAttr();
				input.setMemberId(inputVO.getgMemberId());
				input.setAttrName("oneselfCertification");
				input.setAttrValue("Y");
				this.membershipService.updateMemberAttr(input);
	    	}
			
			
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "createMember()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(마이페이지) 마이페이지 메인
	***********************************************/
	@RequestMapping(value="/getMyPageMain.html")
	public ModelAndView getMyPageMain(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getMyPageMain()");
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
			
			//회원가입 서비스 호출
			Map<String,Object> returnMap = this.membershipService.getMyPageMain(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMyPageMain()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(마이페이지) 마이페이지 조회
	***********************************************/
	@RequestMapping(value="/getMyPageInfo.html")
	public ModelAndView getMyPageInfo(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getMyPageInfo()");
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
			
			//회원가입 서비스 호출
			Map<String,Object> returnMap = this.membershipService.doGetMyPageInfo(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMyPageInfo()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(마이페이지) 회원사진 신규 업로드/업데이트
	***********************************************/
	@RequestMapping(value="/saveMemberPhoto.html", method = RequestMethod.POST)
	public ModelAndView saveMemberPhoto(@Valid SaveMemberPhotoReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "saveMemberPhoto()");
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
			
			Map<String,Object> returnMap = this.membershipService.doSaveMemberPhoto(inputVO, request);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveMemberPhoto()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	GCM 정보 업데이트
	***********************************************/
	@RequestMapping(value="/gcmUpdate.html")
	public ModelAndView gcmUpdate(@Valid GcmUpdateReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "gcmUpdate()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//친구 추가 서비스 호출
			Map<String,Object> returnMap = this.membershipService.gcmUpdate(inputVO);
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
		log.info("[{} END]", "gcmUpdate()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	회원 계정 삭제
	***********************************************/
	@RequestMapping(value="/deleteMember.html")
	public ModelAndView deleteMember(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "deleteMember()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//친구 추가 서비스 호출
			Map<String,Object> returnMap = this.membershipService.deleteMember(inputVO);
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
	아이템 수신내역 조회
	***********************************************/
	@RequestMapping(value="/itemReceiveHist.html")
	public ModelAndView itemReceiveHist(@Valid ItemSndRcvHistReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "itemReceiveHist()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//아이템 수신내역 조회 서비스 호출
			Map<String,Object> returnMap = this.membershipService.itemReceiveHist(inputVO);
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
		log.info("[{} END]", "itemReceiveHist()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	아이템 송신내역 조회
	***********************************************/
	@RequestMapping(value="/itemSendHist.html")
	public ModelAndView itemSendHist(@Valid ItemSndRcvHistReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "itemSendHist()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//아이템 수신내역 조회 서비스 호출
			Map<String,Object> returnMap = this.membershipService.itemSendHist(inputVO);
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
		log.info("[{} END]", "itemSendHist()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	임시 패스워드 발급
	***********************************************/
	@RequestMapping(value="/issueTempPass.html")
	public ModelAndView issueTempPass(@Valid IssueTempPassReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "issueTempPass()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//아이템 수신내역 조회 서비스 호출
			Map<String,Object> returnMap = this.membershipService.issueTempPass(inputVO);
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
		log.info("[{} END]", "issueTempPass()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	포인트 내역 조회
	***********************************************/
	@RequestMapping(value="/pointUsageHist.html")
	public ModelAndView pointUsageHist(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "pointUsageHist()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//아이템 수신내역 조회 서비스 호출
			Map<String,Object> returnMap = this.membershipService.pointUsageHist(inputVO);
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
		log.info("[{} END]", "pointUsageHist()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(공통) MultipartRequest 테스트
	***********************************************/
	@RequestMapping(value="/multiTest.html", method = RequestMethod.GET)
	public ModelAndView apkAdd() {
		ModelAndView modelAndView = new ModelAndView("common/MultipartRequestTest");
		modelAndView.addObject(new SaveMemberPhotoReqVO());

		return modelAndView;
	}
	
	/**********************************************
	 * 포인트 사전 체크(포인트 사용하는 8개 서비스)
	***********************************************/
	@RequestMapping(value="/pointPreCheck.html")
	public ModelAndView pointPreCheck(@Valid PointPreCheckReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "pointPreCheck()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		Map<String,Object> responseMap = new HashMap<String, Object>();

		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//보유 포인트 및 사용 포인트 조회
			Map<String,Object> inputMap = new HashMap<String, Object>();
			inputMap.put("requestURI", inputVO.getRequestURI());
			inputMap.put("memberId", inputVO.getgMemberId());
			inputMap.put("msgId", inputVO.getMsgId());
			inputMap.put("roomId", inputVO.getRoomId());
			
			Map<String,Object> returnMap = commonService.getMemberPointAndNeedPoint(inputMap, inputVO.getgLang());
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
		log.info("[{} END]", "pointPreCheck()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	/**********************************************
	페이스매치 참여여부 업데이트
	***********************************************/
	@RequestMapping(value="/updateFmJoinYn.html")
	public ModelAndView updateFmJoinYn(@Valid UpdateFmJoinYnReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "updateFmJoinYn()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//친구 추가 서비스 호출
			Map<String,Object> returnMap = this.membershipService.updateFmJoinYn(inputVO);
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
		log.info("[{} END]", "updateFmJoinYn()");
		log.info("{}","==============================================");
		return modelAndView;
	}

	
}