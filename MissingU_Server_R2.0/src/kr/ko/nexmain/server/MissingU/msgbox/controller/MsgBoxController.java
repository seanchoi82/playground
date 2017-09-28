package kr.ko.nexmain.server.MissingU.msgbox.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.msgbox.model.DeleteMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.DeleteMsgboxReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversSendVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgListReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.OpenMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.SendMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.service.MsgBoxService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/msgbox")
public class MsgBoxController {
	
	protected static Logger log = LoggerFactory.getLogger(MsgBoxController.class);

	@Autowired
	private MsgBoxService msgBoxService;
	@Autowired
	private MsgUtil msgUtil;
	
	
	
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(시작)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/***
	 * (쪽지함) 쪽지 목록 
	 * @param inputVO
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getConversMsgList.html")
	public ModelAndView getConversMsgList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getConversMsgList()");
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
			
			//채팅방 리스트 조회
			Map<String,Object> returnMap = this.msgBoxService.getConversMsgBoxList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getConversMsgList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/***
	 * (쪽지함) 쪽지 대화 목록 
	 * @param inputVO
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getConversMsgConversationList.html")
	public ModelAndView getConversMsgConversationList(@Valid MsgBoxConversVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getConversMsgList()");
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
			
			//채팅방 리스트 조회
			Map<String,Object> returnMap = this.msgBoxService.getConversMsgConversationList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getConversMsgList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	/**********************************************
	(쪽지함) 쪽지 전송
	***********************************************/
	@RequestMapping(value="/sendConversMsg.html")
	public ModelAndView sendConversMsg(@Valid MsgBoxConversSendVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendConversMsg()");
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
			
			//쪽지전송
			Map<String,Object> returnMap = this.msgBoxService.sendConversMsg(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendConversMsg()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 쪽지함 삭제
	***********************************************/
	@RequestMapping(value="/deleteConversMsgbox.html")
	public ModelAndView deleteConversMsgbox(@Valid MsgBoxConversVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "deleteConversMsgbox()");
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
			
			//쪽지함 삭제 서비스 호출
			Map<String,Object> returnMap = this.msgBoxService.deleteConversMsg(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteConversMsgbox()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(끝)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	
	
	/**********************************************
	(쪽지함) 쪽지함 리스트 조회
	***********************************************/
	@RequestMapping(value="/getMsgBoxList.html")
	public ModelAndView getMsgBoxList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getMsgBoxList()");
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
			
			//채팅방 리스트 조회
			Map<String,Object> returnMap = this.msgBoxService.getMsgBoxList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMsgBoxList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 쪽지 전송
	***********************************************/
	@RequestMapping(value="/sendMsg.html")
	public ModelAndView sendMsg(@Valid SendMsgReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendMsg()");
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
			
			//쪽지전송
			Map<String,Object> returnMap = this.msgBoxService.sendMsg(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendMsg()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 쪽지 리스트 조회
	***********************************************/
	@RequestMapping(value="/getMsgList.html")
	public ModelAndView getMsgList(@Valid MsgListReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getMsgList()");
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
			
			//쪽지 리스트 조회
			Map<String,Object> returnMap = this.msgBoxService.getMsgList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getMsgList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 쪽지함 삭제
	***********************************************/
	@RequestMapping(value="/deleteMsgbox.html")
	public ModelAndView deleteMsgbox(@Valid DeleteMsgboxReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "deleteMsgbox()");
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
			
			//쪽지함 삭제 서비스 호출
			Map<String,Object> returnMap = this.msgBoxService.deleteMsgbox(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteMsgbox()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 쪽지함 삭제
	***********************************************/
	@RequestMapping(value="/deleteMsg.html")
	public ModelAndView deleteMsg(@Valid DeleteMsgReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "deleteMsg()");
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
			
			//쪽지 삭제 서비스 호출
			Map<String,Object> returnMap = this.msgBoxService.deleteMsg(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "deleteMsg()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 내 친구 리스트 조회
	***********************************************/
	@RequestMapping(value="/myFriends.html")
	public ModelAndView myFriends(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "myFriends()");
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
			
			//쪽지함 삭제 서비스 호출
			Map<String,Object> returnMap = this.msgBoxService.getMyFriendsList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "myFriends()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 내 쪽지 리스트 조회
	***********************************************/
	@RequestMapping(value="/myMsgList.html")
	public ModelAndView myMsgList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "myMsgList()");
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
			
			//내 쪽지 리스트 조회
			Map<String,Object> returnMap = this.msgBoxService.myMsgList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "myMsgList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(쪽지함) 쪽지 개봉
	***********************************************/
	@RequestMapping(value="/openMsg.html")
	public ModelAndView openMsg(@Valid OpenMsgReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "openMsg()");
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
			
			//내 쪽지 리스트 조회
			Map<String,Object> returnMap = this.msgBoxService.openMsg(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "openMsg()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	
}