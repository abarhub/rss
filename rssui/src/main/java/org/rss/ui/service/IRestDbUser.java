package org.rss.ui.service;

import org.rss.beans.flux.Categorie;
import org.rss.beans.flux.ListCategories;
import org.rss.beans.flux.RssChannel;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Alain on 15/10/2016.
 */
public interface IRestDbUser {

	ResponseEntity<String> add_url(String nom, String url) throws UnsupportedEncodingException;

	ResponseEntity<RssChannel[]> listeRssDetaille() throws UnsupportedEncodingException;

	ResponseEntity<RssChannel[]> listeRssCategorie(String id) throws UnsupportedEncodingException;

	ResponseEntity<RssChannel[]> listeRssFlux(String id) throws UnsupportedEncodingException;

	ResponseEntity<ListCategories> listeCategorie();
}
