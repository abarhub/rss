package org.rss.ui.security;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.rss.ui.rest.ListeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by Alain on 20/09/2016.
 */
public class ProperCookieClearingLogoutHandler implements LogoutHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProperCookieClearingLogoutHandler.class);

	private final List<String> cookiesToClear;

	public ProperCookieClearingLogoutHandler(String... cookiesToClear) {
		Assert.notNull(cookiesToClear, "List of cookies cannot be null");
		this.cookiesToClear = Arrays.asList(cookiesToClear);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response,
	                   Authentication authentication) {
		for (String cookieName : cookiesToClear) {
			Cookie cookie = new Cookie(cookieName, null);
			String cookiePath = request.getContextPath() + "/";
			if (!StringUtils.hasLength(cookiePath)) {
				cookiePath = "/";
			}
			cookie.setPath(cookiePath);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		try{
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}catch(Exception e){
			LOGGER.error("Error pour invalider la session : "+e.getMessage(),e);
		}
	}
}
