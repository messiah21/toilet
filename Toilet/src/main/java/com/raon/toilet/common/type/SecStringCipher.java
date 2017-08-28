package com.raon.toilet.common.type;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.raon.toilet.common.util.Base64Util;


/**
 * @author jjlim
 * 
 * 암호문 생성 규칙 규칙  : SALT_LEN[1] | AES128_CBC_PKCS5(SALT_LEN[N] | DATA[...])
 */
public class SecStringCipher {

	private static final String DEFAULT_CRYPT_KEY			= "NDNDJKVBNWKJDFKK";
	private static final String DEFAULT_CRYPT_IV 			= "CMSDHQUKSKSIQLZL";
	
	private static SecretKey aesKey = null;

	public SecStringCipher(String secureKey) {
		initCipher(secureKey);
	}

	public SecStringCipher() {
		initCipher(SecStringCipher.DEFAULT_CRYPT_KEY);
	}
	
	private void initCipher(String secureKey) {
	    try {
			byte[] keyBytes = null;
			
			if (null == secureKey || secureKey.isEmpty()) {
				keyBytes = SecStringCipher.DEFAULT_CRYPT_KEY.getBytes("UTF-8");
			} else {
				keyBytes = secureKey.getBytes("UTF-8");
			}
			
	        aesKey = new SecretKeySpec(keyBytes, "AES");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String encryptData(byte[] plainData, int saltLen) {
	    if (saltLen < 0 || 255 < saltLen)
	    	saltLen = 0;
		
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    byte [] iv = SecStringCipher.DEFAULT_CRYPT_IV.getBytes("UTF-8");
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));
	        
	        byte[] data = new byte[saltLen + plainData.length];
	        {
		        if (0 < saltLen) {
			        byte[] salt = new byte[saltLen];
			        new SecureRandom().nextBytes(salt);
			        
			        System.arraycopy(salt, 0, data, 0, salt.length);
		        }
		        System.arraycopy(plainData, 0, data, saltLen, plainData.length);
	        }
	        
	        byte[] cipherData = cipher.doFinal(data);
	        
	        byte[] result = new byte[1 + cipherData.length];
	        {
		        result[0] = (byte)saltLen;
		        System.arraycopy(cipherData, 0, result, 1, cipherData.length);
	        }
	        return Base64Util.base64encode(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public byte[] decryptData(String cipherString) {
        Cipher cipher;
		try {
	        byte[] data = Base64Util.base64decode(cipherString);
	        int saltLen = data[0];

	        // 암호문 길이 확인
		    if (0 != ((data.length - 1) & 0x000F))	return null; // 암호문의 길이가 16의 배수가 아닌 경우
		    if (data.length <= saltLen) 			return null; // salt를 제외한 실제 데이터의 길이가 0보다 작은 경우
	        
		    byte[] cipherData = new byte[data.length - 1];
		    {
		        System.arraycopy(data, 1, cipherData, 0, cipherData.length);
		    }
	        
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    byte [] iv = SecStringCipher.DEFAULT_CRYPT_IV.getBytes("UTF-8");
	        cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));

	        byte[] result = cipher.doFinal(cipherData);

	        byte[] plainData = new byte[result.length - saltLen];
	        System.arraycopy(result, saltLen, plainData, 0, plainData.length);
	        
	        return plainData;
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}

	public String encryptString(String plainString, int saltLen) {
		try {
			return encryptData(plainString.getBytes("UTF-8"), saltLen);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public String decryptString(String cipherString) {
		try {
			return new String(decryptData(cipherString), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}
	
}
