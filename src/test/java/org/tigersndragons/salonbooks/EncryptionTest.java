package org.tigersndragons.salonbooks;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.service.EncryptionService;

public class EncryptionTest extends BaseTestCase {

	@Autowired EncryptionService encryptionService;
	
//	@Ignore
	@Test
	public void testEncryption(){
		String pswd = "password1";
		String encryptedPswd = encryptionService.encryptString(pswd);
		System.out.println(encryptedPswd);

		assertTrue(encryptedPswd!=null);
		//assertTrue(StringUtils.equals(encryptedPswd, "GCW9W9ttFYlWE35zF9V/LA=="));
		String decryptedPswd = encryptionService.decryptString(encryptedPswd);
		assertTrue(StringUtils.equals(decryptedPswd, pswd));
	}
	@Ignore
	@Test
	public void testEncryptionCoded(){
		String pswd = "password1";
		String encryptedPswd = encryptionService.encryptString(pswd);
		System.out.println(encryptedPswd);

		assertTrue(encryptedPswd!=null);
		assertTrue(StringUtils.equals(encryptedPswd, "uJSU3i56jEmQv6GnNY4FYQ=="));
	}
}
