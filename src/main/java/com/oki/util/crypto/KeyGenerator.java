package com.oki.util.crypto;

import java.security.MessageDigest;

public class KeyGenerator {

	private static final String ENCRYPT_ALGORITHM_SHA256 = "SHA-256";
    private static final String ENCODING = "UTF-8";
    
    public static String makeKey(String key) throws Exception{
       MessageDigest md = MessageDigest.getInstance(ENCRYPT_ALGORITHM_SHA256);
       md.update(key.getBytes(ENCODING));
       byte byteData[] = md.digest();

       StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
        }
       return sb.toString();
    }
	
}
