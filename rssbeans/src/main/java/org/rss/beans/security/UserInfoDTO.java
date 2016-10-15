package org.rss.beans.security;

/**
 * Created by Alain on 15/10/2016.
 */
public class UserInfoDTO {

	private String login;
	private String nom;
	private String prenom;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "UserInfoDTO{" +
				"login='" + login + '\'' +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				'}';
	}
}
