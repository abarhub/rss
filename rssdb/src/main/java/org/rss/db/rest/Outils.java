package org.rss.db.rest;

import com.google.common.collect.Lists;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.ItemRssJpa;

import java.util.Date;
import java.util.List;

/**
 * Created by Alain on 13/02/2016.
 */
public final class Outils {


	public static FeedsRssJpa conv_feeds(RssChannel rss) {
		FeedsRssJpa feeds;

		feeds=new FeedsRssJpa();
		feeds.setUrl(rss.getUrl());
		feeds.setTitle(rss.getTitle());
		feeds.setDescription(rss.getDescription());
		feeds.setPubDate(conv(rss.getPubDate()));
		feeds.setLastBuildDate(conv(rss.getLastBuildDate()));
		feeds.setLanguage(rss.getLanguage());

		if(rss.getListeItem()!=null&&!rss.getListeItem().isEmpty())
		{
			List<ItemRssJpa> liste;
			ItemRssJpa item;

			liste= Lists.newArrayList();
			for(RssItem tmp2:rss.getListeItem())
			{
				item=new ItemRssJpa();
				item.setTitle(tmp2.getTitle());
				item.setDescription(tmp2.getDescription());
				item.setGuid(tmp2.getGuid());
				item.setLink(tmp2.getLink());
				item.setPubDate(conv(tmp2.getPubDate()));

				liste.add(item);
			}

			feeds.setListeItem(liste);
		}

		return feeds;
	}

	public static Date conv(DateTimeZone d)
	{
		DateTimeZone tmp = d;
		if(tmp!=null) {
			return tmp.getDate();
		}
		else
		{
			return null;
		}
	}
}
