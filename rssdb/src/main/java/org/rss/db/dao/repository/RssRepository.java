package org.rss.db.dao.repository;

import com.google.common.base.Preconditions;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.rest.ServiceRestSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by Alain on 25/10/2015.
 */
@Repository
@Transactional
public class RssRepository {

	public static final Logger logger = LoggerFactory.getLogger(RssRepository.class);

	//@PersistenceUnit
	//private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager em;

	public void addChannel(FeedsRssJpa channel){

		logger.info("Enregistrement RSS debut");

		//logger.info("rss="+channel);

		//EntityManager tmp = emf.createEntityManager();

		logger.info("em ok");

		em.persist(channel);

		logger.info("Enregistrement RSS fin");
	}

	public List<FeedsRssJpa> listeChannel(){
		String req;
		List<FeedsRssJpa> res;

		req="select f from FeedsRssJpa f";

		res=em.createQuery(req,FeedsRssJpa.class).getResultList();

		return res;
	}

	public boolean channelExiste(FeedsRssJpa feeds){
		String req;
		List<FeedsRssJpa> res;

		Preconditions.checkNotNull(feeds);
		Preconditions.checkNotNull(feeds.getUrl());

		req="select f from FeedsRssJpa f where f.url='"+feeds.getUrl()+"'";

		res=em.createQuery(req,FeedsRssJpa.class).getResultList();

		return res!=null&&!res.isEmpty();
	}


	public boolean urlExiste(String url){
		String req;
		List<FeedsRssJpa> res;

		Preconditions.checkNotNull(url);

		req="select f from FeedsRssJpa f where f.url='"+url+"'";

		res=em.createQuery(req,FeedsRssJpa.class).getResultList();

		return res!=null&&!res.isEmpty();
	}


	public boolean nomExiste(String nom){
		String req;
		List<FeedsRssJpa> res;

		Preconditions.checkNotNull(nom);

		req="select f from FeedsRssJpa f where f.name='"+nom+"'";

		res=em.createQuery(req,FeedsRssJpa.class).getResultList();

		return res!=null&&!res.isEmpty();
	}
}
