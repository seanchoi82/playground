package kr.ko.nexmain.server.MissingU.friends.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsEditReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SendGiftReqVO;
import kr.ko.nexmain.server.MissingU.friends.service.FriendsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/friends")
public class FriendsController {
	
	protected static Logger log = LoggerFactory.getLogger(FriendsController.class);

	@Autowired
	private FriendsService friendsService;
	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	(친구찾기) 친구 검색
	***********************************************/
	@RequestMapping(value="/searchFriends.html")
	public ModelAndView searchFriends(@Valid SearchFriendsReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "searchFriends()");
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
			
			//친구 검색 서비스 호출
			Map<String,Object> returnMap = this.friendsService.getMemberListForSearchFriends(inputVO);
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
		log.info("[{} END]", "searchFriends()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(친구찾기) 친구 추가
	***********************************************/
	@RequestMapping(value="/addFriend.html")
	public ModelAndView addFriend(@Valid FriendsEditReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "addFriend()");
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
			
			//친구 추가 서비스 호출
			Map<String,Object> returnMap = this.friendsService.addFriend(inputVO);
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
		log.info("[{} END]", "addFriend()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(친구찾기) 친구 삭제
	***********************************************/
	@RequestMapping(value="/deleteFriend.html")
	public ModelAndView deleteFriend(@Valid FriendsEditReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "deleteFriend()");
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
			
			//친구 추가 서비스 호출
			Map<String,Object> returnMap = this.friendsService.deleteFriend(inputVO);
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
		log.info("[{} END]", "deleteFriend()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(친구찾기) 친구 상세정보 조회
	***********************************************/
	@RequestMapping(value="/detailInfo.html")
	public ModelAndView detailInfo(@Valid FriendsEditReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "detailInfo()");
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
			
			//친구 상세정보 조회 서비스 호출
			Map<String,Object> returnMap = this.friendsService.getDetailInfo(inputVO);
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
		log.info("[{} END]", "detailInfo()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	윙크 보내기
	***********************************************/
	@RequestMapping(value="/sendWink.html")
	public ModelAndView sendWink(@Valid FriendsEditReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendWink()");
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
			
			//친구 상세정보 조회 서비스 호출
			Map<String,Object> returnMap = this.friendsService.sendWink(inputVO);
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
		log.info("[{} END]", "sendWink()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	선물 보내기
	***********************************************/
	@RequestMapping(value="/sendGift.html")
	public ModelAndView sendGift(@Valid SendGiftReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendGift()");
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
			
			//친구 상세정보 조회 서비스 호출
			Map<String,Object> returnMap = this.friendsService.sendGift(inputVO);
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
		log.info("[{} END]", "sendGift()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
}