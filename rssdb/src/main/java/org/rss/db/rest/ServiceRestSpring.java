package org.rss.db.rest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.rss.beans.flux.*;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.*;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.UrlRssRepository;
import org.rss.db.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

/**
 * Created by Alain on 30/01/2016.
 */
@RestController//("/api3")
public class ServiceRestSpring {

	public static final Logger LOGGER = LoggerFactory.getLogger(ServiceRestSpring.class);

	@Autowired
	private UrlRssRepository repo;

	@Autowired
	private IUrlDao dao_url;

	@Autowired
	private Outils outils;

	@Autowired
	private UrlService urlService;

	private String idServeur;

	@PostConstruct
	public void init(){
		idServeur=""+new Random().nextLong();
	}

	@RequestMapping("/api3/test1")
	public String test1(@RequestParam(value="name", defaultValue="World") String name)
	{
		return "Hello "+name;
	}


	@RequestMapping(value = "/api3/add_rss",method = RequestMethod.POST)
	public String add_rss(@RequestBody RssChannel rss){
		Preconditions.checkNotNull(rss);
		Preconditions.checkNotNull(rss.getUrlRss());
		LOGGER.info("Call spring service");
		LOGGER.info("list {}");
		LOGGER.info("rss=" + rss);
		LOGGER.info("enregistrement rss ...");
		dao_url.saveRss(rss.getUrlRss(),outils.conv_feeds(rss));
		LOGGER.info("enregistrement rss Ok");
		return "Je suis un service";
	}


	@RequestMapping("/api3/liste_url")
	public RssListeUrl liste_url()
	{
		RssListeUrl liste;
		LOGGER.info("liste_url debut");

		liste=new RssListeUrl();
		liste.setIdServeur(idServeur);

		List<UrlJpa> liste1;

		liste1=dao_url.getListeUrl();

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

		LOGGER.info("liste_url fin:"+liste);
		return liste;
	}

	@RequestMapping("/api3/add_url")
	public ResponseEntity<String> addUrl(@RequestParam(value="name") String nom,
	                                     @RequestParam(value="url") String url,
	                                     @RequestParam(value="userId") String userId) throws ErrorJpaException {
		UrlJpa rss;
		String res;
		HttpStatus statusCode;

		LOGGER.info("add_url debut");

		LOGGER.info("userId="+userId);

		res="";
		if(isEmpty(nom))
		{
			res="Erreur:parametre name invalide !";
			statusCode= HttpStatus.BAD_REQUEST;
		}
		else if(isEmpty(url))
		{
			res="Erreur:parametre url invalide !";
			statusCode= HttpStatus.BAD_REQUEST;
		}
		else if(isEmpty(userId))
		{
			res="Erreur:parametre userId invalide !";
			statusCode= HttpStatus.BAD_REQUEST;
		}
		else if(nomExiste(nom)){
			res="Erreur: Le nom '"+nom+"' existe déjà !";
			statusCode= HttpStatus.BAD_REQUEST;
		}
		else if(urlExiste(url)){
			res="Erreur: L'URL '"+url+"' existe déjà !";
			statusCode= HttpStatus.BAD_REQUEST;
		}
		else {
			rss=new UrlJpa();
			rss.setNom(nom);
			rss.setUrl(url);

			LOGGER.info("add:"+rss);
			urlService.save(userId,rss);

			res="OK";
			statusCode= HttpStatus.OK;
		}

		LOGGER.info("add_url fin:"+res);

		return new ResponseEntity<>(res, statusCode);

	}

	private boolean urlExiste(String url) {
		return dao_url.isUrlExiste(url);
	}

	private boolean nomExiste(String nom) {
		return dao_url.isNomExiste(nom);
	}

	private boolean isEmpty(String nom) {
		return nom==null||nom.length()==0||nom.trim().length()==0;
	}

