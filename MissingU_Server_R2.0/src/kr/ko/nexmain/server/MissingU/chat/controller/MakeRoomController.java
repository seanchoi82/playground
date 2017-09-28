package kr.ko.nexmain.server.MissingU.chat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.service.ChatMainService;
import kr.ko.nexmain.server.MissingU.common.Constants;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class MakeRoomController implements Controller{

	private ChatMainService chatMainService;
	private Room room;

	public void setChatMainService(ChatMainService chatMainService) {
		this.chatMainService = chatMainService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		//입력전문 검증(필수값)
		if(validateParameters(request)) {
			model = this.chatMainService.doMakeRoomProcess(room);
		} else {
			model.put("resultCode", Constants.ReturnCode.PARAM_ERROR);
		}
		
		// 반환값인 ModelAndView 인스턴스 생성
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("MakeRoomXmlResultPage");
		modelAndView.addAllObjects(model);

		return modelAndView;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public boolean validateParameters(HttpServletRequest request) {
		String memberId	= request.getParameter("id");
		String roomName = request.getParameter("room_name");
		String memo 	= request.getParameter("memo");
		String roomPass = request.getParameter("room_pass");
		String maxUser 	= request.getParameter("max_user");
		
		if(StringUtils.hasLength(memberId) 
				&& StringUtils.hasLength(roomName)
				&& StringUtils.hasLength(maxUser)) {
			room = new Room();
			room.setMemberId(memberId);
			room.setRoomName(roomName);
			room.setMaxUser(maxUser);
			room.setCurUser("1");
			
			if(StringUtils.hasLength(roomPass)) {
				room.setRoomPass(roomPass);
			} else {
				room.setRoomPass("");
			}
			
			if(StringUtils.hasLength(memo)) {
				room.setMemo(memo);
			} else {
				room.setMemo("");
			}
			
			return true;
		} else {
			return false;
		}
		
	}
}