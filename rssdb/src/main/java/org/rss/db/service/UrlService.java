package org.rss.db.service;

import org.rss.db.dao.ErrorJpaException;
import org.rss.db.dao.IUrlDao;
import org.rss.db.dao.UserDao;
import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.FeedsNameJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.rss.db.dao.repository.CategorieRepository;
import org.rss.db.dao.repository.FeedsNameRepository;
import org.rss.db.dao.repository.UrlRepository;
import org.rss.db.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private CategorieRepository categorieRepository;

	@Autowired
	private FeedsNameRepository feedsNameRepository;

	public UrlService() {
	}

	public void save(String userId, UrlJpa rss) throws ErrorJpaException {
		UserJpa u = userDao.findUserByLogin(userId);
		if(u==null){
			throw new SecurityException("Utilisateur non trouvé");
		}

		CategorieJpa c = categorieRepository.findByNameAndUserJpa("ALL",u);

		if(c==null){
			c=new CategorieJpa();
			c.setTout(true);
			c.setName("ALL");
			c.setUserJpa(u);
			c=categorieRepository.save(c);
		}

		/*if(u.getUrlList()==null){
			u.setUrlList(new ArrayList<>());
		}
		u.getUrlList().add(rss);
		rss.setUser(u);*/
		rss=urlRepository.save(rss);
		//userRepository.save(u);

		FeedsNameJpa feedsNameJpa;
		feedsNameJpa=new FeedsNameJpa();
		feedsNameJpa.setName(rss.getNom());
		feedsNameJpa.setUrlJpa(rss);
		feedsNameJpa.setCategorieJpa(c);
		feedsNameRepository.save(feedsNameJpa);

	}
}
