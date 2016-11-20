package org.rss.beans.param;

import java.util.List;

/**
 * Created by Alain on 25/10/2015.
 */
public final class RssListeUrl {

	private List<RssUrl> liste_url;

	private String idServeur;

	public RssListeUrl() {
	}

	public List<RssUrl> getListe_url() {
		return liste_url;
	}

	public void setListe_url(List<RssUrl> liste_url) {
		this.liste_url = liste_url;
	}

	public String getIdServeur() {
		return idServeur;
	}

	public void setIdServeur(String idServeur) {
		this.idServeur = idServeur;
	}

	@Override
	public String toString() {
		return "RssListeUrl{" +
				"liste_url=" + liste_url +
				", idServeur='" + idServeur + '\'' +
				'}';
	}
}
