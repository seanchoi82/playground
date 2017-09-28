package kr.ko.nexmain.server.MissingU.config.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.config.model.SaveManToManReqVO;
import kr.ko.nexmain.server.MissingU.config.service.ConfigService;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/config")
public class ConfigController {
	
	protected static Logger log = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	private ConfigService configService;
	@Autowired
	private MsgUtil msgUtil;
	
	@Value("#{config['APPLICATION_ROOT_PATH']}")
	private String APPLICATION_ROOT_PATH;
	
	/**********************************************
	(환경설정) 공지사항 리스트 조회
	***********************************************/
	@RequestMapping(value="/getNoticeList.html")
	public ModelAndView getNoticeList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getNoticeList()");
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
			
			//공지사항 리스트 조회 호출
			Map<String,Object> returnMap = this.configService.getNoticeList(inputVO);
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
		log.info("[{} END]", "getNoticeList()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(환경설정) 공지사항 조회
	***********************************************/
	@RequestMapping(value="/getNotice.html")
	public ModelAndView getNotice(@Valid GetNoticeReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getNotice()");
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
			
			//공지사항 조회 호출
			Map<String,Object> returnMap = this.configService.getNotice(inputVO);
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
		log.info("[{} END]", "getNotice()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(환경설정) 사용자가이드 리스트 조회
	***********************************************/
	@RequestMapping(value="/getUserGuideList.html")
	public ModelAndView getUserGuideList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getUserGuideList()");
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
			
			//공지사항 조회 호출
			Map<String,Object> returnMap = this.configService.getUserGuideList(inputVO);
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
		log.info("[{} END]", "getUserGuideList()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(환경설정) 사용자가이드 조회
	***********************************************/
	@RequestMapping(value="/getUserGuide.html")
	public ModelAndView getUserGuide(@Valid GetUserGuideReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getUserGuide()");
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
			
			//공지사항 조회 호출
			Map<String,Object> returnMap = this.configService.getUserGuide(inputVO);
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
		log.info("[{} END]", "getUserGuide()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
	/**********************************************
	(마이페이지) 회원사진 신규 업로드/업데이트
	***********************************************/
	@RequestMapping(value="/manToManQuestion.html", method = RequestMethod.POST)
	public ModelAndView saveTalk(@Valid SaveManToManReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "manToManQuestion()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		ModelAndView modelAndView;
		
		try {
			
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				log.info(" errors "  + bindingResult.getAllErrors().toString());
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
	
				return modelAndView;
			}
			
			MultipartFile uploadFile = inputVO.getUploadFile();
			//파라미터에 톡사진이 있는 경우 사진 업로드
			if(uploadFile != null) {
				
				String baseFileContextPath	= Constants.MANTOMAN_IMG_SAVE_PATH +"/"+ UTL.getCurrentDate();
				String baseFileName			= inputVO.getgMemberId() +"_"+ UTL.getCurrentDatetime();
				String orgFileName = baseFileName + "_"+ Constants.ORIGINAL_IMAGE_SUFFIX +"."+ Constants.FileExt.JPEG;
				
				//대상폴더 미존재시 폴더 생성
				File f = new File(APPLICATION_ROOT_PATH + baseFileContextPath);
				if(!f.exists()) {
					f.mkdirs();
				}
				
				//원본파일 저장
				File orginal = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName);
				uploadFile.transferTo(orginal);
				inputVO.setFile(baseFileContextPath +"/"+ orgFileName);			//mu_talketome 업데이트를 위한 정보
			}
				
			Map<String,Object> returnMap = this.configService.doSaveManToManQuestion(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
		}catch(IOException e) {
			log.info("io error : " + e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		} 
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "manToManQuestion()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
}