package kr.ko.nexmain.server.MissingU.missionmatch.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchRankReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReportReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.service.MissionMatchService;
import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missingu/missionmatch")
public class MissionMatchController {
	
	
	// missingu/missionmatch/getMissionMatchDatas.html?type=0&gLang=ko&gCountry=kr&gMemberId=0&sex=G01002&needRound=32
	
	protected static Logger log = LoggerFactory.getLogger(MissionMatchController.class);
	
	@Autowired
	private MissionMatchService missionMatchService;
	@Autowired
	private MsgUtil msgUtil;
	
	@Value("#{config['APPLICATION_ROOT_PATH']}")
	private String APPLICATION_ROOT_PATH;

	/** 사용가능한 미션 1개를 가져온다. */
	@RequestMapping(value="/getLastedMissionMatch.html")
	public ModelAndView getLastedMissionMatch(@Valid MissionMatchReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "getLastedMissionMatch()");
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
			
			// 최신 미션매치 조회
			Map<String,Object> returnMap = this.missionMatchService.getLastedMissionMatch(inputVO);
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
		log.info("[{} END]", "getLastedMissionMatch()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}
	
	/** 사용가능한 미션 1개를 가져온다. */
	@RequestMapping(value="/getMissionMatchInfo.html")
	public ModelAndView getMissionMatchInfo(@Valid MissionMatchReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "getMissionMatchInfo()");
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
			
			// 최신 미션매치 조회
			Map<String,Object> returnMap = this.missionMatchService.getMissionMatchInfo(inputVO);
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
		log.info("[{} END]", "getMissionMatchInfo()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}

	/** 미션 매치 참여할 목록을 가져온다. */
	@RequestMapping(value="/getMissionMatchDatas.html")
	public ModelAndView getMissionMatchDatas(@Valid MissionMatchReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "getMissionMatchDatas()");
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
			
			// 최신 미션매치 조회
			Map<String,Object> returnMap = this.missionMatchService.getMissionMatchDatas(inputVO);
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
		log.info("[{} END]", "getMissionMatchDatas()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}
	
	/** 미션 매치 결과를 보고 한다. (승수 업데이트) */
	@RequestMapping(value="/reportMissionMatch.html")
	public ModelAndView reportMissionMatch(@Valid MissionMatchReportReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		
		
		log.info("{}","==============================================");
		log.info("[{} START]", "reportMissionMatch()");
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
			
			// 최신 미션매치 조회
			Map<String,Object> returnMap = this.missionMatchService.reportMissionMatch(inputVO);
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
		log.info("[{} END]", "reportMissionMatch()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}
	
	/** 미션 매치 참여  */
	@RequestMapping(value="/joinMissionMatch.html")
	public ModelAndView joinMissionMatch(@Valid MissionMatchJoinReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "joinMissionMatch()");
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
			
			// 미션매치 등록
			MultipartFile uploadFile = inputVO.getSaveFile();
			//파라미터에 톡사진이 있는 경우 사진 업로드
			if(uploadFile != null) {
				
				String baseFileContextPath	= Constants.MISSIONMATCH_IMG_SAVE_PATH +"/"+ UTL.getCurrentDate();
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
				inputVO.setUploadfile(baseFileContextPath +"/"+ orgFileName);			//mu_talketome 업데이트를 위한 정보
				
				//큰사이즈 이미지 생성 및 저장
				Thumbnails.of(orginal)
						.size(bigImgWidth, bigImgHeight)
						.toFile(big);
				inputVO.setUploadfileBig(baseFileContextPath +"/"+ bigFileName);			//mu_talketome 업데이트를 위한 정보
				
				//썸네일 이미지 생성 및 저장
				Thumbnails.of(orginal)
						.size(thumbImgWidth, thumbImgHeight)
						.toFile(thumnail);
				inputVO.setUploadfileOrg(baseFileContextPath +"/"+ thumbFileName);			//mu_talketome 업데이트를 위한 정보
			}
			
			
			Map<String,Object> returnMap = this.missionMatchService.joinMissionMatch(inputVO);
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
		log.info("[{} END]", "joinMissionMatch()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}
	
	
	/** 미션 매치 랭크를 확인한다. */
	@RequestMapping(value="/rankMissionMatch.html")
	public ModelAndView rankMissionMatch(@Valid MissionMatchRankReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "rankMissionMatch()");
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
		
			Map<String,Object> returnMap = this.missionMatchService.joinRankMissionMatch(inputVO);
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
		log.info("[{} END]", "rankMissionMatch()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}
	
	/** 미션 매치 랭크를 확인한다. */
	@RequestMapping(value="/totalRankMissionMatch.html")
	public ModelAndView totalRankMissionMatch(@Valid MissionMatchRankReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		log.info("[{} START]", "totalRankMissionMatch()");
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
		
			Map<String,Object> returnMap = this.missionMatchService.totalRankMissionMatch(inputVO);
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
		log.info("[{} END]", "totalRankMissionMatch()");
		log.info("{}","==============================================");
		
		return modelAndView;
	}
}
