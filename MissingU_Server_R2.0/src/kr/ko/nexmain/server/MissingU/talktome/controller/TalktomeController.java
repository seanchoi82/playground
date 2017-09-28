package kr.ko.nexmain.server.MissingU.talktome.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.talktome.model.GetTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.service.TalktomeService;
import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/talktome")
public class TalktomeController {
	
	protected static Logger log = LoggerFactory.getLogger(TalktomeController.class);

	@Autowired
	private TalktomeService talktomeService;
	@Autowired
	private MsgUtil msgUtil;
	
	@Value("#{config['APPLICATION_ROOT_PATH']}")
	private String APPLICATION_ROOT_PATH;
	
	/**********************************************
	(톡투미) 톡투미 글 리스트 조회
	***********************************************/
	@RequestMapping(value="/getTalkList.html")
	public ModelAndView getTalktomeList(@Valid CommReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getTalktomeList()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = this.talktomeService.getTalktomeList(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getTalktomeList()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	/**********************************************
	(마이페이지) 회원사진 신규 업로드/업데이트
	***********************************************/
	@RequestMapping(value="/saveTalk.html", method = RequestMethod.POST)
	public ModelAndView saveTalk(@Valid SaveTalkReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "saveTalk()");
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
				
				String baseFileContextPath	= Constants.TALK_IMG_SAVE_PATH +"/"+ UTL.getCurrentDate();
				String baseFileName			= inputVO.getgMemberId() +"_"+ UTL.getCurrentDatetime();
				
				//이미지 크기 설정
				int bigImgWidth		= 500;
				int bigImgHeight	= 500;
				int thumbImgWidth	= 100;
				int thumbImgHeight	= 100;
				
				String orgFileName = baseFileName + "_"+ Constants.ORIGINAL_IMAGE_SUFFIX +"."+ Constants.FileExt.JPEG;
				String bigFileName = baseFileName + "_"+ Constants.BIG_IMAGE_SUFFIX +"."+ Constants.FileExt.JPEG;
				String thumbFileName = baseFileName +"."+ Constants.FileExt.JPEG;
				
				//대상폴더 미존재시 폴더 생성
				File f = new File(APPLICATION_ROOT_PATH + baseFileContextPath);
				if(!f.exists()) {
					f.mkdirs();
				}
				
				//원본파일 저장
				File orginal = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + orgFileName);
				File big = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName);
				File thumnail = new File(APPLICATION_ROOT_PATH + baseFileContextPath + "/" + thumbFileName);
				
				uploadFile.transferTo(orginal);
				inputVO.setTalkPhotoOrgUrl(baseFileContextPath +"/"+ orgFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("bigImgWidth " + bigImgWidth + " bigImgHeight" + bigImgHeight); 
				log.info("save big filename : " + APPLICATION_ROOT_PATH + baseFileContextPath + "/" + bigFileName);
				
				//큰사이즈 이미지 생성 및 저장
				Thumbnails.of(orginal)
						.size(bigImgWidth, bigImgHeight)
						.toFile(big);
				inputVO.setTalkPhotoBigUrl(baseFileContextPath +"/"+ bigFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("save big file");
				
				//썸네일 이미지 생성 및 저장
				Thumbnails.of(orginal)
						.size(thumbImgWidth, thumbImgHeight)
						.toFile(thumnail);
				inputVO.setTalkPhotoUrl(baseFileContextPath +"/"+ thumbFileName);			//mu_talketome 업데이트를 위한 정보
				
				log.info("save thumnail file");
			}
				
			Map<String,Object> returnMap = this.talktomeService.doSaveTalkInfo(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
			
			log.info("save talk completed");
			
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
		log.info("[{} END]", "saveTalk()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	/**********************************************
	(공통) SaveTalk 테스트
	***********************************************/
	@RequestMapping(value="/saveTalkTest.html", method = RequestMethod.GET)
	public ModelAndView saveTalkTest() {
		ModelAndView modelAndView = new ModelAndView("common/SaveTalkTest");
		modelAndView.addObject(new SaveTalkReqVO());

		return modelAndView;
	}
	
	/**********************************************
	(톡투미) 톡투미 글 내용 조회
	***********************************************/
	@RequestMapping(value="/getTalk.html")
	public ModelAndView getTalktome(@Valid GetTalkReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getTalktome()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = this.talktomeService.getTalktome(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "getTalktome()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(톡투미) 톡투미 댓글 저장
	***********************************************/
	@RequestMapping(value="/saveTalkReply.html")
	public ModelAndView saveTalkReply(@Valid SaveTalkReplyReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "saveTalkReply()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			//로그인 서비스 호출
			Map<String,Object> returnMap = this.talktomeService.doSaveTalkReply(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "saveTalkReply()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	/**********************************************
	(톡투미) 톡투미 삭제
	***********************************************/
	@RequestMapping(value="/delTalk.html")
	public ModelAndView delTalk(@Valid GetTalkReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "delTalk()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		log.info("gMemberId : {} | gLang : {}",inputVO.getgMemberId(), inputVO.getgLang());
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			//바인딩에러 체크
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			// 톡투미 삭제
			Map<String,Object> returnMap = this.talktomeService.doDeleteTalk(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "delTalk()");
		log.info("{}","==============================================\n");
		return modelAndView;
	}
	
	
	
}