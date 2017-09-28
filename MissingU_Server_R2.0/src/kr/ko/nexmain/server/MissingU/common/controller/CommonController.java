package kr.ko.nexmain.server.MissingU.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.mail.Member;
import kr.ko.nexmain.server.MissingU.common.mail.MuMailNotifier;
import kr.ko.nexmain.server.MissingU.common.model.MailSendParam;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/common")
public class CommonController {
	
	protected static Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private CommonService commonService;
	@Autowired
	private MsgUtil msgUtil;
	@Autowired
	private MuMailNotifier mailSender;
	
	/**********************************************
	 * 포인트 부족 에러 리턴
	***********************************************/
	@RequestMapping(value="/lackOfPointError.html")
	public ModelAndView lackOfPointError(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "lackOfPointError()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		ModelAndView modelAndView;
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		responseMap = msgUtil.getAuthErrorMapByLackOfPoint(responseMap);
		modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "lackOfPointError()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	/**********************************************
	 * 포인트 사용 컨펌 메세지 리턴
	***********************************************/
	@RequestMapping(value="/confirmUsingPoint.html")
	public ModelAndView confirmUsingPoint(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "confirmUsingPoint()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		ModelAndView modelAndView;
		
		Map<String,Object> inputMap = new HashMap<String, Object>();
		inputMap.put("requestURI", params.get("requestURI"));
		inputMap.put("memberId", Integer.parseInt((String)params.get("gMemberId")));
		Map<String,Object> returnMap = commonService.getMemberPointAndNeedPoint(inputMap, (String)params.get("gLang"));
		
		modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "confirmUsingPoint()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	/**********************************************
	 * 회원가입 메일 인증
	***********************************************/
	@RequestMapping(value="/memberJoinCert.html")
	public ModelAndView memberJoinCert(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "memberJoinCert()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> inputMap = new HashMap<String, Object>();
		inputMap.put("gMemberId", Integer.parseInt((String)params.get("gMemberId")));
		
		ModelAndView modelAndView = new ModelAndView("common/simple_result");
		
		//String resultMsg = msgUtil.getPropMsg("theshop.checkplusSuccess.msg");
		String resultMsg = "SUCCESS!";
		modelAndView.addObject("resultMsg", resultMsg);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "memberJoinCert()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	/**********************************************
	 * 메일발송 테스트
	***********************************************/
	@RequestMapping(value="/sendEmailTest.html")
	public ModelAndView sendEmailTest(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendEmailTest()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		String emailTo = (params.get("email") == null) ? "hyuni1982@gmail.com" : (String)params.get("email");
		log.info("Email TO : {}", emailTo);
		
		MailSendParam param = new MailSendParam(1000001011, emailTo, "이람파");
		param.setEmailType(Constants.emailType.MEMBERSHIP_JOIN_CONFIRM);
		param.setLang("ko");
		mailSender.sendEmail(param);
		
		param = new MailSendParam(1000001011, emailTo, "이람파");
		param.setEmailType(Constants.emailType.MEMBERSHIP_JOIN_CONFIRM);
		param.setLang("ja");
		mailSender.sendEmail(param);
		
		param = new MailSendParam(1000001011, emailTo, "이람파");
		param.setLoginPw("tempPasswd");
		param.setEmailType(Constants.emailType.TEMP_PASSWORD);
		param.setLang("ko");
		mailSender.sendEmail(param);
		
		param = new MailSendParam(1000001011, emailTo, "이람파");
		param.setLoginPw("tempPasswd");
		param.setEmailType(Constants.emailType.TEMP_PASSWORD);
		param.setLang("ja");
		mailSender.sendEmail(param);
		
		ModelAndView modelAndView = new ModelAndView("common/simple_result");
		
		//String resultMsg = msgUtil.getPropMsg("theshop.checkplusSuccess.msg");
		String resultMsg = "이메일 발송 테스트 완료!";
		modelAndView.addObject("resultMsg", resultMsg);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendEmailTest()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	
	
	
}