package org.rss.ui.rest;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;


import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsFirst;
import static org.rss.beans.OutilsGeneriques.vide;


/**
 * Created by Alain on 24/10/2015.
 */
@RestController
public class ListeController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ListeController.class);
	public static final Marker markerTrace = MarkerFactory.getMarker("TRACE");

	public static final String idTousFlux="-1";

	@Autowired
	private IRestDb restDb;


	@RequestMapping("/add_url")
	public String addUrl(@RequestParam(value="name") String nom,
	                     @RequestParam(value="url") String url) {
		String res="";

		LOGGER.info("ajout url ...");

		ResponseEntity<String> tmp =restDb.add_url(nom,url);

		LOGGER.info("fin url : "+tmp.getBody());

		return res;
	}


	@RequestMapping("/listeUrl")
	public ListChannelUi listeUrl() {

		ListChannelUi res;
		ChannelUi c;
		ItemUi item;

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
				if(!liste_channel.isEmpty()){
					String titre="Tous les flux";
					c = new ChannelUi();
					c.setDescription(titre);
					c.setLanguage("");
					//c.setLastBuildDate(tmp2.);
					c.setTitle(titre);
					c.setUrl("");
					c.setId(idTousFlux);
					c.setName(titre);

					liste_channel.add(c);
				}
			}

		return res;
	}

	@RequestMapping("/listeMessages")
	public ChannelUi listeRss(@RequestParam(value="id",defaultValue = "",required = false) String id) {

		ListChannelUi res;
		ChannelUi c=null;

		res = new ListChannelUi();

		RssChannel[] liste_url;
		List<ChannelUi> liste_channel;

		liste_channel=Lists.newArrayList();
		res.setListe_channel(liste_channel);

		ResponseEntity<RssChannel[]> tmp=restDb.listeRssDetaille();

		liste_url=tmp.getBody();

		if(liste_url!=null&&liste_url.length>0)
		{
			if(tousLesFlux(id)){
				c = getChannelUiTousFlux(liste_url);
			} else {
				c = getChannelUi(id, liste_url);
			}
			if(c!=null) {
				trie(c.getListeItem());
			}
		}

		return c;
	}

	private void trie(List<ItemUi> listeItem) {
		if(listeItem!=null&&!listeItem.isEmpty()){
			final Comparator<ItemUi> comparator = nullsFirst(comparing(ItemUi::getPubDate2)).reversed();
			listeItem.sort(comparator);
		}
	}

	private ChannelUi getChannelUi(String id, RssChannel[] liste_url) {
		ChannelUi c=null;
		RssChannel tmp2 = trouveFlux(liste_url, id);
		if (tmp2 != null) {
			c = new ChannelUi();
			c.setDescription(tmp2.getDescription());
			c.setLanguage(tmp2.getLanguage());
			//c.setLastBuildDate(tmp2.);
			c.setTitle(tmp2.getTitle());
			c.setUrl(tmp2.getUrl());

			if (tmp2.getListeItem() != null && !tmp2.getListeItem().isEmpty()) {
				c.setListeItem(Lists.newArrayList());

				for (RssItem item3 : tmp2.getListeItem()) {
					ItemUi item2;

					item2 = new ItemUi();
					item2.setDescription(item3.getDescription());
					item2.setGuid(item3.getGuid());
					String link = "";
					if (!OutilsGeneriques.vide(item3.getLink())) {
						link = item3.getLink();
					} else if (!OutilsGeneriques.vide(item3.getGuid())
							&& item3.getGuid().startsWith("http")) {
						link = item3.getGuid();
					}
					item2.setLink(link);
					item2.setTitle(item3.getTitle());
					item2.setPubDate(convDate(item3.getPubDate()));
					item2.setPubDate2(item3.getPubDate());

					c.getListeItem().add(item2);
				}
			}
		}
		return c;
	}

	private String convDate(DateTimeZone pubDate) {
		if(pubDate==null){
			return "";
		} else {
			//SimpleDateFormat formatter;
			//formatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.FRANCE);
			//return formatter.format(pubDate.getDate());
			return pubDate.format("hh:mm:ss dd/MM/yyyy");
		}
	}

	private ChannelUi getChannelUiTousFlux(RssChannel[] liste_url) {
		ChannelUi c=null;
		if(liste_url!=null&&liste_url.length>0) {

			for(RssChannel channel:liste_url){

				if (channel.getListeItem() != null && !channel.getListeItem().isEmpty()) {
					if(c==null) {
						c = new ChannelUi();
						c.setDescription("Tous Les Flux");
						c.setLanguage("fr");
						//c.setLastBuildDate(tmp2.);
						c.setTitle("Tous Les Flux");
						c.setUrl("");

						c.setListeItem(Lists.newArrayList());
					}

					for (RssItem item3 : channel.getListeItem()) {
						ItemUi item2;

						item2 = new ItemUi();
						item2.setDescription(item3.getDescription());
						item2.setGuid(item3.getGuid());
						String link = "";
						if (!OutilsGeneriques.vide(item3.getLink())) {
							link = item3.getLink();
						} else if (!OutilsGeneriques.vide(item3.getGuid())
								&& item3.getGuid().startsWith("http")) {
							link = item3.getGuid();
						}
						item2.setLink(link);
						item2.setTitle(item3.getTitle());
						item2.setPubDate(convDate(item3.getPubDate()));
						item2.setPubDate2(item3.getPubDate());

						c.getListeItem().add(item2);
					}
				}
			}
		}
		return c;
	}

	private RssChannel trouveFlux(RssChannel[] liste_url, String id) {
		if(vide(id)){
			return null;
		} else {
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

	private boolean tousLesFlux(String id){
		return !vide(id)&&id.equals(idTousFlux);
	}

	@RequestMapping(value = "/traces",method = RequestMethod.POST)
	public void traceMessages(@RequestParam(value="niveauErreur",defaultValue = "",required = false) String niveauErreur,
	                          @RequestParam(value="composant",defaultValue = "",required = false) String composant,
	                          @RequestParam(value="message",defaultValue = "",required = false) String message) {
		if(niveauErreur!=null){
			if(niveauErreur.equals("Info")) {
				LOGGER.info(markerTrace, composant + " : " + message);
			} else if(niveauErreur.equals("Erreur")) {
				LOGGER.error(markerTrace, composant + " : " + message);
			} else {
				LOGGER.info(markerTrace, niveauErreur + " ; " + composant + " : " + message);
			}
		} else {
			LOGGER.info(markerTrace, niveauErreur + " ; " + composant + " : " + message);
		}
	}

}
