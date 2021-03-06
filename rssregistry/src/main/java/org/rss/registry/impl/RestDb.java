package org.rss.registry.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.rss.beans.flux.Categorie;
import org.rss.beans.flux.ListCategories;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.metier.*;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.security.UserInfoDTO;
import org.rss.registry.IRestDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alain on 08/05/2016.
 */
@Service
public class RestDb implements IRestDb {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestDb.class);

	private final String urlDbServeur="http://localhost:8083/db/";

	@Override
	public ResponseEntity<RssChannel[]> listeRssDetaille(String idUser) throws UnsupportedEncodingException {
		//String url;
		//url=urlDbServeur+"api3/liste_rss?userId="+encodeParam(idUser);
		URI url=getUri(urlDbServeur+"api3/liste_rss?userId={userId}",idUser);

		RestTemplate restTemplate = getRestTemplate("listeRssDetaille");

		ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class);

		return tmp;
	}

	private URI getUri(String url, String... param){
		UriComponents uriComponents =
				UriComponentsBuilder.fromUriString(url).build()
						.expand((Object[])param)
						.encode();
		return uriComponents.toUri();
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
	public ResponseEntity<String> add_url(String idUser,String nom, String url) throws UnsupportedEncodingException {
		//String url2;
		RestTemplate restTemplate = getRestTemplate("add_url");
		//url2=urlDbServeur+"api3/add_url?name="+nom+"&url="+url;
		//url2=urlDbServeur+"api3/add_url";
		//url2=urlDbServeur+"api3/add_url?name="+encodeParam(nom)+"&url="+encodeParam(url)+"&userId="+encodeParam(idUser);
		URI url2;
		url2=getUri(urlDbServeur+"api3/add_url?name={nom}&url={url}&userId={userId}",nom,url,idUser);

		Map<String, Object> param = Maps.newHashMap();
		/*param.put("userId",idUser);
		param.put("name",nom);
		param.put("url",url);*/
		ResponseEntity<String> tmp = restTemplate.postForEntity(url2, null,String.class);

		return tmp;
	}

	private String encodeParam(String nom) throws UnsupportedEncodingException {
		return UriUtils.encodeQueryParam(nom,"UTF-8");
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

		//LOGGER.info("url param:"+url);

		ResponseEntity<RssListeUrl> res = restTemplate.getForEntity(url, RssListeUrl.class);

		return res;
	}

	@Override
	public ResponseEntity<LoginResponseDTO> connecteUser(String login,String password){
		String url;
		RestTemplate restTemplate = getRestTemplate("connecteUser");

		url=urlDbServeur+"api3/user_exists";

		LoginDTO loginDTO;
		loginDTO=new LoginDTO();
		loginDTO.setLogin(login);
		loginDTO.setPassword(password);

		ResponseEntity<LoginResponseDTO> res = restTemplate.postForEntity(url,loginDTO,LoginResponseDTO.class);

		return res;
	}

	@Override
	public ResponseEntity<SearchUsersResponseDTO> searchUser(String nom){
		String url;
		RestTemplate restTemplate = getRestTemplate("searchUser");

		url=urlDbServeur+"api3/users_search";

		SearchUsersDTO searchUsersDTO;
		searchUsersDTO=new SearchUsersDTO();
		searchUsersDTO.setNom(nom);

		ResponseEntity<SearchUsersResponseDTO> res = restTemplate.postForEntity(url,searchUsersDTO,SearchUsersResponseDTO.class);

		return res;
	}

	@Override
	public ResponseEntity<Boolean> addUser(UserDTO userDTO){
		String url;
		RestTemplate restTemplate = getRestTemplate("addUser");

		url=urlDbServeur+"api3/add_user";

		ResponseEntity<Boolean> res = restTemplate.postForEntity(url,userDTO,Boolean.class);

		return res;
	}

	@Override
	public ResponseEntity<UserInfoDTO> getUser(String userId){
		String url;
		RestTemplate restTemplate = getRestTemplate("getUser");

		url=urlDbServeur+"api3/get_user";

		UserDTO userDTO=new UserDTO();
		userDTO.setLogin(userId);

		ResponseEntity<UserInfoDTO> res = restTemplate.postForEntity(url,userDTO,UserInfoDTO.class);

		return res;
	}

	@Override
	public ResponseEntity<ListCategories> listeCategorie(String userId) {
		String url;
		RestTemplate restTemplate = getRestTemplate("listeCategorie");

		URI url2;
		url2=getUri(urlDbServeur+"api3/listeCategorie?userId={userId}",userId);

		UserDTO userDTO=new UserDTO();
		userDTO.setLogin(userId);

		ResponseEntity<ListCategories> res = restTemplate.getForEntity(url2, ListCategories.class);

		return res;
	}


	public ResponseEntity<RssChannel[]> listeRssCategorie(String idUser,String id) throws UnsupportedEncodingException{
		URI url=getUri(urlDbServeur+"api3/listeRss?userId={userId}&type=categorie&id={id}",idUser,id);

		RestTemplate restTemplate = getRestTemplate("listeRssCategorie");

		ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class);

		return tmp;
	}

	public ResponseEntity<RssChannel[]> listeRssFlux(String idUser,String id) throws UnsupportedEncodingException{
		URI url=getUri(urlDbServeur+"api3/listeRss?userId={userId}&type=flux&id={id}",idUser,id);

		RestTemplate restTemplate = getRestTemplate("listeRssFlux");

		ResponseEntity<RssChannel[]> tmp = restTemplate.getForEntity(url, RssChannel[].class);

		return tmp;
	}


}
