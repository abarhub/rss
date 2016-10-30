package org.rss.ui.service;

import com.google.common.collect.Lists;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.ui.bean.ChannelUi;
import org.rss.ui.bean.ItemUi;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsFirst;
import static org.rss.beans.OutilsGeneriques.vide;

/**
 * Created by Alain on 21/08/2016.
 */
@Service
public class UIService {

	public static final String idTousFlux="-1";

	public void trie(List<ItemUi> listeItem) {
		if(listeItem!=null&&!listeItem.isEmpty()){
			final Comparator<ItemUi> comparator = nullsFirst(comparing(ItemUi::getPubDate2)).reversed();
			listeItem.sort(comparator);
		}
	}

	public ChannelUi getChannelUi(String id, RssChannel[] liste_url) {
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

	public String convDate(DateTimeZone pubDate) {
		if(pubDate==null){
			return "";
		} else {
			//SimpleDateFormat formatter;
			//formatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.FRANCE);
			//return formatter.format(pubDate.getDate());
			return pubDate.format("HH:mm:ss dd/MM/yyyy", Locale.FRANCE);
		}
	}

	public ChannelUi getChannelUiTousFlux(RssChannel[] liste_url) {
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

	public ChannelUi donneListeRss(String id, RssChannel[] liste_url) {
		ChannelUi c=null;

		if(tousLesFlux(id)){
			c = getChannelUiTousFlux(liste_url);
		} else {
			c = getChannelUi(id, liste_url);
		}
		if(c!=null) {
			trie(c.getListeItem());
		}

		return c;
	}
}
