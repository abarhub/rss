package org.rss.ui.security;

import com.google.common.collect.Lists;
import org.rss.beans.metier.LoginResponseDTO;
import org.rss.registry.IRestDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Alain on 24/09/2016.
 */
@Service
public class CustomAuthenticationManager implements AuthenticationManager {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationManager.class);

	@Autowired
	private IRestDb restDb;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String login="",password="";
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

		if(authentication.getPrincipal() instanceof String) {
			login = (String) authentication.getPrincipal();
		}
		if(authentication.getCredentials()!=null) {
			password = authentication.getCredentials()+"";
		}
		LOGGER.info("Login authenticate : login="+login);
		LOGGER.info("Login authenticate : password="+password);
		LOGGER.info("Login authenticate : isAuthenticated="+authentication.isAuthenticated());

		if(!loginUser(login, password)){
			LOGGER.info("Login authenticate : KO");
			throw new BadCredentialsException("Erreur d'authentification");
		} else {
			SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("ADMIN");
			List<GrantedAuthority> listeRoles= Lists.newArrayList(simpleGrantedAuthority);
			usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, password,listeRoles);
			LOGGER.info("Login authenticate : OK");
		}

		return usernamePasswordAuthenticationToken;
	}

	private boolean loginUser(String login, String password) {
		try {
			if (false) {
				return StringUtils.isEmpty(login) || StringUtils.isEmpty(password);
			} else {
				ResponseEntity<LoginResponseDTO> res = restDb.connecteUser(login, password);
				if(res.getStatusCode().is2xxSuccessful()){
					if(res.hasBody()){
						LoginResponseDTO b = res.getBody();
						return b.isLoginOk();
					}else {
						LOGGER.error("Erreur pour vérifier la connexion : la ressource n'a rien retournée");
						return false;
					}
				}else {
					LOGGER.error("Erreur pour vérifier la connexion : code retour = "+res.getStatusCode().value());
					return false;
				}
			}
		}catch(Exception e){
			LOGGER.error("Erreur pour vérifier la connexion : "+e.getMessage(),e);
			return false;
		}
	}
}
