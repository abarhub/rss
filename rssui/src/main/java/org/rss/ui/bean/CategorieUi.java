package org.rss.ui.bean;

import java.util.List;

/**
 * Created by Alain on 31/10/2016.
 */
public class CategorieUi {

	private String id;
	private String nom;
	private List<FluxUi> fluxUiList;

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

	public List<FluxUi> getFluxUiList() {
		return fluxUiList;
	}

	public void setFluxUiList(List<FluxUi> fluxUiList) {
		this.fluxUiList = fluxUiList;
	}

	@Override
	public String toString() {
		return "CategorieUi{" +
				"id='" + id + '\'' +
				", nom='" + nom + '\'' +
				", fluxUiList=" + fluxUiList +
				'}';
	}
}
