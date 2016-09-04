package org.rss.db.dao.repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.rss.beans.OutilsGeneriques;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;
import org.rss.db.rest.ServiceRestSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

	public static final Logger LOGGER = LoggerFactory.getLogger(RssRepository.class);

	//@PersistenceUnit
	//private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager em;

	public void addChannel(FeedsRssJpa channel){

		LOGGER.info("Enregistrement RSS debut");

		//LOGGER.info("rss="+channel);

		//EntityManager tmp = emf.createEntityManager();

		LOGGER.info("em ok");

		em.persist(channel);

		LOGGER.info("Enregistrement RSS fin");
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

	public void updateChannel(FeedsRssJpa rss) {
		Preconditions.checkNotNull(rss);
		Preconditions.checkNotNull(rss.getLastBuildDate());

		FeedsRssJpa rssPersiste;

		String req="select f from FeedsRssJpa f where f.url='"+rss.getUrl()+"'";

		rssPersiste=em.createQuery(req,FeedsRssJpa.class).getSingleResult();

		Preconditions.checkNotNull(rssPersiste);
		if(OutilsGeneriques.estInf(rssPersiste.getLastBuildDate(),rss.getLastBuildDate())){
			if(!StringUtils.isEmpty(rss.getTitle())&&
					!rss.getTitle().equals(rssPersiste.getTitle())) {
				rssPersiste.setTitle(rss.getTitle());
			}
			rssPersiste.setLastBuildDate(rss.getLastBuildDate());

			if(rss.getListeItem()!=null&&!rss.getListeItem().isEmpty()) {
				for (ItemRssJpa item:rss.getListeItem()){

					Preconditions.checkNotNull(item.getLink());
					Preconditions.checkNotNull(item.getPubDate());

					if(rssPersiste.getListeItem()==null||rssPersiste.getListeItem().isEmpty()){
						rssPersiste.setListeItem(Lists.newArrayList());
						rssPersiste.getListeItem().add(item);
					} else {
						boolean trouve=false;
						for (ItemRssJpa item2 : rssPersiste.getListeItem()) {
							if(item2.getLink()!=null&&item2.getPubDate()!=null){
								if(item2.getLink().equals(item.getLink())){
									trouve=true;
									if(OutilsGeneriques.estInf(item2.getPubDate(),item.getPubDate())){
										item2.setTitle(item.getTitle());
										item2.setDescription(item.getDescription());
										item2.setGuid(item.getGuid());
										item2.setLink(item.getLink());
										item2.setPubDate(item.getPubDate());
									}
									break;
								}
							}
						}
						if(!trouve){
							rssPersiste.getListeItem().add(item);
						}
					}
				}
			}

			em.persist(rssPersiste);
		}
	}
}
