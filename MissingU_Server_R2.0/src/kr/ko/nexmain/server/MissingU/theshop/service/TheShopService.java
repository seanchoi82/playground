package kr.ko.nexmain.server.MissingU.theshop.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.theshop.model.BeforeGooglePayReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.BuyItemReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.GooglePayReqVO;
import kr.ko.nexmain.server.MissingU.theshop.model.TStoreProduct;

public interface TheShopService {
	/** 아이템 구매 처리 */
	Map<String,Object> buyItem(BuyItemReqVO inputVO);
	
	/** 결제내역 조회 */
	Map<String,Object> payHist(Map<String,Object> inputVO);
	
	/** 다우결제 후처리  */
	Map<String,Object> afterDaoupay(Map<String,Object> inputMap);
	
	/** 구글결제 전처리  */
	Map<String,Object> beforeGooglePay(BeforeGooglePayReqVO inputVO) throws Exception;
	
	/** 구글결제 후처리  */
	Map<String,Object> afterGooglePay(GooglePayReqVO inputVO);

//	/** 티스토어 결제전처리 - 주문번호 생성 및 결제 히스토리 생성 // 로컬에서 주문하고 성공하면 서버에서 확정만 하는 걸로~  
//	 * @throws Exception */
//	Map<String, Object> beforeTStore(String productId, String gMemberId) throws Exception;
	
	/** 티스토어 결제후처리 - 결제승인, 포인트 생성 및 이력생성*/
	Map<String, Object> afterTStore(TStoreProduct product, String txid, String signdata, String gMemberId);
}
