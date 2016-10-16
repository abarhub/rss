package org.rss.db.dao;

import com.google.common.base.Preconditions;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.RssRepository;
import org.rss.db.dao.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alain on 30/01/2016.
 */
@Repository
@Transactional
public class UrlDao implements IUrlDao {

	public static final Logger LOGGER = LoggerFactory.getLogger(UrlDao.class);

	@Autowired
	private UrlRepository repository;

	@Autowired
	private RssRepository repo_rss;

	public UrlDao() {
	}

	@Override
	public void save(UrlJpa url) {
		Preconditions.checkNotNull(url);
		LOGGER.info("Sauve url : "+url.getUrl());
		repository.save(url);
	}

	@Override
	public List<UrlJpa> getListeUrl() {
		List<UrlJpa> liste1;

		liste1 = repository.findAllUrl();

		return liste1;
	}

	public void enregistre_rss(FeedsRssJpa rss) {
		Preconditions.checkNotNull(rss);
		if (repo_rss.channelExiste(rss)) {
			LOGGER.info("rss '" + rss.getUrl() + "' existe deja");
			repo_rss.updateChannel(rss);
		} else {
			LOGGER.info("Enregistrement rss '" + rss.getUrl() + "' ...");
			repo_rss.addChannel(rss);
			Preconditions.checkNotNull(rss.getId());
			UrlJpa u = repository.findByUrl(rss.getUrl());
			if(u!=null) {
				Preconditions.checkNotNull(u);
				u.setListe_feeds(rss);
				repository.save(u);
			}
			LOGGER.info("Enregistrement rss Ok");
		}
	}

	public List<FeedsRssJpa> liste_rss() {
		List<FeedsRssJpa> liste;

		liste = repo_rss.listeChannel();

		return liste;
	}

	public boolean isUrlExiste(String url) {
		Preconditions.checkNotNull(url);
		return repo_rss.urlExiste(url);
	}

	public boolean isNomExiste(String nom) {
		Preconditions.checkNotNull(nom);
		return repo_rss.nomExiste(nom);
	}
}
