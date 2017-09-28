package kr.ko.nexmain.server.MissingU.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.common.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/admin/chat")
public class AdminChatServerController extends BaseController {

	protected static Logger log = LoggerFactory.getLogger(AdminChatServerController.class);
	
	
	@RequestMapping(value="/chatServer.html")
	public ModelAndView msgConversList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		
		ModelAndView modelAndView = new ModelAndView("admin/chatservermgr/admin_normalChatServer");
		
		endLog(log);
		return modelAndView;
	}
	
	@RequestMapping(value="/groupList.html")
	public ModelAndView groupList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		
		
		endLog(log);
		return null;
	}
	
}
