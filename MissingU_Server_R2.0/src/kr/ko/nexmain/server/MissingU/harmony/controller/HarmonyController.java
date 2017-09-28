package kr.ko.nexmain.server.MissingU.harmony.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsEditReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.friends.service.FriendsService;
import kr.ko.nexmain.server.MissingU.harmony.model.DetailInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.model.TotalInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.service.HarmonyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/harmony")
public class HarmonyController {
	
	protected static Logger log = LoggerFactory.getLogger(HarmonyController.class);

	@Autowired
	private HarmonyService harmonyService;
	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	내 간편 궁합정보 조회
	***********************************************/
	@RequestMapping(value="/mySimpleInfo.html")
	public ModelAndView mySimpleInfo(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "mySimpleInfo()");
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
			
			//내 간단 정보 서비스 호출
			Map<String,Object> returnMap = this.harmonyService.getMySimpleInfo(inputVO);
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
		log.info("[{} END]", "mySimpleInfo()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	종합 궁합정보 조회
	***********************************************/
	@RequestMapping(value="/totalInfo.html")
	public ModelAndView totalInfo(@Valid TotalInfoReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "totalInfo()");
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
			
			//친구 검색 서비스 호출
			Map<String,Object> returnMap = this.harmonyService.getTotalInfo(inputVO);
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
		log.info("[{} END]", "totalInfo()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	상세 궁합정보 조회
	***********************************************/
	@RequestMapping(value="/detailInfo.html")
	public ModelAndView detailInfo(@Valid DetailInfoReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "detailInfo()");
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
			
			//친구 검색 서비스 호출
			Map<String,Object> returnMap = this.harmonyService.getDetailInfo(inputVO);
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
		log.info("[{} END]", "detailInfo()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
}