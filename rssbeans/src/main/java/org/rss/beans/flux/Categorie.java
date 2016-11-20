package org.rss.beans.flux;

import java.util.List;

/**
 * Created by Alain on 31/10/2016.
 */
public class Categorie {

	private String id;
	private String nom;
	private List<RssChannel> rssChannelList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<RssChannel> getRssChannelList() {
		return rssChannelList;
	}

	public void setRssChannelList(List<RssChannel> rssChannelList) {
		this.rssChannelList = rssChannelList;
	}

	@Override
	public String toString() {
		return "Categorie{" +
				"id='" + id + '\'' +
				", nom='" + nom + '\'' +
				", rssChannelList=" + rssChannelList +
				'}';
	}
}
