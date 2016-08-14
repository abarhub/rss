package org.rss.ui.rest;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.registry.IRestDb;
import org.rss.registry.impl.RestDb;
import org.rss.ui.bean.ChannelUi;
import org.rss.ui.bean.ItemUi;
import org.rss.ui.bean.ListChannelUi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.rss.beans.OutilsGeneriques.vide;


/**
 * Created by Alain on 24/10/2015.
 */
@RestController
public class ListeController {

	public static final Logger logger = LoggerFactory.getLogger(ListeController.class);
	public static final Marker markerTrace = MarkerFactory.getMarker("TRACE");

	//private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();

	@Autowired
	private IRestDb restDb;//=new RestDb();

	@RequestMapping("/liste")
	public ListChannelUi greeting() {

		return getListe();
	}

	private ListChannelUi getListe() {
		ListChannelUi res;
		ChannelUi c;
		ItemUi item;

		if(false) {
			res = new ListChannelUi();

			res.setListe_channel(Lists.newArrayList());

			for (int i = 0; i < 10; i++) {
				c = new ChannelUi();
				c.setDescription("AAAA" + i);
				c.setLanguage("en");
				c.setLastBuildDate("2001-01-01");
				c.setPubDate("2001-01-02");
				c.setTitle("BBBBBB" + i);
				c.setUrl("http://www.google.fr/" + i);

				c.setListeItem(Lists.newArrayList());

				for (int j = 0; j < 3; j++) {
					item = new ItemUi();
					item.setDescription("GGGG" + i + "-" + j);
					item.setGuid("HHHH" + i + "-" + j);
					item.setLink("http://www.yahoo.fr/" + j);
					item.setPubDate("2001-01-03");
					item.setTitle("KKKKKK" + i + "-" + j);

					c.getListeItem().add(item);
				}

				res.getListe_channel().add(c);
			}
		}
		else
		{
			//RestTemplate restTemplate = new RestTemplate();

			res = new ListChannelUi();

			//String url;
			RssChannel[] liste_url;
			List<ChannelUi> liste_channel;

			liste_channel=Lists.newArrayList();
			res.setListe_channel(liste_channel);

			/*url="http://localhost:8083/api3/liste_rss";

			ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class, Maps.newHashMap());

			liste_url=tmp.getBody();*/
			ResponseEntity<RssChannel[]> tmp=restDb.listeRssDetaille();

			liste_url=tmp.getBody();

			if(liste_url!=null&&liste_url.length>0)
			{

				for(RssChannel tmp2:liste_url)
				{
					Preconditions.checkNotNull(tmp2.getId());
					Preconditions.checkState(!tmp2.getId().trim().isEmpty());

					c = new ChannelUi();
					c.setDescription(tmp2.getDescription());
					c.setLanguage(tmp2.getLanguage());
					//c.setLastBuildDate(tmp2.);
					c.setTitle(tmp2.getTitle());
					c.setUrl(tmp2.getUrl());
					c.setId(tmp2.getId());

					if(tmp2.getListeItem()!=null&&!tmp2.getListeItem().isEmpty())
					{
						c.setListeItem(Lists.newArrayList());

						for(RssItem item3:tmp2.getListeItem())
						{
							ItemUi item2;

							item2=new ItemUi();
							item2.setDescription(item3.getDescription());
							item2.setGuid(item3.getGuid());
							item2.setLink(item3.getLink());
							item2.setTitle(item3.getTitle());

							c.getListeItem().add(item2);
						}
					}

					liste_channel.add(c);
				}
			}
		}

		return res;
	}


	@RequestMapping("/add_url")
	public String addUrl(@RequestParam(value="name") String nom,
	                     @RequestParam(value="url") String url) {
		String res="",url2;
		//RestTemplate restTemplate = new RestTemplate();
		//Map param;

		logger.info("ajout url ...");
		/*url2="http://localhost:8083/api3/add_url?name="+nom+"&url="+url;
		//url2="http://localhost:8083/api3/add_url";

		param=Maps.newHashMap();
		//param.put("name",nom);
		//param.put("url",url);
		ResponseEntity<String> tmp = restTemplate.postForEntity(url2, null,String.class, param);*/
		ResponseEntity<String> tmp =restDb.add_url(nom,url);

		logger.info("fin url : "+tmp.getBody());

		return res;
	}


