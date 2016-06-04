package org.tigersndragons.salonbooks.service.impl;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.tigersndragons.salonbooks.service.EncryptionService;

public class EncryptionServiceImpl extends BaseServiceImpl implements EncryptionService {
	public String encryptString(String s){

		try {

			return  encrypt(s);//encodeMsg(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
		
	}
	
	public String decryptString(String s){
		try {
			return decrypt(s);//decrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
		
	}

	private static byte[] rawKey =new byte[]{'1','4','5','2','3','6','7','4','8','5','8','a','Z','a','b','b'};
	private static SecureRandom rnd = new SecureRandom();
	private static IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(16));
	private static final String CHARSET="US-ASCII";//"UTF-8";

	public static String encrypt(String value) throws GeneralSecurityException {

		SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(value.getBytes());
		return Base64.encodeBase64String(encrypted);
	}

	public static String decrypt(String encrypted)
			throws GeneralSecurityException {

		SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

		return new String(original);
	}

}
