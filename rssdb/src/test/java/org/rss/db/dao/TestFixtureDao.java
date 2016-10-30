package org.rss.db.dao;

import com.google.common.base.Preconditions;
import org.rss.beans.OutilsGeneriques;
import org.rss.db.dao.jpa.*;
import org.rss.db.dao.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Alain on 11/09/2016.
 */
@Service
public class TestFixtureDao {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRssDao rssDao;

	@Autowired
	private CategorieRepository categorieRepository;

	public RoleJpa getRole(String nomRole) throws ErrorJpaException {
		Preconditions.checkArgument(!OutilsGeneriques.vide(nomRole));
		RoleJpa roleJpa=userDao.addRole(nomRole,"BBB");
		assertNotNull(roleJpa);
		assertNotNull(roleJpa.getId());
		return roleJpa;
	}


	public UserJpa getUser(String login) throws ErrorJpaException {
		Preconditions.checkArgument(!OutilsGeneriques.vide(login));
		UserJpa userJpa;

		userJpa=new UserJpa();
		userJpa.setLogin(login);
		userJpa.setNom("Nom1");
		userJpa.setPrenom("Prenom1");
		userJpa.setPassword("DDD");
		userJpa.setRole(getRole("AAA"));
		userJpa=userDao.addUser(userJpa);
		assertNotNull(userJpa);
		assertNotNull(userJpa.getId());
		return userJpa;
	}

	public CategorieJpa getCategorie(UserJpa userJpa, String nom){
		Preconditions.checkNotNull(userJpa);
		Preconditions.checkArgument(!OutilsGeneriques.vide(nom));
		CategorieJpa categorieJpa,categorieJpa2;
		categorieJpa=new CategorieJpa();
		categorieJpa.setName(nom);
		categorieJpa.setDescription("BBB");
		categorieJpa.setUserJpa(userJpa);

		categorieJpa2=rssDao.addCategorie(categorieJpa);

		assertNotNull(categorieJpa2);
		assertNotNull(categorieJpa2.getId());
		return categorieJpa2;
	}


	public FeedsNameJpa getFeedsNameJpa(UrlJpa urlJpa) throws ErrorJpaException {
		Preconditions.checkNotNull(urlJpa);
		FeedsNameJpa feedsNameJpa;
		feedsNameJpa=new FeedsNameJpa();
		feedsNameJpa.setName("AAA");
		feedsNameJpa.setUrlJpa(urlJpa);
		UserJpa userJpa=getUser("HHH");
		CategorieJpa categorieJpa=getCategorie(userJpa,"TTT");
		feedsNameJpa.setCategorieJpa(categorieJpa);
		return feedsNameJpa;
	}

	public FeedsRssJpa getFeedsRssJpa(String url,String description) {
		Preconditions.checkNotNull(url);
		Preconditions.checkNotNull(description);
		FeedsRssJpa rss;
		rss=new FeedsRssJpa();
		rss.setUrl(url);
		rss.setDescription(description);
		rss.setLanguage("FR");
		return rss;
	}

}
