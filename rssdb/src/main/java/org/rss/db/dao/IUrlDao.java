package org.rss.db.dao;

import org.rss.beans.flux.ListCategories;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.UrlJpa;

import java.util.List;

/**
 * Created by Alain on 30/01/2016.
 */
public interface IUrlDao {

	public void save(UrlJpa url);

	public List<UrlJpa> getListeUrl();

	public void saveRss(String url,FeedsRssJpa rss);

	public List<FeedsRssJpa> liste_rss();

	public boolean isUrlExiste(String url);

	public boolean isNomExiste(String nom);

	List<FeedsRssJpa> listeRssUser(String userId);

	List<FeedsRssJpa> listeRssCategorie(String userId, int id);

	List<FeedsRssJpa> listeRssFlux(String userId, int id);

	ListCategories listeCategorie(String userId);
}
