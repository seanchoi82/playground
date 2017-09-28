package kr.ko.nexmain.server.MissingU.common.security;

public interface Crypto {
	/**
	 * 암호화
	 * @param plainText 원본 문자열
	 * @return 암호화된 문자열
	 */
	String encrypt(String plainText) throws Exception;
	
	/**
	 * 복호화
	 * @param encryptText 암호화된 문자열
	 * @return 복호화된 원본 문자열
	 */
	String decrypt(String encryptText) throws Exception;
}
