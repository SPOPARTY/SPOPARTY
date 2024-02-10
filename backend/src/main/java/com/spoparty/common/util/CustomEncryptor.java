package com.spoparty.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.spoparty.api.common.constants.ErrorCode;
import com.spoparty.api.common.exception.CustomException;

@Component
public class CustomEncryptor {
	private final String SECRET_KEY = "!o4s*7xj#o81Ac&l";

	public String encrypt(String strToEncrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			String encodingString = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
			return URLEncoder.encode(encodingString, "UTF-8");
		} catch (Exception e) {
			throw new CustomException(ErrorCode.ENCRYPT_FAIL);
		}
	}

	public String decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			String decodingString = URLDecoder.decode(strToDecrypt, "UTF-8"); // '/' 들어가지 않은 경우
			return new String(cipher.doFinal(Base64.getDecoder().decode(decodingString)));
		} catch (Exception e) {
			try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
				return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt))); // '/' 들어가는 경우 -> URLDecoder decode 불필요
			} catch (Exception err) {
				throw new CustomException(ErrorCode.DECRYPT_FAIL);
			}
		}
	}
}
