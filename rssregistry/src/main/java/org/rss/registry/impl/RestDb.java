package org.rss.registry.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.param.RssListeUrl;
import org.rss.registry.IRestDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alain on 08/05/2016.
 */
@Service
public class RestDb implements IRestDb {

	private static final Logger logger = LoggerFactory.getLogger(RestDb.class);

	private final String urlDbServeur="http://localhost:8083/";

	@Override
	public ResponseEntity<RssChannel[]> listeRssDetaille(){
		String url;
		url=urlDbServeur+"api3/liste_rss";

		RestTemplate restTemplate = getRestTemplate("listeRssDetaille");

		ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class, Maps.newHashMap());

		return tmp;
	}

	private RestTemplate getRestTemplate(String url) {
		Preconditions.checkNotNull(url);
		Preconditions.checkState(!url.isEmpty());
		Preconditions.checkState(!url.trim().isEmpty());

		RestTemplate restTemplate=new RestTemplate();

		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor("metier",url);
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		return restTemplate;
	}

	@Override
	public ResponseEntity<String> add_url(String nom, String url){
		String url2;
		RestTemplate restTemplate = getRestTemplate("add_url");
		url2=urlDbServeur+"api3/add_url?name="+nom+"&url="+url;

		Map<String, Object> param = Maps.newHashMap();
		ResponseEntity<String> tmp = restTemplate.postForEntity(url2, null,String.class, param);

		return tmp;
	}


	@Override
	public ResponseEntity<String> addRss(RssChannel rss){

		String url;
		RestTemplate restTemplate = getRestTemplate("addRss");
		url=urlDbServeur+"api3/add_rss";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RssChannel> entity = new HttpEntity<RssChannel>(rss, headers);

		ResponseEntity<String> tmp = restTemplate.postForEntity(url, entity, String.class);

		return tmp;
	}


	@Override
	public ResponseEntity<RssListeUrl> lectureParametres(){
		String url;
		RestTemplate restTemplate = getRestTemplate("lectureParametres");

		url=urlDbServeur+"api3/liste_url";

		//logger.info("url param:"+url);

		ResponseEntity<RssListeUrl> res = restTemplate.getForEntity(url, RssListeUrl.class);

		return res;
	}

}
