package org.rss.beans.param;

/**
 * Created by Alain on 25/10/2015.
 */
public final class RssUrl {

	private String nom;
	private String url;

	public RssUrl() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "RssUrl{" +
				"nom='" + nom + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
