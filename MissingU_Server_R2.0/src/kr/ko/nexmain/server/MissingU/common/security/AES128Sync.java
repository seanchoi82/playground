package kr.ko.nexmain.server.MissingU.common.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kr.ko.nexmain.server.MissingU.common.Constants;

import org.apache.commons.codec.binary.Base64;

/**
 * Android-Java-Php-Cshop-Perl-Javascript 공통(키 길이가 짧으니 주의할것 16 = AES128bit)
 * @author 영환
 *
 */
public class AES128Sync {

	public static String encode(String str) {
		
		if(str == null || str.length() ==0)
			return "";
		
		try {
			byte[] textBytes = str.getBytes("UTF-8");
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(Constants.IV_PARAMETER_SYNC);
		    SecretKeySpec newKey = new SecretKeySpec(Constants.AES_KEY_SYNC, "AES");
		    Cipher cipher = null;
		    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		    return Base64.encodeBase64String(cipher.doFinal(textBytes));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	
		return "";
	}

	public static String decode(String str){
		
		if(str == null || str.length() ==0)
			return "";
		
		try {
			byte[] textBytes = Base64.decodeBase64(str);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(Constants.IV_PARAMETER_SYNC);
			SecretKeySpec newKey = new SecretKeySpec(Constants.AES_KEY_SYNC, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
			return new String(cipher.doFinal(textBytes), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public static void main(String[] args) {
//		System.out.println(decode("7pegDMlrGaEDTRWQF/a8r15FoDNEr7CaoJcOvmvrRxY="));
	}
	
}
