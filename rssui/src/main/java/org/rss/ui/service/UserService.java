package org.rss.ui.service;

import org.rss.beans.security.UserInfoDTO;
import org.rss.registry.IRestDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Alain on 15/10/2016.
 */
@Service
public class UserService {

	@Autowired
	private IRestDb restDb;

	public String getUserId(){
		String login= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return login;
	}

	public UserInfoDTO getUserConnected(){
		String id=getUserId();

		if(StringUtils.isEmpty(id)){
			return null;
		} else {
			ResponseEntity<UserInfoDTO> res = restDb.getUser(id);
			if(res.getStatusCode().is2xxSuccessful()&&res.hasBody()){
				UserInfoDTO u = res.getBody();
				return u;
			} else{
				return null;
			}
		}
	}
}
