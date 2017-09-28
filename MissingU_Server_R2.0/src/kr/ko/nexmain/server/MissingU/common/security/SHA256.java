package kr.ko.nexmain.server.MissingU.common.security;

import java.security.MessageDigest;

/**
 * 해쉬 알고리즘 (단방향)
 * @author nefer
 *
 */
public class SHA256 implements Crypto {

	public SHA256() {
		
	}

	/**
	 * 암호화
	 * @param Context context
	 * @param plainText 원본 문자열
	 */
	@Override
	public String encrypt(String plainText) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(plainText.getBytes());
        
        byte[] mdbytes = md.digest();
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<mdbytes.length;i++) {
    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
    	}
    	
		return hexString.toString();
	}
	
	/**
	 * Hash 알고리즘은 복호화를 지원하지 않습니다.
	 * @deprecated
	 */
	@Override
	public String decrypt(String encryptText) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
}
