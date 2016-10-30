package org.rss.ui.service;

import org.rss.beans.flux.RssChannel;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Alain on 15/10/2016.
 */
public interface IRestDbUser {

	ResponseEntity<String> add_url(String nom, String url) throws UnsupportedEncodingException;

	ResponseEntity<RssChannel[]> listeRssDetaille() throws UnsupportedEncodingException;
}
