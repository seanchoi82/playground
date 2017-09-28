package kr.ko.nexmain.server.MissingU.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.service.ChatMainService;
import kr.ko.nexmain.server.MissingU.common.Constants;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ChatRoomListController implements Controller{

	private ChatMainService chatMainService;
	private Room room;

	public void setChatMainService(ChatMainService chatMainService) {
		this.chatMainService = chatMainService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ChatRoom> chatRoomList = this.chatMainService.getChatRoomList();
		MessageSourceAccessor accessor = this.chatMainService.getMessageSourceAccessor();
		
		Map<String, Object> model = new HashMap<String, Object>();
		if(chatRoomList != null && !chatRoomList.isEmpty()) {
			model.put("resultCode", Constants.ReturnCode.SUCCESS);
			model.put("resultMsg", accessor.getMessage("chat.chatRoomList.return.success"));
			model.put("resultObjList", chatRoomList);
		} else {
			model.put("resultCode", Constants.ReturnCode.OTHER_ERROR);
			model.put("resultMsg", accessor.getMessage("chat.chatRoomList.return.otherError"));
		}
		
		// 반환값인 ModelAndView 인스턴스 생성
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ChatRoomListXmlResultPage");
		modelAndView.addAllObjects(model);

		return modelAndView;
	}
	
}