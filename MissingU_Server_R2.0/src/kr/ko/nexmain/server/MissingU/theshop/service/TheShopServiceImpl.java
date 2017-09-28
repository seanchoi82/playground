package kr.ko.nexmain.server.MissingU.theshop.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.security.AES256;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.theshop.dao.TheshopDao;
import kr.ko.nexmain.server.MissingU.theshop.model.BeforeGooglePayReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.BuyItemReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.GooglePayReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.TStoreProduct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TheShopServiceImpl implements TheShopService {
	protected static Logger log = LoggerFactory.getLogger(TheShopServiceImpl.class);
	private Locale gLocale;
	
	@Autowired
	private TheshopDao theshopDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private MsgUtil msgUtil;
	
	/** 아이템 구매 처리 */
	public Map<String,Object> buyItem(BuyItemReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Integer	memberId = inputVO.getgMemberId();
		
		//정액권 만료일자 체크
		boolean hasFreePass = commonService.hasFreePass(memberId);
		
		if(hasFreePass) {
			//정액권 사용기간이 남은 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("theshop.buyItem.le.passNotExpired", gLocale),
					msgUtil.getMsgText("theshop.buyItem.le.passNotExpired", gLocale));
			returnMap.put("result", result);
		} else {
			String itemCode = inputVO.getItemCode();
			String	usageCd = "";
			Integer	usePoint = 0;
			int		dateAddType = Calendar.DATE;
			int		dayToAdd = 0;
			
			if(Constants.ItemCode.PASS_1W.equals(itemCode)) {
				usageCd = "O101";
				usePoint = -7000;
				//dateAddType = Calendar.WEEK_OF_YEAR;
				dayToAdd = 7;
			} else if (Constants.ItemCode.PASS_1M.equals(itemCode)) {
				usageCd = "O102";
				usePoint = -21000;
				//dateAddType = Calendar.MONTH;
				dayToAdd = 30;
			}
			
			//가용포인트 체크
			Map<String,Object> member = membershipDao.selectMemberAndPointInfoByMemberId(memberId);
			if(member == null) {
				log.info("member is null");
			} else {
				Long	memberPoint = (Long)member.get("point");
				if(memberPoint < Math.abs(usePoint)){
					//포인트가 부족한 경우
					Result result = new Result(
							Constants.ReturnCode.AUTH_ERROR,
							msgUtil.getMsgCd("comm.authError.lackOfPoint", gLocale),
							msgUtil.getMsgText("comm.authError.lackOfPoint", gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
			}
			
			//만료일자 설정
			String currentDay = UTL.getCurrentDate();
			String	newPassExpireDay = UTL.dateToGap(currentDay, dateAddType, dayToAdd);
			MemberAttr attr = new MemberAttr();
			attr.setMemberId(memberId);
			attr.setAttrName(Constants.MemberAttrName.PASS_EXPIRE_DAY);
			attr.setAttrValue(newPassExpireDay);
			membershipDao.updateMemberAttr(attr);
			log.info("passStartDay : {}", currentDay);
			log.info("newPassExpireDay : {}", newPassExpireDay);
			
			//포인트 정보 업데이트
			commonService.updatePointInfo(memberId, Constants.EventTypeCd.OUTCOME, usageCd, usePoint, inputVO.getgLang());
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("theshop.buyItem.ss.success", gLocale),
					msgUtil.getMsgText("theshop.buyItem.ss.success", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	
	/** 결제내역 리스트 조회 */
	public Map<String,Object> payHist(Map<String,Object> inputVO) {
		gLocale = new Locale(inputVO.get("gLang") + "");
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//포인트 조회
		Integer	memberId = UTL.convertToInt(inputVO.get("gMemberId"), 0);
		Map<String,Object> member = membershipDao.selectMemberAndPointInfoByMemberId(memberId);
		Long	memberPoint = (Long)member.get("point");
		responseMap.put("memberPoint", memberPoint);
		
		// 결제내역 전체
		Integer totalCnt = theshopDao.selectPayHistCntByMemberId(inputVO);
		responseMap.put("totalCnt", totalCnt);
		//결제내역 조회
		List<Map<String,Object>> payHistList = theshopDao.selectPayHistByMemberId(inputVO);
		responseMap.put("payHistList", payHistList);
		
		if(member != null) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 다우결제 후처리  */
	public Map<String,Object> afterDaoupay(Map<String,Object> inputMap) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String	prodCode = (String)inputMap.get("PRODUCTCODE");
		
		Integer	memberId = Integer.parseInt((String)inputMap.get("USERID"));
		String	usageCd = "";
		Integer	chargePoint = 0;
		
		if("P01001".equals(prodCode)) {
			usageCd = "I101";
			chargePoint = 10000;
		} else if ("P01002".equals(prodCode)) {
			usageCd = "I102";
			chargePoint = 24000;
		} else if ("P01003".equals(prodCode)) {
			usageCd = "I103";
			chargePoint = 36000;
		}
		
		//포인트 정보 업데이트
		commonService.updatePointInfo(memberId, Constants.EventTypeCd.INCOME, usageCd, chargePoint, "ko");
		
		//결제이력 생성
		Map<String,Object> inMap = new HashMap<String,Object>();
		inMap.put("gMemberId", memberId);
		inMap.put("orderNum", inputMap.get("ORDERNO"));
		inMap.put("status", "S");
		inMap.put("payMethod", inputMap.get("PAYMETHOD"));
		inMap.put("prodCd", prodCode);
		inMap.put("prodName", inputMap.get("PRODUCTNAME"));
		inMap.put("amount", inputMap.get("AMOUNT"));
		inMap.put("payDate", inputMap.get("SETTDATE"));
		inMap.put("cpid", inputMap.get("CPID"));
		inMap.put("trxId", inputMap.get("DAOUTRX"));
		inMap.put("mobileCom", inputMap.get("MOBILECOMPANY"));
		inMap.put("mobileNo", inputMap.get("MOBILENO"));
		inMap.put("email", inputMap.get("EMAIL"));
		inMap.put("keyword1", inputMap.get("RESERVEDSTRING"));
		inMap.put("keyword2", inputMap.get("RESERVEDINDEX1"));
		inMap.put("keyword3", inputMap.get("RESERVEDINDEX2"));
		theshopDao.insertPayHist(inMap);
		
		return returnMap;
	}
	
	/** 구글결제 전처리 
	 * @throws Exception */
	public Map<String,Object> beforeGooglePay(BeforeGooglePayReqVO inputVO) throws Exception {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//토큰 발급
		StringBuilder sb = new StringBuilder();
		sb.append(inputVO.getgMemberId());
		sb.append("_");
		sb.append(UTL.getCurrentDatetime());
		
		AES256 aes = new AES256(Constants.AES_KEY, Constants.AES_KEY);
		String token = aes.encrypt(sb.toString());
		
		//주문번호
		String orderNum = null;
		if(StringUtils.isNotEmpty(inputVO.getOrderNum())) {
			orderNum = inputVO.getOrderNum();
		} else {
			orderNum = sb.toString();
		}
		
		responseMap.put("token", token);
		responseMap.put("orderNum", orderNum);
		
		//결제이력 생성
		Map<String,Object> inMap = new HashMap<String,Object>();
		inMap.put("gMemberId", inputVO.getgMemberId());
		inMap.put("orderNum", orderNum);
		inMap.put("token", token);
		inMap.put("status", "P");
		inMap.put("payMethod", "GooglePlay");
		
		int cnt = theshopDao.insertOrUpdatePayHist(inMap);
		
		if(cnt > 0 && StringUtils.isNotEmpty(token)) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 구글결제 후처리 */
	public Map<String,Object> afterGooglePay(GooglePayReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String	prodCd = inputVO.getProdCd();
		Integer	memberId = inputVO.getgMemberId();
		
		String	usageCd = "";
		Integer	chargePoint = 0;
		String amount = inputVO.getAmount();
		
		if("P01001".equals(prodCd.toUpperCase())) {
			usageCd = "I101";
			chargePoint = 10000;
			//amount = 10000;
		} else if ("P01002".equals(prodCd.toUpperCase())) {
			usageCd = "I102";
			chargePoint = 24000;
			//amount = 20000;
		} else if ("P01003".equals(prodCd.toUpperCase())) {
			usageCd = "I103";
			chargePoint = 36000;
			//amount = 30000;
		}
		
		//포인트 정보 업데이트
		commonService.updatePointInfo(memberId, Constants.EventTypeCd.INCOME, usageCd, chargePoint, inputVO.getgLang());
		
		//결제이력 생성
		Map<String,Object> inMap = new HashMap<String,Object>();
		inMap.put("gMemberId", memberId);
		inMap.put("orderNum", inputVO.getOrderNum());
		inMap.put("token", inputVO.getToken());
		inMap.put("status", "S");
		inMap.put("payMethod", "Google");
		inMap.put("prodCd", prodCd);
		inMap.put("prodName", inputVO.getProdName());
		inMap.put("amount", amount);
		inMap.put("payDate", UTL.getCurrentDatetime());
//		inMap.put("cpid", "");
//		inMap.put("trxId", "");
//		inMap.put("mobileCom", "");
//		inMap.put("mobileNo", "");
//		inMap.put("email", "");
//		inMap.put("keyword1", "");
//		inMap.put("keyword2", "");
//		inMap.put("keyword3", "");
		int cnt = theshopDao.updatePayHist(inMap);
		
		if(cnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.pay", gLocale),
					msgUtil.getMsgText("comm.success.pay", gLocale));
			returnMap.put("result", result);
		} else {
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.pay", gLocale),
					msgUtil.getMsgText("comm.fail.pay", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

	
//	/** 로컬에서 주문하고 확정만 서버에서 한다. 쿨하게~
//	 * 티스토어 결제전처리 - 주문번호 생성 및 결제 히스토리 생성 
//	 * */
//	public Map<String, Object> beforeTStore(String productId, String gMemberId) throws Exception {
//		
//		gLocale = new Locale("ko"); // tstore는 한국만 있어요~
//		
//		Map<String,Object> returnMap = new HashMap<String,Object>();
//		Map<String,Object> responseMap = new HashMap<String,Object>();
//		
//		//토큰 발급
//		StringBuilder sb = new StringBuilder();
//		sb.append(gMemberId);
//		sb.append("_");
//		sb.append(UTL.getCurrentDatetime());
//		
//		String oderNum = sb.toString();
//		
//		AES256 aes = new AES256(Constants.AES_KEY, Constants.AES_KEY);
//		String token = aes.encrypt(oderNum);
//		String	prodCd = productId;
//		
//		//주문번호
//		responseMap.put("token", token);
//		responseMap.put("orderNum", oderNum);
//		
//		//결제이력 생성
//		Map<String,Object> inMap = new HashMap<String,Object>();
//		inMap.put("gMemberId", gMemberId);
//		inMap.put("orderNum", oderNum);
//		inMap.put("token", token);
//		inMap.put("status", "P");
//		inMap.put("payMethod", "TStore");
//		inMap.put("prodCd", prodCd);
//				
//		int cnt = theshopDao.insertOrUpdatePayHist(inMap);
//		if(cnt > 0 && StringUtils.isNotEmpty(token)) {
//			Result result = new Result(
//					Constants.ReturnCode.SUCCESS,
//					msgUtil.getMsgCd("comm.success.search", gLocale),
//					msgUtil.getMsgText("comm.success.search", gLocale));
//			returnMap.put("result", result);
//			returnMap.put("response", responseMap);
//		} else {
//			Result result = new Result(
//					Constants.ReturnCode.LOGIC_ERROR,
//					msgUtil.getMsgCd("comm.fail.search", gLocale),
//					msgUtil.getMsgText("comm.fail.search", gLocale));
//			returnMap.put("result", result);
//		}
//		
//		return returnMap;		
//	}
	
	/** 구글결제 후처리 */
	public Map<String,Object> afterTStore(TStoreProduct product, String txid, String signdata, String gMemberId) {
		
		gLocale = new Locale("ko"); // tstore는 한국만 있어요~
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String	usageCd = "";
		String	prodCd = product.getProduct_id();
		Integer chargePoint = 0;
		int amount = product.getCharge_amount();
		int memberId = UTL.convertToInt(gMemberId, 0);
		
		if(Constants.TSTORE.PRODUCT_ID_10000_POINT.equals(prodCd.toUpperCase())) {
			usageCd = "I101";
			chargePoint = 10000;
		} else if (Constants.TSTORE.PRODUCT_ID_24000_POINT.equals(prodCd.toUpperCase())) {
			usageCd = "I102";
			chargePoint = 24000;
		} else if (Constants.TSTORE.PRODUCT_ID_36000_POINT.equals(prodCd.toUpperCase())) {
			usageCd = "I103";
			chargePoint = 36000;
		}
		
		//포인트 정보 업데이트
		commonService.updatePointInfo(memberId, Constants.EventTypeCd.INCOME, usageCd, chargePoint, gLocale.toString());
		
		//결제이력 생성
		Map<String,Object> inMap = new HashMap<String,Object>();
		inMap.put("gMemberId", memberId);
		inMap.put("orderNum", product.getTid());
		inMap.put("token", product.getBp_info());
		inMap.put("status", "S");
		inMap.put("payMethod", "TStore");
		inMap.put("prodCd", prodCd);
		inMap.put("prodName", product.getDetail_pname());
		inMap.put("amount", amount);
		inMap.put("payDate", UTL.getCurrentDatetime());
		
		// tstore 구매주문번호
		inMap.put("txid", txid);
		inMap.put("signdata", signdata);

		int cnt = theshopDao.insertOrUpdatePayHistTStore(inMap);
		
		if(cnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.pay", gLocale),
					msgUtil.getMsgText("comm.success.pay", gLocale));
			returnMap.put("result", result);
		} else {
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.pay", gLocale),
					msgUtil.getMsgText("comm.fail.pay", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
}