	@RequestMapping("/listeUrl")
	public ListChannelUi listeUrl() {

		ListChannelUi res;
		ChannelUi c;
		ItemUi item;

				//RestTemplate restTemplate = new RestTemplate();

			res = new ListChannelUi();

			//String url;
			RssChannel[] liste_url;
			List<ChannelUi> liste_channel;

			liste_channel=Lists.newArrayList();
			res.setListe_channel(liste_channel);

			ResponseEntity<RssChannel[]> tmp=restDb.listeRssDetaille();

			liste_url=tmp.getBody();

			if(liste_url!=null&&liste_url.length>0)
			{
				for(RssChannel tmp2:liste_url)
				{
					Preconditions.checkNotNull(tmp2.getId());
					Preconditions.checkState(!tmp2.getId().trim().isEmpty());

					c = new ChannelUi();
					c.setDescription(tmp2.getDescription());
					c.setLanguage(tmp2.getLanguage());
					//c.setLastBuildDate(tmp2.);
					c.setTitle(tmp2.getTitle());
					c.setUrl(tmp2.getUrl());
					c.setId(tmp2.getId());
					c.setName(tmp2.getName());

					liste_channel.add(c);
				}
			}

		return res;
	}


	@RequestMapping("/listeMessages")
	public ChannelUi listeRss(@RequestParam(value="id",defaultValue = "",required = false) String id) {

		ListChannelUi res;
		ChannelUi c=null;
		ItemUi item;
		int id0;

		//RestTemplate restTemplate = new RestTemplate();

		res = new ListChannelUi();

		//String url;
		RssChannel[] liste_url;
		List<ChannelUi> liste_channel;

		liste_channel=Lists.newArrayList();
		res.setListe_channel(liste_channel);

			/*url="http://localhost:8083/api3/liste_rss";

			ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class, Maps.newHashMap());

			liste_url=tmp.getBody();*/
		ResponseEntity<RssChannel[]> tmp=restDb.listeRssDetaille();

		liste_url=tmp.getBody();

		if(liste_url!=null&&liste_url.length>0)
		{
			/*try {
				id0 = Integer.parseInt(id);
				id0=id0-1;
				if(id0<=0)
					id0=0;
			}catch(NumberFormatException e)
			{
				id0=0;
			}
			RssChannel tmp2=liste_url[id0];*/
			RssChannel tmp2=trouveFlux(liste_url,id);
			if(tmp2!=null)
			{
				c = new ChannelUi();
				c.setDescription(tmp2.getDescription());
				c.setLanguage(tmp2.getLanguage());
				//c.setLastBuildDate(tmp2.);
				c.setTitle(tmp2.getTitle());
				c.setUrl(tmp2.getUrl());

				if(tmp2.getListeItem()!=null&&!tmp2.getListeItem().isEmpty())
				{
					c.setListeItem(Lists.newArrayList());

					for(RssItem item3:tmp2.getListeItem())
					{
						ItemUi item2;

						item2=new ItemUi();
						item2.setDescription(item3.getDescription());
						item2.setGuid(item3.getGuid());
						item2.setLink(item3.getLink());
						item2.setTitle(item3.getTitle());

						c.getListeItem().add(item2);
					}
				}

				//liste_channel.add(c);
			}
		}

		return c;
	}

	private RssChannel trouveFlux(RssChannel[] liste_url, String id) {
		if(vide(id))
		{
			return null;
		}
		else
		{
			Optional<RssChannel> opt = Arrays.stream(liste_url)
					.filter(x -> !vide(x.getId()) && x.getId().equals(id))
					.findAny();
			if(opt.isPresent())
			{
				return opt.get();
			}
			else
			{
				return null;
			}
		}
	}

	@RequestMapping(value = "/traces",method = RequestMethod.POST)
	public void traceMessages(@RequestParam(value="niveauErreur",defaultValue = "",required = false) String niveauErreur,
	                          @RequestParam(value="composant",defaultValue = "",required = false) String composant,
	                          @RequestParam(value="message",defaultValue = "",required = false) String message) {
		if(niveauErreur!=null){
			if(niveauErreur.equals("Info")) {
				logger.info(markerTrace, composant + " : " + message);
			} else if(niveauErreur.equals("Erreur")) {
				logger.error(markerTrace, composant + " : " + message);
			} else {
				logger.info(markerTrace, niveauErreur + " ; " + composant + " : " + message);
			}
		} else {
			logger.info(markerTrace, niveauErreur + " ; " + composant + " : " + message);
		}
	}

}
