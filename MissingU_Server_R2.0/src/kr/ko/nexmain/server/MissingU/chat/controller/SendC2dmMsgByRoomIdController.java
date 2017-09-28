package kr.ko.nexmain.server.MissingU.chat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ko.nexmain.server.MissingU.chat.service.ChatMainService;
import kr.ko.nexmain.server.MissingU.common.Constants;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SendC2dmMsgByRoomIdController implements Controller {

	private ChatMainService chatMainService;
	String roomId;
	String msg;
	String memberId;

	public void setChatMainService(ChatMainService chatMainService) {
		this.chatMainService = chatMainService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultCode;
		
		//입력전문 검증(필수값)
		if(validateParameters(request)) {
			resultCode = this.chatMainService.sendC2dmMsgByChatRoomId(roomId, msg, memberId, Constants.ActionType.CHAT_MSG);
		} else {
			resultCode = Constants.ReturnCode.PARAM_ERROR;
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("resultCode", resultCode);

		// 반환값인 ModelAndView 인스턴스 생성
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("CommonXmlResultPage");
		modelAndView.addAllObjects(model);

		return modelAndView;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public boolean validateParameters(HttpServletRequest request) {
		String roomId = request.getParameter("room_id");
		String msg = request.getParameter("msg");
		String memberId = request.getParameter("member_id");
		
		if(StringUtils.hasLength(roomId) 
				&& StringUtils.hasLength(msg)
				&& StringUtils.hasLength(memberId)) {
			this.roomId = roomId;
			this.msg = msg;
			this.memberId = memberId;
			return true;
		} else {
			return false;
		}
		
	}
}