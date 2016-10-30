package org.rss.ui.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alain on 24/09/2016.
 */
//@Service
public class LoginSuccesLog extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoginSuccesLog.class);

	public LoginSuccesLog() {
		super();
	}

	public LoginSuccesLog(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		LOGGER.info("login succes : "+authentication.getName());
		super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
	}

}
