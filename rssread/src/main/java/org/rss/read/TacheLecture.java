package org.rss.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.BaseEncoding;
import com.google.common.io.CharStreams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.read.domrrs.ChannelRss;
import org.rss.read.domrrs.ItemRss;
import org.rss.registry.IRestDb;
import org.rss.registry.impl.RestDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alain on 25/10/2015.
 */
@Service
public class TacheLecture {

	public static final Logger logger = LoggerFactory.getLogger(TacheLecture.class);

	//private static int nb=0;

	@Autowired
	private ParamUrl param;

	@Autowired
	private ParseRss parse;

	@Autowired
	private ReadHttp read;

	@Autowired
	private IRestDb restDb;

	//private int nb0;
	//private int nb2;
	private List<String> url_deja_traite=Lists.newArrayList();

	public TacheLecture() {
		//nb++;
		//nb0=nb;
		//nb2=0;
	}

	private void init() {
		//if(true)
		{
			List<RssUrl> liste;
			RssUrl tmp;
			liste= Lists.newArrayList();
			tmp=new RssUrl();
			tmp.setNom("AAA");
			tmp.setUrl("http://static.userland.com/gems/backend/rssTwoExample2.xml");
			liste.add(tmp);
			param.setListUrl(liste);
		}
	}

	public void lecture_parametres()
	{
		if(param.getListUrl().isEmpty()) {
			init();
			lecture_param();
		}
		else
		{
			lecture_param();
			lecture_rss2();
		}
	}

	public void lecture_rss2() {

		boolean dejaTraite;
		logger.info("debut lecture flus rss");
		if(param.getListUrl()!=null&&!param.getListUrl().isEmpty()){
			for(RssUrl url:param.getListUrl())
			{
				final String url2;
				url2=url.getUrl();
				dejaTraite=url_deja_traite.stream().anyMatch(s -> s.equals(url2));
				if(!dejaTraite) {
					lecture_url(url);
					url_deja_traite.add(url2);
				}
			}
		}
		logger.info("fin lecture flus rss");
	}

	/*public void lecture_rss() {
		logger.info("debut lecture flus rss");
		if(param.getListUrl()!=null&&!param.getListUrl().isEmpty()){
			for(RssUrl url:param.getListUrl())
			{
				lecture_url(url);
			}
		}
		logger.info("fin lecture flus rss");
	}*/

	private void lecture_url(RssUrl url) {
		String res="",url0;
		Preconditions.checkNotNull(url);
		url0=url.getUrl();
		if(url0!=null&&url0.length()>0&&url0.startsWith("http")) {
			logger.info("Lecture RSS de " + url.getNom() + " : " + url0);
			try {
				logger.info("Lecture ...");
				res = read.read(url0);
				logger.info("Lecture Ok");
				logger.info("Parse ...");
				ResultatRss res2 = parse.read(res);
				Preconditions.checkNotNull(res2);
				if (res2.isError()) {
					logger.info("Error:" + res2.getErrors());
				} else {
					Preconditions.checkNotNull(res2.getRes());
					logger.info("Parse OK");
					envoie_flux02(res2.getRes());
				}
			} catch (IOException | ExecutionException | InterruptedException e) {
				logger.error("Erreur:" + e.getLocalizedMessage(), e);
			}
		}
	}

	private void envoie_flux02(ChannelRss res) {
		RssChannel rss;

		Preconditions.checkNotNull(res);

		logger.info("debut envoie_flux02 ...");

		rss=conv(res);

		Preconditions.checkNotNull(rss);

		logger.info("rss="+rss);
		try {
			ResponseEntity<String> tmp = restDb.addRss(rss);
			logger.info("fin appel");
			logger.info("res:"+tmp.getBody()+"("+tmp.getStatusCode()+")");
			logger.info("envoie_flux OK");
		}catch(Exception e){
			logger.error("Error:"+e.getLocalizedMessage(),e);
		}
		logger.info("fin envoie_flux");
	}

	private RssChannel conv(ChannelRss res) {
		RssChannel rss;

		Preconditions.checkNotNull(res);

		rss=new RssChannel();
		rss.setUrl(res.getLink());
		rss.setDescription(res.getDescription());
		rss.setLanguage(res.getLanguage());
		rss.setLastBuildDate(conv_date(res.getLastBuildDate()));
		rss.setPubDate(conv_date(res.getPubDate()));
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
				item2.setPubDate(conv_date(item.getPubDate()));
				item2.setTitle(item.getTitle());
				rss.getListeItem().add(item2);
			}
		}

		return rss;
	}

	private DateTimeZone conv_date(String date) {
		SimpleDateFormat format;
		LocalDateTime res=null;
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
					res0=ZonedDateTime.ofInstant(d.toInstant(),ZoneId.systemDefault());
				} catch (ParseException e) {
					logger.error(e.getLocalizedMessage(),e);
					res0=null;
				}
			} else {
				res0 = ZonedDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
			}

			res = res0.toLocalDateTime();
		}

		if(res==null) {
			res2 = null;
		}
		else
		{
			res2=new DateTimeZone();
			Date d;
			d=Date.from(res.atZone(ZoneId.systemDefault()).toInstant());
			res2.setDate(d);
		}

		return res2;
	}


	private void lecture_param() {
		logger.info("lecture param");
		try {
			ResponseEntity<RssListeUrl> res2 = restDb.lectureParametres();
			RssListeUrl res=res2.getBody();
			if(res.getListe_url()!=null)
			{
				param.setListUrl(res.getListe_url());
			}
		}catch(Exception e){
			logger.error("Error:"+e.getLocalizedMessage(),e);
		}
	}

	@Override
	public String toString() {
		return "TacheLecture{}";
	}
}
