package com.fish.apple.user.util;

import org.apache.commons.lang3.StringUtils;

public class LoginUtil {

	public static boolean validate(String login , String store) {
		return StringUtils.equals(login, store);
	}
	
}