	@RequestMapping("/api3/liste_rss")
	public List<RssChannel> liste_rss(@RequestParam(value="userId") String userId)
	{
		List<RssChannel> liste;
		List<FeedsRssJpa> liste_jpa;

		LOGGER.info("userId="+userId);

		liste=Lists.newArrayList();

		if(false) {
			liste_jpa = dao_url.liste_rss();
		} else {
			liste_jpa = dao_url.listeRssUser(userId);
		}

		if(liste_jpa!=null&&!liste_jpa.isEmpty())
		{
			for(FeedsRssJpa tmp:liste_jpa)
			{
				RssChannel channel;
				RssItem item;
				List<RssItem> liste2;

				Preconditions.checkNotNull(tmp);
				Preconditions.checkNotNull(tmp.getId());

				channel=new RssChannel();
				channel.setDescription(tmp.getDescription());
				channel.setLanguage(tmp.getLanguage());
				channel.setTitle(tmp.getTitle());
				channel.setUrl(tmp.getUrl());
				channel.setId(""+tmp.getId());
				channel.setName(tmp.getName());

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
						item.setPubDate(new DateTimeZone(tmp2.getPubDate()));

						liste2.add(item);
					}

					channel.setListeItem(liste2);
				}

				liste.add(channel);
			}
		}

		return liste;
	}


	@RequestMapping("/api3/listeRss")
	public RssChannel[] listeRss(@RequestParam(value="userId") String userId,
	                                  @RequestParam(value="type") String type,
									  @RequestParam(value="id") String id)
	{
		List<RssChannel> liste;
		List<FeedsRssJpa> liste_jpa;

		LOGGER.info("userId="+userId);

		int id2;
		id2=Integer.parseInt(id);

		liste=Lists.newArrayList();

		liste_jpa=null;

		if(!StringUtils.isEmpty(type)){
			if(type.equals("categorie")){
				liste_jpa=dao_url.listeRssCategorie(userId,id2);
			} else if(type.equals("flux")){
				liste_jpa=dao_url.listeRssFlux(userId,id2);
			}
		}

		if(liste_jpa!=null&&!liste_jpa.isEmpty())
		{
			for(FeedsRssJpa tmp:liste_jpa)
			{
				RssChannel channel;
				RssItem item;
				List<RssItem> liste2;

				Preconditions.checkNotNull(tmp);
				Preconditions.checkNotNull(tmp.getId());

				channel=new RssChannel();
				channel.setDescription(tmp.getDescription());
				channel.setLanguage(tmp.getLanguage());
				channel.setTitle(tmp.getTitle());
				channel.setUrl(tmp.getUrl());
				channel.setId(""+tmp.getId());
				channel.setName(tmp.getName());

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
						item.setPubDate(new DateTimeZone(tmp2.getPubDate()));

						liste2.add(item);
					}

					channel.setListeItem(liste2);
				}

				liste.add(channel);
			}
		}

		return liste.toArray(new RssChannel[0]);
	}


	@RequestMapping("/api3/listeCategorie")
	public ListCategories listeCategorie(@RequestParam(value="userId") String userId)
	{
		List<RssChannel> liste;
		List<FeedsRssJpa> liste_jpa;
		ListCategories res;

		LOGGER.info("userId="+userId);


		liste=Lists.newArrayList();

		liste_jpa=null;

		res=dao_url.listeCategorie(userId);

		/*if(StringUtils.isEmpty(type)){
			if(type.equals("categorie")){
				liste_jpa=dao_url.listeRssCategorie(userId,id2);
			} else if(type.equals("flux")){
				liste_jpa=dao_url.listeRssFlux(userId,id2);
			}
		}

		if(liste_jpa!=null&&!liste_jpa.isEmpty())
		{
			for(FeedsRssJpa tmp:liste_jpa)
			{
				RssChannel channel;
				RssItem item;
				List<RssItem> liste2;

				Preconditions.checkNotNull(tmp);
				Preconditions.checkNotNull(tmp.getId());

				channel=new RssChannel();
				channel.setDescription(tmp.getDescription());
				channel.setLanguage(tmp.getLanguage());
				channel.setTitle(tmp.getTitle());
				channel.setUrl(tmp.getUrl());
				channel.setId(""+tmp.getId());
				channel.setName(tmp.getName());

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
						item.setPubDate(new DateTimeZone(tmp2.getPubDate()));

						liste2.add(item);
					}

					channel.setListeItem(liste2);
				}

				liste.add(channel);
			}
		}*/

		return res;
	}

}
