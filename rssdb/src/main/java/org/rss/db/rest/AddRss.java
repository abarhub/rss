package org.rss.db.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;
import org.rss.db.dao.repository.RssRepository;
import org.rss.db.dao.repository.UrlRssRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Alain on 25/10/2015.
 */
@RestController
public class AddRss {

	public static final Logger logger = LoggerFactory.getLogger(AddRss.class);

	@Autowired
	UrlRssRepository repo;

	@Autowired
	RssRepository repo2;

	/*@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
				String.format(template, name));
	}*/


	@RequestMapping(value = "/add_rss",method = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET,RequestMethod.PATCH}, consumes="application/json")
	public String add_rss(/*@RequestParam(value="rss")*/ RssChannel rss) {
		//RssListeUrl liste;
		logger.info("list {}");
		logger.info("rss="+rss);

		/*liste=new RssListeUrl();

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
				rss.setNom(u.getNom());
				rss.setUrl(u.getUrl());
				liste2.add(rss);
			}
		}

		return liste;*/
		return "OK";
	}


	@RequestMapping(value = "/add_rss2",method = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET,RequestMethod.PATCH}, consumes="application/json")
	public String add_rss2(RssChannel rss2,@RequestParam(value="rss") String rss0) {
		RssChannel rss;
		//RssListeUrl liste;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;

		//logger.info("liste {}");
		logger.info("rss2="+rss2);
		logger.info("rss0="+rss0);

		BaseEncoding hex = BaseEncoding.base16().upperCase();


		try {
			byte[] buf = hex.decode(rss0);

			jsonInString=new String(buf,"UTF-8");

			rss = mapper.readValue(jsonInString, RssChannel.class);

			logger.info("rss="+rss);
		} catch (IOException e) {
			logger.error("Error:"+e.getLocalizedMessage(),e);
		}


		/*liste=new RssListeUrl();

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
				rss.setNom(u.getNom());
				rss.setUrl(u.getUrl());
				liste2.add(rss);
			}
		}

		return liste;*/
		return "OK";
	}


	@RequestMapping(value = "/add_rss3",method = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET,RequestMethod.PATCH}, consumes="application/json")
	public String add_rss3(@RequestParam(value="rss") String rss0) {
		RssChannel rss;
		//RssListeUrl liste;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		FeedsRssJpa channel;

		//logger.info("liste {}");
		//logger.info("rss2="+rss2);
		logger.info("rss0="+rss0);

		BaseEncoding hex = BaseEncoding.base16().upperCase();


		try {
			byte[] buf = hex.decode(rss0);

			jsonInString=new String(buf,"UTF-8");

			rss = mapper.readValue(jsonInString, RssChannel.class);

			logger.info("rss="+rss);

			if(rss!=null) {
				channel = conv(rss);

				Preconditions.checkNotNull(channel);
				repo2.addChannel(channel);
			}

		} catch (IOException e) {
			logger.error("Error:"+e.getLocalizedMessage(),e);
		}


		/*liste=new RssListeUrl();

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
				rss.setNom(u.getNom());
				rss.setUrl(u.getUrl());
				liste2.add(rss);
			}
		}

		return liste;*/
		return "OK";
	}

	private FeedsRssJpa conv(RssChannel rss) {
		FeedsRssJpa res;

		res=new FeedsRssJpa();

		res.setDescription(rss.getDescription());
		res.setLanguage(rss.getLanguage());
		//res.setLastBuildDate(rss.getLastBuildDate());
		res.setPubDate(res.getPubDate());
		res.setTitle(res.getTitle());
		res.setUrl(res.getTitle());

		if(rss.getListeItem()!=null&&!rss.getListeItem().isEmpty())
		{
			res.setListeItem(Lists.newArrayList());
			for(RssItem item:rss.getListeItem())
			{
				ItemRssJpa item2;
				item2=new ItemRssJpa();
				item2.setDescription(item.getDescription());
				item2.setGuid(item.getGuid());
				item2.setLink(item.getLink());
				//item2.setPubDate(conv(item.getPubDate()));
				item2.setTitle(item.getTitle());
				res.getListeItem().add(item2);
			}
		}

		return res;
	}
}
