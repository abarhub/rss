package org.rss.ui.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alain on 24/09/2016.
 */
@Service
public class LoginFailureLog extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoginFailureLog.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		LOGGER.info("login error : "+e.getMessage());
		super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
	}

}
