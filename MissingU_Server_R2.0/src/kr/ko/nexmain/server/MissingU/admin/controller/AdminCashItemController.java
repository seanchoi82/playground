package kr.ko.nexmain.server.MissingU.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.admin.service.AdminCashService;
import kr.ko.nexmain.server.MissingU.admin.service.AdminService;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.controller.BaseController;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/admin/cash")
public class AdminCashItemController extends BaseController {
	
	protected static Logger log = LoggerFactory.getLogger(AdminCashItemController.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminCashService adminCashService;
	
	@RequestMapping(value="/winkDelete.html")
	public ModelAndView winkDelete(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		
		Map<String,Object> returnMap = adminCashService.deleteWink(params);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
	
	@RequestMapping(value="/winkList.html")
	public ModelAndView winkList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		makePaging(params);
		
		params.put("itemCd", Constants.ItemCode.WINK);
		
		Map<String,Object> returnMap = adminCashService.getWinkList(params);
		returnMap.put("reqParams", params); 
		
		ModelAndView modelAndView = new ModelAndView("admin/pointmgr/admin_winkList");
		modelAndView.addAllObjects(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
	
	@RequestMapping(value="/giftDelete.html")
	public ModelAndView giftDelete(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		
		Map<String,Object> returnMap = adminCashService.deleteGift(params);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
	
	@RequestMapping(value="/giftList.html")
	public ModelAndView giftList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		makePaging(params);
		
		params.put("itemCd", Constants.ItemCode.GIFT_FLOWER);
		
		Map<String,Object> returnMap = adminCashService.getGiftList(params);
		returnMap.put("reqParams", params); 
		
		ModelAndView modelAndView = new ModelAndView("admin/pointmgr/admin_giftList");
		modelAndView.addAllObjects(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
	
	@RequestMapping(value="/pointList.html")
	public ModelAndView pointList(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		makePaging(params);
		
		Map<String,Object> returnMap = adminCashService.getPointList(params);
		returnMap.put("reqParams", params); 
		
		ModelAndView modelAndView = new ModelAndView("admin/pointmgr/admin_pointList");
		modelAndView.addAllObjects(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
}
