package org.rss.registry.impl;

import com.google.common.collect.Maps;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.param.RssListeUrl;
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
public class RestDb implements org.rss.registry.IRestDb {

	private final String urlDbServeur="http://localhost:8083/";

	@Override
	public ResponseEntity<RssChannel[]> listeRssDetaille(){
		String url;
		url=urlDbServeur+"api3/liste_rss";

		RestTemplate restTemplate = getRestTemplate();

		ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class, Maps.newHashMap());

		return tmp;
	}

	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate=new RestTemplate();

		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		return restTemplate;
	}

	@Override
	public ResponseEntity<String> add_url(String nom, String url){
		String url2;
		RestTemplate restTemplate = getRestTemplate();
		url2=urlDbServeur+"api3/add_url?name="+nom+"&url="+url;
		//url2="http://localhost:8083/api3/add_url";

		Map<String, Object> param = Maps.newHashMap();
		//param.put("name",nom);
		//param.put("url",url);
		ResponseEntity<String> tmp = restTemplate.postForEntity(url2, null,String.class, param);

		return tmp;
	}


	@Override
	public ResponseEntity<String> addRss(RssChannel rss){

		String url;
		RestTemplate restTemplate = getRestTemplate();
		url=urlDbServeur+"api3/add_rss";
		//url=getUrlDao(UrlDao.AddRss);
		//url="http://192.168.1.11:8080/rss.db-0.0.1-SNAPSHOT/api/add_rss";
		//url="http://192.168.1.11:8080/db/api/add_rss";
		//restTemplate.s

			/*MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("name", "xx");
			map.add("password", "xx");*/

		//HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		//HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
		//restTemplate.setMessageConverters(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew});
		//restTemplate.setMessageConverters(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RssChannel> entity = new HttpEntity<RssChannel>(rss, headers);

		/*ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);*/
		//rt.setInterceptors(ris);
		//rt.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		//restTemplate.setInterceptors(ris);
		//restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		//logger.info("appel : "+url);
		//restTemplate.put(url, rss);
		//ResponseEntity<String> tmp = restTemplate.postForEntity(url, rss, String.class);
		ResponseEntity<String> tmp = restTemplate.postForEntity(url, entity, String.class);

		return tmp;
	}


	@Override
	public ResponseEntity<RssListeUrl> lectureParametres(){
		String url;
		RestTemplate restTemplate = getRestTemplate();

		//url=getUrlDao(UrlDao.ListParam);
		url=urlDbServeur+"api3/liste_url";

		//logger.info("url param:"+url);

		ResponseEntity<RssListeUrl> res = restTemplate.getForEntity(url, RssListeUrl.class);

		return res;
	}

}
