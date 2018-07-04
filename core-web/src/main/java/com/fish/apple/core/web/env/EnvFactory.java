package com.fish.apple.core.web.env;

import javax.servlet.http.HttpServletRequest;

public interface EnvFactory {

	WebEnv fillWeb(HttpServletRequest request);
	User fillUser(HttpServletRequest request);
}
