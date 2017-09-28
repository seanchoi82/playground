package kr.ko.nexmain.server.MissingU.theshop.model;

import java.util.List;

public class TStorePurchaseConfirmationResult {
	/**
	 * 검증결과리턴코드 0 성공, 9실패
	 */
	int status;
	/**
	 * 검증결과상세코드
	 */
	String detail;
	/**
	 * 검증결과메세지
	 */
	String message;
	/**
	 * 성공시 구매 상품개수
	 */
	int count;
	
	/**
	 * 구매된 상품 정보
	 */
	List<TStoreProduct> product;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<TStoreProduct> getProduct() {
		return product;
	}

	public void setProduct(List<TStoreProduct> product) {
		this.product = product;
	}
	
	
	
}
