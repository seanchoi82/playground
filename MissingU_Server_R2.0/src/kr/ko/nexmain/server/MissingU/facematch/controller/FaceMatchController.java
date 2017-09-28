package kr.ko.nexmain.server.MissingU.facematch.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.facematch.model.GetCandidatesReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.GetRankReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.UpdateResultReqVO;
import kr.ko.nexmain.server.MissingU.facematch.service.FaceMatchService;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.friends.service.FriendsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/facematch")
public class FaceMatchController {
	
	protected static Logger log = LoggerFactory.getLogger(FaceMatchController.class);

	@Autowired
	private FaceMatchService faceMatchService;
	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	(페이스매치) 후보조회
	***********************************************/
	@RequestMapping(value="/getCandidates.html")
	public ModelAndView getCandidates(@Valid GetCandidatesReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getCandidates()");
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
			
			//페이스매치 후보 조회 서비스 호출
			Map<String,Object> returnMap = this.faceMatchService.getCandidates(inputVO);
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
		log.info("[{} END]", "getCandidates()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(페이스매치) 결과 업데이트
	***********************************************/
	@RequestMapping(value="/updateResult.html")
	public ModelAndView updateResult(@Valid UpdateResultReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "updateResult()");
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
			
			//페이스매치 후보 조회 서비스 호출
			Map<String,Object> returnMap = this.faceMatchService.updateResult(inputVO);
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
		log.info("[{} END]", "updateResult()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
	/**********************************************
	(페이스매치) 순위조회
	***********************************************/
	@RequestMapping(value="/getRank.html")
	public ModelAndView getRank(@Valid GetRankReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getRank()");
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
			
			//페이스매치 후보 조회 서비스 호출
			Map<String,Object> returnMap = this.faceMatchService.getRank(inputVO);
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
		log.info("[{} END]", "getRank()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
}