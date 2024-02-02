package com.spoparty.common.util;

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
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.ENCRYPT_FAIL);
		}
	}

	public String decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.DECRYPT_FAIL);
		}
	}
}
