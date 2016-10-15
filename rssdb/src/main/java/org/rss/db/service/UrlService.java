package org.rss.db.service;

import org.rss.db.dao.ErrorJpaException;
import org.rss.db.dao.IUrlDao;
import org.rss.db.dao.UserDao;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.rss.db.dao.repository.UrlRepository;
import org.rss.db.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by Alain on 15/10/2016.
 */
@Service
@Transactional
public class UrlService {

	@Autowired
	private IUrlDao dao_url;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UrlRepository urlRepository;

	public UrlService() {
	}

	public void save(String userId, UrlJpa rss) throws ErrorJpaException {
		UserJpa u = userDao.findUserByLogin(userId);
		if(u==null){
			throw new SecurityException("Utilisateur non trouvé");
		}

		if(u.getUrlList()==null){
			u.setUrlList(new ArrayList<>());
		}
		u.getUrlList().add(rss);
		rss.setUser(u);
		urlRepository.save(rss);
		userRepository.save(u);
	}
}
