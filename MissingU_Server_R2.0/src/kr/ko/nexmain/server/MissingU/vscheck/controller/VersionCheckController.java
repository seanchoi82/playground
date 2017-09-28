package kr.ko.nexmain.server.MissingU.vscheck.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.vscheck.service.VersionCheckService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/vscheck")
public class VersionCheckController {

	protected static Logger log = LoggerFactory.getLogger(VersionCheckController.class);
	
	@Autowired
	private VersionCheckService versionCheckService;
	@Autowired
	private MsgUtil msgUtil;
	
	
	/**********************************************
	(환경설정) 사용자가이드 조회
	***********************************************/
	@RequestMapping(value="/getVersionCheck.html")
	public ModelAndView getVersionCheck(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getUserGuide()"); 
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				if(inputVO.getgLang() == null)
					inputVO.setgLang("ko");
				
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//공지사항 조회 호출
			Map<String,Object> returnMap = this.versionCheckService.getUpgradeVersionInfoAndEMRNotification(inputVO, true);
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
		log.info("[{} END]", "getUserGuide()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(환경설정) 사용자가이드 조회
	***********************************************/
	@RequestMapping(value="/getVersionCheckRandomChat.html")
	public ModelAndView getVersionCheckRandomChat(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getUserGuide()"); 
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				if(inputVO.getgLang() == null)
					inputVO.setgLang("ko");
				
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//공지사항 조회 호출
			Map<String,Object> returnMap = this.versionCheckService.getUpgradeVersionInfoAndEMRNotificationRandomChat(inputVO, true);
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
		log.info("[{} END]", "getUserGuide()");
		log.info("{}","==============================================");
		return modelAndView;
	}
}
