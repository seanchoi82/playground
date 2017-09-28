package kr.ko.nexmain.server.MissingU.theshop.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.controller.BaseController;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.NetworkUtil;
import kr.ko.nexmain.server.MissingU.common.utils.NetworkUtil.HttpResult;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.theshop.dao.TheshopDao;
import kr.ko.nexmain.server.MissingU.theshop.model.BuyItemReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.OneselfCertReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.OrderReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.TStorePurchaseConfirmation;
import kr.ko.nexmain.server.MissingU.theshop.model.TStorePurchaseConfirmationResult;
import kr.ko.nexmain.server.MissingU.theshop.service.TheShopService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/missingu/theshop")
public class TheShopController extends BaseController{
	
	protected static Logger log = LoggerFactory.getLogger(TheShopController.class);

	@Autowired
	private TheShopService theShopService;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private TheshopDao theshopDao;
	@Autowired
	private MsgUtil msgUtil;
	
	@Value("#{config['missingu.server.baseUrl']}")
	private String MISSINGU_SERVER_BASEURL;
	
	
//	/**
//	 * TStore 구매전 주문요청 -===> 단말에서 주문하고 서버는 확정만 하는 기능으로 개선 (쿨하게)
//	 * @param params 요청파라미터
//	 * @return
//	 */
//	@RequestMapping(value="/tstorePurchase.html")
//	public ModelAndView beforeGooglePay(@RequestParam Map<String,Object> params, HttpServletRequest request) {
//		
//		Locale gLocale = new Locale("ko"); // tstore는 한국만 있어요~
//		
//		startLog(log, params, request);
//		
//		String gMemberId = params.get("gMemberId") + "";
//		String productId = params.get("productId") + "";
//				
//		Map<String,Object> responseMap = new HashMap<String, Object>();
//		
//		ModelAndView modelAndView;
//		try {
//			
//			Map<String,Object> returnMap = theShopService.beforeTStore(productId, gMemberId);
//			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
//			
//		} catch (Exception e) {
//			// 시스템 오류
//			e.printStackTrace();
//			log.info("[ERROR : {}]", e.getMessage());
//			responseMap = msgUtil.getSystemErrorMap(responseMap, gLocale.getLanguage());
//			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
//			return modelAndView;
//		}
//		
//		endLog(log);
//		
//		return modelAndView;
//	}
	
