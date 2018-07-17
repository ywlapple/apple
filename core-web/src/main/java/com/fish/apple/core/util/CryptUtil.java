package com.fish.apple.core.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fish.apple.core.common.exception.BussinessException;

public class CryptUtil {
	
	public static final String MAC_INSTANCE_NAME = "HMacSHA256";
	public static final String JWT_HEADER = "{\"type\":\"JWT\",\"alg\":\"HS256\"}";
	public static String Hmacsha256(String secret, String message) {
		try {
		    Mac hmac_sha256 = Mac.getInstance(MAC_INSTANCE_NAME);
		    SecretKeySpec key = new SecretKeySpec(secret.getBytes(), MAC_INSTANCE_NAME);
		    hmac_sha256.init(key);
		    byte[] buff = hmac_sha256.doFinal(message.getBytes());
		    return Base64.getEncoder().encodeToString(buff);
		}catch(Exception e) {
			throw BussinessException.create("加密失败", e);
		}

	}
}
