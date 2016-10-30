package org.rss.read.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.read.ResultatRss;
import org.rss.read.domrrs.ChannelRss;
import org.rss.read.nettoyage.SupprimeElementDejaPresent;
import org.rss.registry.IRestDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alain on 25/10/2015.
 */
@Service
public class TacheLecture {

	public static final Logger LOGGER = LoggerFactory.getLogger(TacheLecture.class);

	//private static int nb=0;

	@Autowired
	private ParamUrl param;

	@Autowired
	private ParseRss parse;

	@Autowired
	private ReadHttp read;

	@Autowired
	private IRestDb restDb;

	@Autowired
	private OutilsRead outilsRead;

	@Autowired
	private SupprimeElementDejaPresent supprimeElementDejaPresent;

	//private int nb0;
	//private int nb2;
	//private List<String> url_deja_traite=Lists.newArrayList();
	private Map<String,DateTimeZone> url_deja_traite= Maps.newHashMap();

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

		LOGGER.info("debut lecture flus rss");
		if(param.getListUrl()!=null&&!param.getListUrl().isEmpty()){
			for(RssUrl url:param.getListUrl())
			{
				lecture_url(url);
			}
		}
		LOGGER.info("fin lecture flus rss");
	}

	public void lecture_url(RssUrl url) {
		String res="",url0;
		Preconditions.checkNotNull(url);
		url0=url.getUrl();
		if(url0!=null&&url0.length()>0&&url0.startsWith("http")) {
			LOGGER.info("Lecture RSS de " + url.getNom() + " : " + url0);
			try {
				LOGGER.info("Lecture ...");
				res = read.read(url0);
				LOGGER.info("Lecture Ok");
				LOGGER.info("Parse ...");
				ResultatRss res2 = parse.read(res);
				Preconditions.checkNotNull(res2);
				if (res2.isError()) {
					LOGGER.info("Error:" + res2.getErrors());
				} else {
					LOGGER.info("Parse OK");
					ChannelRss channel = res2.getRes();
					Preconditions.checkNotNull(channel);
					Preconditions.checkNotNull(channel.getLastBuildDate());

					boolean aEnvoyer=false;
					DateTimeZone date=null;
					if(StringUtils.isEmpty(channel.getLastBuildDate())){
						aEnvoyer = true;
					}else {
						date = outilsRead.convDate(channel.getLastBuildDate());
						//Preconditions.checkNotNull(date,"date="+channel.getLastBuildDate());
						if (url_deja_traite.containsKey(url0)) {
							DateTimeZone dateDernierTraitement = url_deja_traite.get(url0);
							if (date==null||dateDernierTraitement.compareTo(date) < 0) {
								LOGGER.info("envoie?(" + dateDernierTraitement + "<" + date + ")");
								channel=supprimeElementDejaPresent.supprime(url0,channel);
								if(!CollectionUtils.isEmpty(channel.getListItem())) {
									aEnvoyer = true;
									LOGGER.info("a envoier ("+channel.getListItem().size()+" elt)");
								} else {
									LOGGER.info("rien a envoier");
								}
							} else {
								LOGGER.info("pas envoie(" + dateDernierTraitement + ">=" + date + ")");
							}
						} else {
							aEnvoyer = true;
						}
					}
					if(aEnvoyer) {
						LOGGER.info("Envoie ...");
						//Preconditions.checkNotNull(date);
						url_deja_traite.put(url0,date);
						envoie_flux(url0,channel);
					}
				}
			} catch (IOException | ExecutionException | InterruptedException e) {
				LOGGER.error("Erreur:" + e.getLocalizedMessage(), e);
			}
		}
	}

	public void envoie_flux(String urlRss, ChannelRss res) {
		RssChannel rss;

		Preconditions.checkNotNull(res);

		LOGGER.info("debut envoie_flux ...");

		rss=outilsRead.convChannel(res);

		Preconditions.checkNotNull(rss);

		rss.setUrlRss(urlRss);

		LOGGER.info("rss="+rss);
		try {
			ResponseEntity<String> tmp = restDb.addRss(rss);
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("fin appel");
				LOGGER.info("res:" + tmp.getBody() + "(" + tmp.getStatusCode() + ")");
				LOGGER.info("envoie_flux OK");
			}
		}catch(Exception e){
			LOGGER.error("Error:"+e.getLocalizedMessage(),e);
		}
		LOGGER.info("fin envoie_flux");
	}


	private void lecture_param() {
		LOGGER.info("lecture param");
		try {
			ResponseEntity<RssListeUrl> res2 = restDb.lectureParametres();
			RssListeUrl res=res2.getBody();
			if(res.getListe_url()!=null)
			{
				param.setListUrl(res.getListe_url());
			}
		}catch(Exception e){
			LOGGER.error("Error:"+e.getLocalizedMessage(),e);
		}
	}

	@Override
	public String toString() {
		return "TacheLecture{}";
	}
}
