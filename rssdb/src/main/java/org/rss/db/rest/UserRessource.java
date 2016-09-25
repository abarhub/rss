package org.rss.db.rest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.beans.metier.LoginDTO;
import org.rss.beans.metier.LoginResponseDTO;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.ErrorJpaException;
import org.rss.db.dao.IUrlDao;
import org.rss.db.dao.IUserDao;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.UrlRssRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
