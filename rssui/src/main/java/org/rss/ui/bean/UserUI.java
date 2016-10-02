package org.rss.ui.bean;

/**
 * Created by Alain on 02/10/2016.
 */
public class UserUI {

	private String nom;
	private String prenom;
	private String login;
	private boolean nonModifiable;
	private int id;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isNonModifiable() {
		return nonModifiable;
	}

	public void setNonModifiable(boolean nonModifiable) {
		this.nonModifiable = nonModifiable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserUI{" +
				"nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", login='" + login + '\'' +
				", nonModifiable=" + nonModifiable +
				", id=" + id +
				'}';
	}
}
