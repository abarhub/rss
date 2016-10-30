package org.rss.db.dao.repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.rss.beans.OutilsGeneriques;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Alain on 25/10/2015.
 */
@Repository
@Transactional
public class Rss2Repository  {

	public static final Logger LOGGER = LoggerFactory.getLogger(Rss2Repository.class);

	@Autowired
	private Rss3Repository rss3Repository;

	public void addChannel(FeedsRssJpa channel){

		LOGGER.info("Enregistrement RSS debut");

		rss3Repository.save(channel);

		LOGGER.info("Enregistrement RSS fin");
	}

	public List<FeedsRssJpa> listeChannel(){
		String req;
		List<FeedsRssJpa> res;

		res=new ArrayList<>();
		Iterables.addAll(res,rss3Repository.findAll());

		return res;
	}

	public boolean channelExiste(FeedsRssJpa feeds){
		Preconditions.checkNotNull(feeds);
		Preconditions.checkNotNull(feeds.getUrl());

		Optional<FeedsRssJpa> optFeeds = rss3Repository.findByUrl(feeds.getUrl());

		return optFeeds.isPresent();
	}


	public boolean urlExiste(String url){

		Preconditions.checkNotNull(url);

		Optional<FeedsRssJpa> optFeeds = rss3Repository.findByUrl(url);

		return optFeeds.isPresent();
	}


	public boolean nomExiste(String nom){
		String req;
		List<FeedsRssJpa> res;

		Preconditions.checkNotNull(nom);

		Optional<FeedsRssJpa> optFeeds = rss3Repository.findByName(nom);

		return optFeeds.isPresent();
	}

	public FeedsRssJpa updateChannel(String url, FeedsRssJpa rss) {
		Preconditions.checkNotNull(rss);
		Preconditions.checkNotNull(rss.getUrl());

		LOGGER.info("debut maj du flux "+url);

		FeedsRssJpa rssPersiste;

		Optional<FeedsRssJpa> optRssPersiste = rss3Repository.findByUrl(url);

		Preconditions.checkArgument(optRssPersiste.isPresent());

		rssPersiste=optRssPersiste.get();

		Preconditions.checkNotNull(rssPersiste);
		if(rssPersiste.getLastBuildDate()==null
				||OutilsGeneriques.estInf(rssPersiste.getLastBuildDate(),rss.getLastBuildDate())){
			if(!StringUtils.isEmpty(rss.getTitle())&&
					!rss.getTitle().equals(rssPersiste.getTitle())) {
				rssPersiste.setTitle(rss.getTitle());
			}
			if(rss.getLastBuildDate()!=null) {
				rssPersiste.setLastBuildDate(rss.getLastBuildDate());
			}

			if(rss.getListeItem()!=null&&!rss.getListeItem().isEmpty()) {
				for (ItemRssJpa item:rss.getListeItem()){

					Preconditions.checkNotNull(item.getLink(),"item="+item);
					Preconditions.checkNotNull(item.getPubDate(),"item="+item);

					if(rssPersiste.getListeItem()==null||rssPersiste.getListeItem().isEmpty()){
						rssPersiste.setListeItem(Lists.newArrayList());
						rssPersiste.getListeItem().add(item);
						LOGGER.info("add du flux (vide):"+item);
					} else {
						boolean trouve=false;
						for (ItemRssJpa item2 : rssPersiste.getListeItem()) {
							if(item2.getLink()!=null&&item2.getPubDate()!=null){
								if(item2.similaire(item)){
									trouve=true;
									if(OutilsGeneriques.estInf(item2.getPubDate(),item.getPubDate())){
										item2.setTitle(item.getTitle());
										item2.setDescription(item.getDescription());
										item2.setGuid(item.getGuid());
										item2.setLink(item.getLink());
										item2.setPubDate(item.getPubDate());
										LOGGER.info("maj du flux:"+item2);
									}
									break;
								}
							}
						}
						if(!trouve){
							LOGGER.info("add du flux:"+item);
							rssPersiste.getListeItem().add(item);
						}
					}
				}
			}

			rss3Repository.save(rssPersiste);
		}
		LOGGER.info("fin maj du flux "+url);
		return rssPersiste;
	}
}
