package org.rss.ui.service;

import org.rss.beans.flux.Categorie;
import org.rss.beans.flux.ListCategories;
import org.rss.beans.flux.RssChannel;
import org.rss.registry.IRestDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Alain on 15/10/2016.
 */
@Service
public class RestDbUser implements IRestDbUser {

	@Autowired
	private UserService userService;

	@Autowired
	private IRestDb restDb;


	@Override
	public ResponseEntity<String> add_url(String nom, String url) throws UnsupportedEncodingException {
		String userId = getUserConnecte();
		return restDb.add_url(userId,nom,url);
	}

	private String getUserConnecte() {
		String userId=userService.getUserId();
		if(StringUtils.isEmpty(userId)){
			throw new SecurityException("L'utilisateur n'est pas connecté");
		}
		return userId;
	}

	@Override
	public ResponseEntity<RssChannel[]> listeRssDetaille() throws UnsupportedEncodingException {
		String userId = getUserConnecte();
		return restDb.listeRssDetaille(userId);
	}

	public ResponseEntity<RssChannel[]> listeRssCategorie(String id) throws UnsupportedEncodingException{
		String userId = getUserConnecte();
		return restDb.listeRssCategorie(userId,id);
	}

	public ResponseEntity<RssChannel[]> listeRssFlux(String id) throws UnsupportedEncodingException{
		String userId = getUserConnecte();
		return restDb.listeRssFlux(userId,id);
	}

	@Override
	public ResponseEntity<ListCategories> listeCategorie() {
		String userId = getUserConnecte();
		return restDb.listeCategorie(userId);
	}
}
