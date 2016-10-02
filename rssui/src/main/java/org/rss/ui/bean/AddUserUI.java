package org.rss.ui.bean;

/**
 * Created by Alain on 02/10/2016.
 */
public class AddUserUI {

	private String login;
	private String password;
	private String nom;
	private String prenom;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "AddUserUI{" +
				"login='" + login + '\'' +
				", password='" + password + '\'' +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				'}';
	}
}
