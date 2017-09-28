package kr.ko.nexmain.server.MissingU.common.security;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.codec.Base64;
import org.springframework.util.StringUtils;

/**
 * AES256 암호화 알고리즘
 * @author nefer
 */
public class AES256 implements Crypto {
	
	private byte[] sk = null;
	private byte[] iv = null;
	
	private static String ALGORITHM_AES = "AES";
	//private static String ALGORITHM_AES_FULL = "AES/CBC/PKCS7Padding";
	private static String ALGORITHM_AES_FULL = "AES/CBC/PKCS5Padding";
	
	public AES256(String strKey, String strIv){
		this.sk = strKey.getBytes();
		this.iv = strIv.getBytes();
	}
	
	/**
	 * 보안 KeySpec 생성
	 */
	private SecretKeySpec getKeySpec() throws IOException, NoSuchAlgorithmException { 
		return new SecretKeySpec(sk, ALGORITHM_AES); 
	}
	/**
	 * IV Spec 생성
	 */
	private IvParameterSpec getIV() { 
		return new IvParameterSpec(iv);
	}
	
	/**
	 * 암호화
	 * @param context
	 * @param plainText 원본 문자열
	 * @return 암호화된 문자열
	 */
	@Override
	public String encrypt(String plainText) throws Exception
	{		
		if(sk == null || iv == null) {
			throw new Exception("security key or iv key is empty.");
		}
		
		if(!StringUtils.hasLength(plainText))
			return null;
		
		try {
			
			SecretKeySpec spec = getKeySpec();
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES_FULL);
			cipher.init(Cipher.ENCRYPT_MODE, spec, getIV());
			return new String(Base64.encode(cipher.doFinal(plainText.getBytes())));
			
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("지원되지 않는 암호화 알고리즘 입니다.", e);
		} catch (IOException e) {
			throw new Exception("인코딩 처리중 예외오류가 발생 되었습니다.", e);
		} catch (NoSuchPaddingException e) {
			throw new Exception("지원되지 않는 암호화 알고리즘 Padding 값 입니다.", e);
		} catch (InvalidKeyException e) {
			throw new Exception("개인키가 유효하지 않습니다.", e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new Exception("알고리즘 설정이 유효하지 않습니다.", e);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("유효하지 않은 블럭 크기 입니다.", e);
		} catch (BadPaddingException e) {
			throw new Exception("패딩이 잘못 되어 있습니다.", e);
		}
	}
	
	/**
	 * 복화화
	 * @param encryptText 암호화된 문자열
	 * @return 복호화된 문자열
	 */
	@Override
	public String decrypt(String encryptText) throws Exception{
		
		if(sk == null || iv == null) {
			throw new Exception("security key or iv key is empty.");
		}
		
		if(!StringUtils.hasLength(encryptText))
			return null;
		
		try {
			
			SecretKeySpec spec = getKeySpec(); 
	        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_FULL); 
	        cipher.init(Cipher.DECRYPT_MODE, spec, getIV());
	        return new String(cipher.doFinal(Base64.decode(encryptText.getBytes())));
	        
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("지원되지 않는 암호화 알고리즘 입니다.", e);
		} catch (IOException e) {
			throw new Exception("인코딩 처리중 예외오류가 발생 되었습니다.", e);
		} catch (NoSuchPaddingException e) {
			throw new Exception("지원되지 않는 암호화 알고리즘 Padding 값 입니다.", e);
		} catch (InvalidKeyException e) {
			throw new Exception("개인키가 유효하지 않습니다.", e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new Exception("알고리즘 설정이 유효하지 않습니다.", e);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("유효하지 않은 블럭 크기 입니다.", e);
		} catch (BadPaddingException e) {
			throw new Exception("패딩이 잘못 되어 있습니다.", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		AES256 aes = new AES256("0OKM9IJN8UHB7YGV", "0OKM9IJN8UHB7YGV");
		String encStr = aes.encrypt("안녕하세요");
		System.out.println("encStr : " + encStr);
		String decStr = aes.decrypt(encStr);
		System.out.println("decStr : " + decStr);
	}

}
