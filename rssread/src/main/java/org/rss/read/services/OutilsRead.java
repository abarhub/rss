package org.rss.read.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.read.domrrs.ChannelRss;
import org.rss.read.domrrs.ItemRss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alain on 21/08/2016.
 */
@Service
public class OutilsRead {

	public static final Logger LOGGER = LoggerFactory.getLogger(OutilsRead.class);

	public OutilsRead(){

	}

	public RssChannel convChannel(ChannelRss res) {
		RssChannel rss;

		Preconditions.checkNotNull(res);

		rss=new RssChannel();
		rss.setUrl(res.getLink());
		rss.setDescription(res.getDescription());
		rss.setLanguage(res.getLanguage());
		rss.setLastBuildDate(convDate(res.getLastBuildDate()));
		rss.setPubDate(convDate(res.getPubDate()));
		rss.setTitle(res.getTitle());

		if(res.getListItem()!=null&&!res.getListItem().isEmpty())
		{
			rss.setListeItem(Lists.newArrayList());
			for(ItemRss item:res.getListItem())
			{
				RssItem item2;
				item2=new RssItem();
				item2.setDescription(item.getDescription());
				item2.setGuid(item.getGuid());
				item2.setLink(item.getLink());
				item2.setPubDate(convDate(item.getPubDate()));
				item2.setTitle(item.getTitle());
				rss.getListeItem().add(item2);
			}
		}

		return rss;
	}

	public DateTimeZone convDate(String date) {
		SimpleDateFormat format;
		ZonedDateTime res;
		DateTimeZone res2;

		if(date==null||date.length()==0)
		{
			res=null;
		}
		else {
			ZonedDateTime res0;
			if(false) {
				format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
				try {
					Date d = format.parse(date);
					res0=ZonedDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
				} catch (ParseException e) {
					LOGGER.error(e.getLocalizedMessage(),e);
					res0=null;
				}
			} else {
				res0 = ZonedDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
				res0=ZonedDateTime.of(res0.toLocalDateTime(),ZoneId.of("UTC"));
			}

			res = res0;//.toLocalDateTime();
		}

		if(res==null) {
			res2 = null;
		}
		else
		{
			//res2=new DateTimeZone();
			//Date d;
			//d=Date.from(res.atZone(ZoneId.systemDefault()).toInstant());
			//res2.setDate(d);
			//res2=new DateTimeZone(d);
			res2=new DateTimeZone(res);
		}

		return res2;
	}

}
