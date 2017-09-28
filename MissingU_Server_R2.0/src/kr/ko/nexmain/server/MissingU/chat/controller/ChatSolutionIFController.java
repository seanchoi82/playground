package kr.ko.nexmain.server.MissingU.chat.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.controller.BaseController;
import kr.ko.nexmain.server.MissingU.common.model.Result;
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
@RequestMapping("/missingu/chat/solution")
public class ChatSolutionIFController extends BaseController {

	protected static Logger log = LoggerFactory.getLogger(ChatSolutionIFController.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	(채팅) 채팅방 생성
	***********************************************/
	@RequestMapping(value="/pointUseCreateChatRoom.html")
	public ModelAndView createChatRoom(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Locale gLocale  = new Locale(params.get("gLang") + "");
		
		// 포인트 업데이트 성공시
		if(commonService.updatePointInfo(UTL.convertToInt(params.get("gMemberId"), 0), Constants.EventTypeCd.OUTCOME, "O201", -100, params.get("gLang") + "")) {
			//성공
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("chat.createRoom.ss.success", gLocale),
					msgUtil.getMsgText("chat.createRoom.ss.success", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}else{
			//실패
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("chat.createRoom.le.fail", gLocale),
					msgUtil.getMsgText("chat.createRoom.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
	
	/**********************************************
	(채팅) 채팅방 입장
	***********************************************/
	@RequestMapping(value="/pointUseChatRoomIn.html")
	public ModelAndView chatRoomIn(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Locale gLocale  = new Locale(params.get("gLang") + "");
		
		// 포인트 업데이트 성공시
		if(commonService.updatePointInfo(UTL.convertToInt(params.get("gMemberId"), 0), Constants.EventTypeCd.OUTCOME, "O202", -100, params.get("gLang") + "")) {
			//성공
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("chat.createRoom.ss.success", gLocale),
					msgUtil.getMsgText("chat.createRoom.ss.success", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}else{
			//실패
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("chat.createRoom.le.fail", gLocale),
					msgUtil.getMsgText("chat.createRoom.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		endLog(log);
		return  modelAndView;
	}
}
