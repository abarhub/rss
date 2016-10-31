package org.rss.ui.bean;

/**
 * Created by Alain on 31/10/2016.
 */
public class FluxUi {

	private String id;
	private String nom;

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

	@Override
	public String toString() {
		return "FluxUi{" +
				"id='" + id + '\'' +
				", nom='" + nom + '\'' +
				'}';
	}
}
