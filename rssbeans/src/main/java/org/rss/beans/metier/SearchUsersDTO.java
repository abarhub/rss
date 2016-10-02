package org.rss.beans.metier;

/**
 * Created by Alain on 02/10/2016.
 */
public class SearchUsersDTO {

	private String nom;

	public SearchUsersDTO() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return "SearchUsersDTO{" +
				"nom='" + nom + '\'' +
				'}';
	}
}
