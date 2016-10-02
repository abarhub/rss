package org.rss.beans.metier;

/**
 * Created by Alain on 02/10/2016.
 */
public class UserDTO {

	private String login;
	private String nom;
	private String prenom;
	private boolean nonModifiable;
	private int id;

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
		return "UserDTO{" +
				"login='" + login + '\'' +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", nonModifiable=" + nonModifiable +
				", id=" + id +
				'}';
	}
}
