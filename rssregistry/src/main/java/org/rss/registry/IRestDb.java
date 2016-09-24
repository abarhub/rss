package org.rss.registry;

import org.rss.beans.flux.RssChannel;
import org.rss.beans.param.RssListeUrl;
import org.springframework.http.ResponseEntity;

/**
 * Created by Alain on 08/05/2016.
 */
public interface IRestDb {
	ResponseEntity<RssChannel[]> listeRssDetaille();

	ResponseEntity<String> add_url(String nom, String url);

	ResponseEntity<String> addRss(RssChannel rss);

	ResponseEntity<RssListeUrl> lectureParametres();

	ResponseEntity<Boolean> connecteUser(String login,String password);
}
