package org.rss.db.rest;

import com.google.common.collect.Lists;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.UrlRssRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Alain on 25/10/2015.
 */
@RestController
public class ParamAppli {

	public static final Logger logger = LoggerFactory.getLogger(ParamAppli.class);

	@Autowired
	UrlRssRepository repo;

	/*@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
				String.format(template, name));
	}*/

	@RequestMapping("/ajoute_url")
	public String ajoute_url(@RequestParam(value="name", defaultValue="World") String name) {
		logger.info("ajout {}",name);
		return "Ok";
	}


	@RequestMapping("/ajoute_url2")
	public String ajoute_url2(@RequestParam(value="name", defaultValue="World") RssUrl name) {
		return "Ok";
	}


	@RequestMapping("/ajoute_url3")
	public String ajoute_url3(@RequestParam(value="name", defaultValue="World") String name,
	                          @RequestParam(value="url", defaultValue="http://www.google.fr") String url0) {
		logger.info("ajout3 {}",name);

		UrlJpa url;
		url=new UrlJpa();
		//url.setNom(name);
		//url.setUrl(url0);
		repo.addUrl(url);
		logger.info("ajout3 {} ok",name);
		return "Ok";
	}

	@RequestMapping(value = "/liste_url",method = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET,RequestMethod.PATCH}, consumes="application/json")
	public RssListeUrl liste_url(@RequestParam(value="name", defaultValue="World") String name) {
		RssListeUrl liste;
		logger.info("liste {}");

		liste=new RssListeUrl();

		List<UrlJpa> liste1 = repo.getListe();

		if(liste1!=null&&!liste1.isEmpty())
		{
			List<RssUrl> liste2;
			liste2= Lists.newArrayList();
			liste.setListe_url(liste2);
			for(UrlJpa u:liste1)
			{
				RssUrl rss;
				rss=new RssUrl();
				//rss.setNom(u.getNom());
				//rss.setUrl(u.getUrl());
				liste2.add(rss);
			}
		}

		return liste;
	}
}
