package org.rss.db.dao;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.RssRepository;
import org.rss.db.dao.repository.UrlRepository;
import org.rss.db.rest.ServiceRestSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alain on 30/01/2016.
 */
@Repository
public class DAOUrl implements IDAOUrl {

	public static final Logger logger = LoggerFactory.getLogger(DAOUrl.class);

	@Autowired
	private UrlRepository repository;

	@Autowired
	private RssRepository repo_rss;

	public DAOUrl() {
	}

	@Override
	public void save(UrlJpa url)
	{
		repository.save(url);
	}

	@Override
	public List<UrlJpa> getListeUrl()
	{
		List<UrlJpa> liste1=null;// = repository.getListe();

		if(false) {
			liste1 = Lists.newArrayList(repository.findAll());
		}
		else
		{
			liste1=repository.findAllUrl();
		}

		return liste1;
	}

	public void enregistre_rss(FeedsRssJpa rss)
	{
		Preconditions.checkNotNull(rss);
		if(repo_rss.channelExiste(rss))
		{
			logger.info("rss '"+rss.getUrl()+"' existe deja");
		}
		else {
			logger.info("Enregistrement rss '"+rss.getUrl()+"' ...");
			repo_rss.addChannel(rss);
			logger.info("Enregistrement rss Ok");
		}
	}

	public List<FeedsRssJpa> liste_rss(){
		List<FeedsRssJpa> liste;

		liste=repo_rss.listeChannel();

		return liste;
	}
}
