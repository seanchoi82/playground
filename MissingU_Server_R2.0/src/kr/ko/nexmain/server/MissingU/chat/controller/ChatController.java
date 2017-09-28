package kr.ko.nexmain.server.MissingU.chat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.chat.model.CreateRoomReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.KickOutReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.RoomInOutReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.SendChatMsgReqVO;
import kr.ko.nexmain.server.MissingU.chat.service.ChatService;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/chat")
public class ChatController {
	
	protected static Logger log = LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private ChatService chatService;
	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	(채팅) 채팅방 목록 조회
	***********************************************/
	@RequestMapping(value="/getRoomList.html")
	public ModelAndView getRoomList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getRoomList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//바인딩에러 체크
		if (bindingResult.hasErrors()) {
			responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
			ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		//채팅방 리스트 조회
		Map<String,Object> returnMap = this.chatService.getRoomList(inputVO);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getRoomList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(채팅) 채팅방 생성
	***********************************************/
	@RequestMapping(value="/createRoom.html")
	public ModelAndView createRoom(@Valid CreateRoomReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "createRoom()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//바인딩에러 체크
		if (bindingResult.hasErrors()) {
			responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
			ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		//채팅방 생성
		Map<String,Object> returnMap = this.chatService.createRoom(inputVO);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "createRoom()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(채팅) 채팅방 입장
	***********************************************/
	@RequestMapping(value="/roomIn.html")
	public ModelAndView roomIn(@Valid RoomInOutReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "roomIn()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//바인딩에러 체크
		if (bindingResult.hasErrors()) {
			responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
			ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		//채팅방 생성
		Map<String,Object> returnMap = this.chatService.doRoomInProcess(inputVO);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "roomIn()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(채팅) 채팅방 퇴장
	***********************************************/
	@RequestMapping(value="/roomOut.html")
	public ModelAndView roomOut(@Valid RoomInOutReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "roomOut()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//바인딩에러 체크
		if (bindingResult.hasErrors()) {
			responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
			ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		//채팅방 생성
		Map<String,Object> returnMap = this.chatService.doRoomOutProcess(inputVO);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "roomOut()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(채팅) 강제 퇴장
	***********************************************/
	@RequestMapping(value="/kickOut.html")
	public ModelAndView kickOut(@Valid KickOutReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "kickOut()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//바인딩에러 체크
		if (bindingResult.hasErrors()) {
			responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
			ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		//채팅방 생성
		Map<String,Object> returnMap = this.chatService.doKickOutProcess(inputVO);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "kickOut()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(채팅) 채팅 메세지 전송
	***********************************************/
	@RequestMapping(value="/sendChatMsg.html")
	public ModelAndView sendChatMsg(@Valid SendChatMsgReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendChatMsg()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		//바인딩에러 체크
		if (bindingResult.hasErrors()) {
			responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
			ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		//채팅방 생성
		Map<String,Object> returnMap = this.chatService.sendChatMsg(inputVO);
		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendChatMsg()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
}