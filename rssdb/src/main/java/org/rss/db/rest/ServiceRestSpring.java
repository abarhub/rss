package org.rss.db.rest;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.*;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.UrlRssRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Alain on 30/01/2016.
 */
@RestController//("/api3")
public class ServiceRestSpring {

	public static final Logger logger = LoggerFactory.getLogger(ServiceRestSpring.class);

	@Autowired
	private UrlRssRepository repo;

	/*@Autowired
	private UrlRepository repository;*/
	@Autowired
	private IDAOUrl dao_url;

	@RequestMapping("/api3/test1")
	public String test1(@RequestParam(value="name", defaultValue="World") String name)
	{
		return "Hello "+name;
	}


	@RequestMapping(value = "/api3/add_rss",method = RequestMethod.POST)
	public String add_rss(@RequestBody RssChannel rss)
	{
		logger.info("Call spring service");
		logger.info("list {}");
		logger.info("rss=" + rss);
		logger.info("enregistrement rss ...");
		dao_url.enregistre_rss(Outils.conv_feeds(rss));
		logger.info("enregistrement rss Ok");
		return "Je suis un service";
	}


	@RequestMapping("/api3/liste_url")
	public RssListeUrl liste_url()
	{
		RssListeUrl liste;
		logger.info("liste_url debut");

		liste=new RssListeUrl();

		List<UrlJpa> liste1;

		if(false) {
			liste1 = repo.getListe();
		}
		else
		{
			liste1=dao_url.getListeUrl();
		}

		if(liste1!=null&&!liste1.isEmpty())
		{
			List<RssUrl> liste2;
			liste2= Lists.newArrayList();
			liste.setListe_url(liste2);
			for(UrlJpa u:liste1)
			{
				RssUrl rss;
				rss=new RssUrl();
				rss.setNom(u.getNom());
				rss.setUrl(u.getUrl());
				liste2.add(rss);
			}
		}

		logger.info("liste_url fin:"+liste);
		return liste;
	}

	@RequestMapping("/api3/add_url")
	public String add_url(@RequestParam(value="name") String nom,
	                      @RequestParam(value="url") String url)
	{
		UrlJpa rss;
		//List<RssUrl> liste;
		String res;

		logger.info("add_url debut");

		res="";
		if(isEmpty(nom))
		{
			res="Erreur:parametre name invalide !";
		}
		else if(isEmpty(url))
		{
			res="Erreur:parametre url invalide !";
		}
		else {
			//liste = Lists.newArrayList();
			rss=new UrlJpa();
			rss.setNom(nom);
			rss.setUrl(url);

			logger.info("add:"+rss);
			if(false) {
				repo.addUrl(rss);
			}
			else
			{
				dao_url.save(rss);
			}
			//liste.add(rss);
			res="OK";
			//logger.info("add ok:"+rss.getId());
		}

		logger.info("add_url fin:"+res);

		return res;

	}

	private boolean isEmpty(/*@RequestParam(value = "name")*/ String nom) {
		return nom==null||nom.length()==0||nom.trim().length()==0;
	}

	@RequestMapping("/api3/liste_rss")
	public List<RssChannel> liste_rss()
	{
		List<RssChannel> liste;
		List<FeedsRssJpa> liste_jpa;

		liste=Lists.newArrayList();

		liste_jpa=dao_url.liste_rss();

		if(liste_jpa!=null&&!liste_jpa.isEmpty())
		{
			for(FeedsRssJpa tmp:liste_jpa)
			{
				RssChannel channel;
				RssItem item;
				List<RssItem> liste2;

				Preconditions.checkNotNull(tmp.getId());

				channel=new RssChannel();
				channel.setDescription(tmp.getDescription());
				channel.setLanguage(tmp.getLanguage());
				channel.setTitle(tmp.getTitle());
				channel.setUrl(tmp.getUrl());
				channel.setId(""+tmp.getId());

				if(tmp.getListeItem()!=null&&!tmp.getListeItem().isEmpty())
				{
					liste2=Lists.newArrayList();

					for(ItemRssJpa tmp2:tmp.getListeItem())
					{
						item=new RssItem();
						item.setDescription(tmp2.getDescription());
						item.setGuid(tmp2.getGuid());
						item.setLink(tmp2.getLink());
						item.setTitle(tmp2.getTitle());

						liste2.add(item);
					}

					channel.setListeItem(liste2);
				}

				liste.add(channel);
			}
		}

		return liste;
	}
}