	/**
	 * TStore 구매확정처리
	 * @param params 요청파라미터
	 * @return
	 */
	@RequestMapping(value="/tstorePurchaseConfirmation.html")
	public ModelAndView tstorePurchaseConfirmation(@RequestParam Map<String,Object> params) {
		
		Locale gLocale = new Locale("ko"); // tstore는 한국만 있어요~
		
		String url = Constants.TSTORE.DEBUG_MODE ? Constants.TSTORE.RECEIPTS_VARIFICATION_URL_DEBUG : Constants.TSTORE.RECEIPTS_VARIFICATION_URL;
		
		String gMemberId = params.get("gMemberId") + "";
		String txid = params.get("txid") + "";
		String appid = Constants.TSTORE.APP_ID_MISSINGU;
		String signdata = params.get("signdata") + "";
//		String token = params.get("token") + ""; 토큰 검증으로 실패 때리지 않도록 주의 한다.
		
		TStorePurchaseConfirmation tstore = new TStorePurchaseConfirmation();
		tstore.setTxid(txid);
		tstore.setAppid(appid);
		tstore.setSigndata(signdata);
		
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		String json = gson.toJson(tstore);
		
		// TStore 서버에 전자영수증 검증
		log.info("------- tsotre 검증 --------");
		log.info(json);
		HttpResult result = NetworkUtil.requestHttp(HttpMethod.POST, url, json);
		
		// 결제 검증 성공 (주문 확정 처리)
		Map<String,Object> responseMap = new HashMap<String, Object>();
		if(result != null && result.isResult()) {
			
			TStorePurchaseConfirmationResult resultVo = gson.fromJson(result.getContent(), TStorePurchaseConfirmationResult.class);
			if(resultVo != null && resultVo.getStatus() == 0 && resultVo.getProduct().size() > 0) {
				log.info("------- tsotre 결제 검증 성공 --------");
				
				// 정상일 경우 포인트 발급처리
				responseMap = theShopService.afterTStore(resultVo.getProduct().get(0), txid, signdata, gMemberId);
				
			}else if(resultVo != null && resultVo.getStatus() == 9) {
				
				log.info("------- tsotre 결제 검증 실패 --------");
				
				// 구매 확증 오류
				Result resultErr = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						resultVo.getDetail(),
						resultVo.getMessage());
				responseMap.put("result", resultErr);
			}else{
				
				log.info("------- tsotre 결제시 예외 오류 --------");
				
				Result resultErr = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("comm.fail.pay", gLocale),
						msgUtil.getMsgText("comm.fail.pay", gLocale));
				responseMap.put("result", resultErr);
			}
			
		}else if(result != null && !result.isResult()) {
			
			log.info("------- tsotre 결제 요청 오류 --------");
			log.info(result.getResultMsg());
			log.info(result.getContent());
			log.info(result.getHttpStatusCode() + "");
			
			// 오류일 경우 전달 처리
			Result resultErr = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.pay", gLocale),
					msgUtil.getMsgText("comm.fail.pay", gLocale) + ", " + result.getResultMsg());
			responseMap.put("result", resultErr);
		}else{
			
			log.info("------- tsotre 결제 요청 예외 오류 --------");
			
			// 오류일 경우 전달 처리
			Result resultErr = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.pay", gLocale),
					msgUtil.getMsgText("comm.fail.pay", gLocale));
			responseMap.put("result", resultErr);
		}

		ModelAndView modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
		return modelAndView;
	}

	@RequestMapping(value="/onselfCert.html", method = RequestMethod.GET)
	public ModelAndView onselfCert(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "onselfCert()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
		
		ModelAndView modelAndView = new ModelAndView("theshop/oneselfCertification");
		modelAndView.addObject(new OneselfCertReqVO());
		modelAndView.addObject("paramMap", params);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "onselfCert()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/oneselfCertSubmit.html", method = RequestMethod.POST)
	public ModelAndView oneselfCertSubmit(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "oneselfCertSubmit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		
//		if (bindingResult.hasErrors()) {
//			ModelAndView modelAndView = new ModelAndView("theshop/oneselfCertification");
//			modelAndView.getModel().putAll(bindingResult.getModel());
//			return modelAndView;
//		}
		
		Integer	memberId = 0;
		try {
			memberId = Integer.parseInt(params.get("gMemberId") + "");
		}catch(Exception e) {}
		
		//나신평 CheckPlus 호출 준비
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
	    
	    String sSiteCode = Constants.CheckPlus.SITE_CODE;				// 나신평정보로부터 부여받은 사이트 코드
	    String sSitePassword = Constants.CheckPlus.SITE_PASSWD;				// 나신평정보로부터 부여받은 사이트 패스워드
	    
	   	String popgubun 	= "N";		//Y : 취소버튼 있음 / N : 취소버튼 없음
		String customize 	= "Mobile";			//없으면 기본 웹페이지 / Mobile : 모바일페이지
		
	    String sRequestNumber = String.valueOf(memberId) + "_" + sSiteCode + "_" + UTL.getCurrentDatetime();        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
	    																																						// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
	    //sRequestNumber = kisCrypt.getRequestNO(sSiteCode);
	  	//session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
	  	
	  	String sAuthType = params.get("authType") + "";
	  	if(sAuthType == null || sAuthType.length() == 0) sAuthType = "M";
//	  			inputVO.getAuthType();      	// 없으면 기본 선택화면, X: 공인인증서, M: 핸드폰, C: 신용카드
//	  	String sJuminid = inputVO.getJuminIdPrefix()+inputVO.getJuminIdSuffix();		// 사용자 주민등록번호
	   
	    // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
	    String sReturnUrl = MISSINGU_SERVER_BASEURL + Constants.CheckPlus.SUCCESS_RTN_URL;      // 성공시 이동될 URL
	    String sErrorUrl = MISSINGU_SERVER_BASEURL + Constants.CheckPlus.ERROR_RTN_URL;          // 실패시 이동될 URL

	    // 입력될 plain 데이타를 만든다.
	    String sPlainData =  "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
			                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
			                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
			                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
			                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
			                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
			                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;
	    
//	    // 입력될 plain 데이타를 만든다.
//	    String sPlainData = "7:JUMINID" + sJuminid.getBytes().length + ":" + sJuminid +
//	                        "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
//	                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
//	                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
//	                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
//	                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl;
	    
	    String sMessage = "";
	    String sEncData = "";
	    
	    
	    
	    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
	    System.out.println("sPlainData : " + sPlainData);
	    System.out.println("iReturn : " + iReturn);
	    if( iReturn == 0 )
	    {
	        sEncData = niceCheck.getCipherData();
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "암호화 시스템 에러입니다.";
	    }    
	    else if( iReturn == -2)
	    {
	        sMessage = "암호화 처리오류입니다.";
	    }    
	    else if( iReturn == -3)
	    {
	        sMessage = "암호화 데이터 오류입니다.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "입력 데이터 오류입니다.";
	    }    
	    else
	    {
	        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    }
		
	    ModelAndView modelAndView = new ModelAndView(
	    		iReturn == 0 ? 
	    		"theshop/oneselfCertificationSubmit"
	    				: "theshop/oneselfCertification"
	    				);
	    
	    modelAndView.addObject("sEncData", sEncData);
	    modelAndView.addObject("sMessage", sMessage);
	    modelAndView.addObject("paramMap", params);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "oneselfCertSubmit()");
		log.info("{}","==============================================\n");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/checkplus_success.html")
	public ModelAndView checkplusSuccess(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "checkplusSuccess()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		ModelAndView modelAndView = new ModelAndView("theshop/checkplus_success");
		
		//나신평으로부터 암호화 된 결과값 복호화
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

	    String sEncodeData = request.getParameter("EncodeData");
	    String sReserved1  = request.getParameter("param_r1");
	    String sReserved2  = request.getParameter("param_r2");
	    String sReserved3  = request.getParameter("param_r3");
	    
	    String sPlainData = "";
	    String sSiteCode = Constants.CheckPlus.SITE_CODE;						// 나신평정보로부터 부여받은 사이트 코드
	    String sSitePassword = Constants.CheckPlus.SITE_PASSWD;			// 나신평정보로부터 부여받은 사이트 패스워드
	    String sCipherTime = "";						// 복호화한 시간
	    String sRequestNumber = "";						// 요청 번호
	    String sResponseNumber = "";					// 인증 고유번호
	    String sAuthType = "";						// 인증 수단
	    String sName = "";							 // 성명
	    String sDupInfo = "";						 // 중복가입 확인값 (DI_64 byte)
	    String sConnInfo = "";					 // 연계정보 확인값 (CI_88 byte)
	    String sBirthDate = "";					 // 생일
	    String sGender = "";						 // 성별
	    String sNationalInfo = "";       // 내/외국인정보 (개발가이드 참조)	    
	    String sMessage = "";
	    
	    String[] reqSeqArray		= null;
	    Integer	memberId = null;
	    
	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();
	        
	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	        
	        log.info("{}","----------------------------------------------");
	        log.info("{}", "sPlainData : " + sPlainData);
	        log.info("{}", "mapresult : " + mapresult); 
	        
	        
	        
	        
	        /**
	         *
	         * */
	        for(Object key : mapresult.keySet()) {
	        	log.info("{}", key + " : " + mapresult.get(key));	
	        }
	        log.info("{}","----------------------------------------------");
	        
	        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	        sResponseNumber = (String)mapresult.get("RES_SEQ");
	        sAuthType 			= (String)mapresult.get("AUTH_TYPE");
	        sName 					= (String)mapresult.get("NAME");
	        sBirthDate 			= (String)mapresult.get("BIRTHDATE");
	        sGender 				= (String)mapresult.get("GENDER");
	        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
	        sDupInfo 				= (String)mapresult.get("DI");
	        sConnInfo 			= (String)mapresult.get("CI");
	        
	        //요청번호에서 회원ID 추출
	        /*
	        reqSeqArray = sRequestNumber.split("_");
	        memberId = Integer.parseInt(reqSeqArray[0]);
	        */
	        
	        try {
	        	memberId = Integer.parseInt(sReserved1);
	        }catch(Exception e) {
	        	memberId = 0;
	        }
	        
	        modelAndView.addObject("sName", sName);
	        modelAndView.addObject("sBirthDate", sBirthDate);
	        modelAndView.addObject("sGender", sGender);
	        modelAndView.addObject("sNationalInfo", sNationalInfo);
	        
	        /*
	         * sAuthType 			= (String)mapresult.get("AUTH_TYPE");
		        sName 					= (String)mapresult.get("NAME"); 이름
		        sBirthDate 			= (String)mapresult.get("BIRTHDATE"); 생년월일
		        sGender 				= (String)mapresult.get("GENDER"); 성별
		        sNationalInfo  	= (String)mapresult.get("NATIONALINFO"); 국가정보
		        sDupInfo 				= (String)mapresult.get("DI"); DI
		        sConnInfo 			= (String)mapresult.get("CI"); CI
        
	        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	        if(!sRequestNumber.equals(session_sRequestNumber))
	        {
	            sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
	            sResponseNumber = "";
	            sAuthType = "";
	        }
	        */
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "복호화 시스템 에러입니다.";
	    }    
	    else if( iReturn == -4)
	    {
	        sMessage = "복호화 처리오류입니다.";
	    }    
	    else if( iReturn == -5)
	    {
	        sMessage = "복호화 해쉬 오류입니다.";
	    }    
	    else if( iReturn == -6)
	    {
	        sMessage = "복호화 데이터 오류입니다.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "입력 데이터 오류입니다.";
	    }    
	    else if( iReturn == -12)
	    {
	        sMessage = "사이트 패스워드 오류입니다.";
	    }    
	    else
	    {
	        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    }
	    
	    // 만 나이 추출 sBirthDate
	    int manAge = 0;
	    if(sBirthDate != null && sBirthDate.length() > 7) {
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    	String today = formatter.format(new Date()); //시스템 날짜를 가져와서 yyyyMMdd 형태로 변환

	    	
	    	int todayYear = Integer.parseInt( today.substring(0, 4) );
	    	int todayMonth = Integer.parseInt( today.substring(4, 6) );
	    	int todayDay = Integer.parseInt( today.substring(6, 8) );

	    	int ssnYear = Integer.parseInt( sBirthDate.substring(0, 4) );
	    	int ssnMonth = Integer.parseInt( sBirthDate.substring(4, 6) );
	    	int ssnDay = Integer.parseInt( sBirthDate.substring(6, 8) );

			manAge = todayYear - ssnYear;
			
			if( todayMonth < ssnMonth ){ //생년월일 "월"이 지났는지 체크
				manAge--;
			}else if( todayMonth == ssnMonth ){ //생년월일 "일"이 지났는지 체크
				if( todayDay < ssnDay ){
					manAge--; //생일 안지났으면 (만나이 - 1)
				}
			}
	    }
	    
	    boolean isAdult = manAge > 18;
	    if(!isAdult) {
	    	iReturn = -90;
	    	String resultMsg = msgUtil.getPropMsg("theshop.checkplusFailForUnAdult.msg");
			modelAndView.addObject("resultMsg", resultMsg);
	    }else{
		
	    	if(memberId > 0) {
				//본인인증여부 업데이트
				MemberAttr input = new MemberAttr();
				input.setMemberId(memberId);
				input.setAttrName("oneselfCertification");
				input.setAttrValue("Y");
				membershipDao.updateMemberAttr(input);
	    	}
	    	
			
			String resultMsg = msgUtil.getPropMsg("theshop.checkplusSuccess.msg");
			modelAndView.addObject("resultMsg", resultMsg);
	    }
	    
	    modelAndView.addObject("iReturn", iReturn);
	    
		log.info("sMessage : {}", sMessage);
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "checkplusSuccess()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/checkplus_error.html", method = RequestMethod.POST)
	public ModelAndView checkplusError(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "checkplusError()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		ModelAndView modelAndView = new ModelAndView("theshop/checkplus_fail");
		String resultMsg = msgUtil.getPropMsg("theshop.checkplusFail.msg");
		modelAndView.addObject("resultMsg", resultMsg);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "checkplusError()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/pointCharge.html", method = RequestMethod.GET)
	public ModelAndView pointCharge(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "pointCharge()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		ModelAndView modelAndView = new ModelAndView("theshop/pointSelection");
		modelAndView.addObject(new OrderReqVO());
		modelAndView.addObject("paramMap", params);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "pointCharge()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/orderPhone.html", method = RequestMethod.POST)
	public ModelAndView orderPhone(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "orderPhone()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		ModelAndView modelAndView = new ModelAndView("theshop/orderFormPhone");
		String prodCode		= (String)params.get("prodCode");
		
		if("P01001".equals(prodCode)) {
			params.put("PRODUCTNAME", "10,000포인트 충전");
			params.put("AMOUNT", 10000);
		} else if ("P01002".equals(prodCode)) {
			params.put("PRODUCTNAME", "24,000포인트 충전");
			params.put("AMOUNT", 20000);
		} else if ("P01003".equals(prodCode)) {
			params.put("PRODUCTNAME", "36,000포인트 충전");
			params.put("AMOUNT", 30000);
		}
		
		params.put("CPID", Constants.DaouPay.CPID);
		params.put("PRODUCTCODE", prodCode);
		params.put("HOMEURL", MISSINGU_SERVER_BASEURL + Constants.DaouPay.HOMEURL);
		params.put("CLOSEURL", MISSINGU_SERVER_BASEURL + Constants.DaouPay.CLOSEURL);
		params.put("FAILURL", MISSINGU_SERVER_BASEURL + Constants.DaouPay.FAILURL);
		
		modelAndView.addObject("paramMap", params);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "orderPhone()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	/**
	 * 아이템 구매 페이지
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buyItem.html", method = RequestMethod.GET)
	public ModelAndView buyItem(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "buyItem()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		ModelAndView modelAndView = new ModelAndView("theshop/itemSelection");
		modelAndView.addObject(new BuyItemReqVO());
		modelAndView.addObject("paramMap", params);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "buyItem()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/buyItemSubmit.html", method = RequestMethod.POST)
	public ModelAndView buyItemSubmit(@Valid BuyItemReqVO inputVO, BindingResult bindingResult, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "buyItemSubmit()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", inputVO.toString());
		log.info("{}","----------------------------------------------");
		
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		ModelAndView modelAndView;
		try {
			if (bindingResult.hasErrors()) {
				responseMap = msgUtil.getParamErrorMap(responseMap, bindingResult, inputVO.getgLang());
				modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
				return modelAndView;
			}
			
			Map<String,Object> returnMap = theShopService.buyItem(inputVO);
			modelAndView = UTL.getModelAndViewForJsonResponse(returnMap);
		} catch (Exception e) {
			// 시스템 오류
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
			responseMap = msgUtil.getSystemErrorMap(responseMap, inputVO.getgLang());
			modelAndView = UTL.getModelAndViewForJsonResponse(responseMap);
			return modelAndView;
		}
		
	    //modelAndView.addObject("paramMap", inputVO);
	    //modelAndView.addObject("responseMap", responseMap);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "buyItemSubmit()");
		log.info("{}","==============================================\n");
		
		return modelAndView;
	}
	
	
	/**
	 * 결제내역
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/history.html", method = RequestMethod.GET)
	public ModelAndView history(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "history()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");
		ModelAndView modelAndView = new ModelAndView("theshop/history");
		modelAndView.addObject(new CommReqVO());
		modelAndView.addObject("paramMap", params);
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "history()");
		log.info("{}","==============================================\n");

		return modelAndView;
	}
	
	@RequestMapping(value="/payHist.html")
	public ModelAndView payHist(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		startLog(log, params, request);
		makePaging(params);
				
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("reqParams", params);
		
		ModelAndView modelAndView = new ModelAndView("theshop/history");
		modelAndView.addAllObjects(returnMap);
		
		try {			
			Map<String,Object> responseMap = theShopService.payHist(params);
			modelAndView.addAllObjects(responseMap);
			
		} catch (Exception e) {
			log.error("err", e);
		}
		
		endLog(log);
		
		return modelAndView;
	}
	

}