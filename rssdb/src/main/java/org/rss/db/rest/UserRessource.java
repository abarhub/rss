package org.rss.db.rest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.beans.metier.*;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.ErrorJpaException;
import org.rss.db.dao.IUrlDao;
import org.rss.db.dao.IUserDao;
import org.rss.db.dao.jpa.*;
import org.rss.db.dao.repository.UrlRssRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alain on 30/01/2016.
 */
@RestController
public class UserRessource {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserRessource.class);

	@Autowired
	private IUserDao userDao;

	@RequestMapping(value = "/api3/user_exists",method = RequestMethod.POST)
	public LoginResponseDTO userExiste(@RequestBody LoginDTO identifiant)
	{
		LoginResponseDTO res;
		String login,password;
		Preconditions.checkNotNull(identifiant);
		login=identifiant.getLogin();
		password=identifiant.getPassword();
		LOGGER.info("tentative connexion login={} password={}",login,password);
		try {
			if(!userDao.connectUser(login,password)){
				res=new LoginResponseDTO();
				res.setLoginOk(false);
				res.setMessageError("Erreur d'autentification");
			} else {
				res=new LoginResponseDTO();
				res.setLoginOk(true);
				res.setIdUser("1");
			}
		} catch (ErrorJpaException e) {
			LOGGER.info("tentative connexion error:"+e.getMessage(),e);
			res=new LoginResponseDTO();
			res.setLoginOk(false);
			res.setMessageError("Erreur d'autentification");
		}
		LOGGER.info("tentative connexion res={}",res);
		return res;
	}


	@RequestMapping(value = "/api3/users_search",method = RequestMethod.POST)
	public SearchUsersResponseDTO searchUser(@RequestBody SearchUsersDTO searchUsersDTO)
	{
		Preconditions.checkNotNull(searchUsersDTO);
		Preconditions.checkArgument(!StringUtils.isEmpty(searchUsersDTO.getNom()));

		List<UserJpa> liste = userDao.searchUser(searchUsersDTO.getNom());
		SearchUsersResponseDTO searchUsersResponseDTO=new SearchUsersResponseDTO();
		UserDTO userDTO;

		if(!CollectionUtils.isEmpty(liste)){
			searchUsersResponseDTO.setListUserDTO(new ArrayList<>());
			for(UserJpa userJpa:liste){
				userDTO=new UserDTO();
				userDTO.setNom(userJpa.getNom());
				userDTO.setPrenom(userJpa.getPrenom());
				userDTO.setLogin(userJpa.getLogin());
				userDTO.setNonModifiable(false);
				userDTO.setId(userJpa.getId());
				searchUsersResponseDTO.getListUserDTO().add(userDTO);
			}
		}

		return searchUsersResponseDTO;
	}


	@RequestMapping(value = "/api3/add_user",method = RequestMethod.POST)
	public Boolean addUser(@RequestBody UserDTO userDTO)
	{
		boolean res=false;
		Preconditions.checkNotNull(userDTO);
		UserJpa userJpa=new UserJpa();
		userJpa.setLogin(userDTO.getLogin());
		userJpa.setPassword(userDTO.getPassword());
		userJpa.setNom(userDTO.getNom());
		userJpa.setPrenom(userDTO.getPrenom());
		try {
			RoleJpa role = userDao.findRoleByNom("SIMPLE_USER");
			Preconditions.checkNotNull(role);
			userJpa.setRole(role);
			userDao.addUser(userJpa);
			res=true;
		} catch (ErrorJpaException e) {
			LOGGER.error("Erreur pour ajouter l'utilisateur :"+e.getMessage(),e);
		}
		return res;
	}
}
