package kr.ko.nexmain.server.MissingU.theshop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.theshop.model.BeforeGooglePayReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.GooglePayReqVO;
import kr.ko.nexmain.server.MissingU.theshop.service.TheShopService;

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
@RequestMapping("/missingu/pay")
public class PayController {
	
	protected static Logger log = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private TheShopService theShopService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MsgUtil msgUtil;

	@RequestMapping(value="/afterDaoupay.html", method = RequestMethod.GET)
	public ModelAndView afterDaoupay(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "afterDaoupay()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		ModelAndView modelAndView = new ModelAndView("theshop/daoupay_result");
		
		theShopService.afterDaoupay(params);
		
		String resultMsg = Constants.DaouPay.SUCCESS_MSG;
		modelAndView.addObject("resultMsg", resultMsg);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "afterDaoupay()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	/**********************************************
	구글 Pay 전처리
	***********************************************/
	@RequestMapping(value="/beforeGooglePay.html")
	public ModelAndView beforeGooglePay(@Valid BeforeGooglePayReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "beforeGooglePay()");
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
			
			Map<String,Object> returnMap = theShopService.beforeGooglePay(inputVO);
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
		log.info("[{} END]", "beforeGooglePay()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	구글 Pay 후처리
	***********************************************/
	@RequestMapping(value="/afterGooglePay.html")
	public ModelAndView afterGooglePay(@Valid GooglePayReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "afterGooglePay()");
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
			
			Map<String,Object> returnMap = theShopService.afterGooglePay(inputVO);
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
		log.info("[{} END]", "afterGooglePay()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	

}