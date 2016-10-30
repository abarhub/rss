package org.rss.db.service;

import com.google.common.base.Preconditions;
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
import org.springframework.util.StringUtils;

import static org.rss.db.dao.Constantes.CATEGORIE_TOUT_DESCRIPTION;
import static org.rss.db.dao.Constantes.CATEGORIE_TOUT_NOM;

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
		Preconditions.checkNotNull(userId);
		Preconditions.checkArgument(!StringUtils.isEmpty(userId));
		Preconditions.checkNotNull(rss);
		Preconditions.checkArgument(!StringUtils.isEmpty(rss.getUrl()));
		Preconditions.checkArgument(!StringUtils.isEmpty(rss.getNom()));

		UserJpa u = userDao.findUserByLogin(userId);
		if(u==null){
			throw new SecurityException("Utilisateur non trouvé");
		}

		CategorieJpa c = categorieRepository.findByNameAndUserJpa(CATEGORIE_TOUT_NOM,u);

		if(c==null){
			c=new CategorieJpa();
			c.setTout(true);
			c.setName(CATEGORIE_TOUT_NOM);
			c.setDescription(CATEGORIE_TOUT_DESCRIPTION);
			c.setUserJpa(u);
			c=categorieRepository.save(c);
		}
		Preconditions.checkNotNull(c);

		UrlJpa rss2;
		rss2=urlRepository.findByUrl(rss.getUrl());
		if(rss2==null){
			rss2=new UrlJpa();
			rss2.setUrl(rss.getUrl());

			rss2=urlRepository.save(rss2);
		}
		Preconditions.checkNotNull(rss2);

		FeedsNameJpa feedsNameJpa;
		feedsNameJpa=new FeedsNameJpa();
		feedsNameJpa.setName(rss.getNom());
		feedsNameJpa.setUrlJpa(rss2);
		feedsNameJpa.setCategorieJpa(c);
		feedsNameJpa.setFeeds(rss2.getListe_feeds());
		feedsNameRepository.save(feedsNameJpa);

	}
}
