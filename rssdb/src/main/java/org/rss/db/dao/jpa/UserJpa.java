package org.rss.db.dao.jpa;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alain on 10/09/2016.
 */
@Entity
@Table(name="USER")
public class UserJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false,length = 30)
	private String login;

	@Column(nullable = false,length = 50)
	private String password;

	@Column(nullable = false,length = 50)
	private String nom;

	@Column(nullable = true,length = 50)
	private String prenom;

	@ManyToOne(fetch = FetchType.EAGER)
	private RoleJpa role;

	@Column(nullable = false)
	private boolean desactive;

	@OneToMany(fetch = FetchType.LAZY,mappedBy="userJpa")
	private List<CategorieJpa> listeCategorie;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="userJpa")
	private List<FeedsAttributJpa> feeds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public RoleJpa getRole() {
		return role;
	}

	public void setRole(RoleJpa role) {
		this.role = role;
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

	public boolean isDesactive() {
		return desactive;
	}

	public void setDesactive(boolean desactive) {
		this.desactive = desactive;
	}

	public List<CategorieJpa> getListeCategorie() {
		return listeCategorie;
	}

	public void setListeCategorie(List<CategorieJpa> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	public List<FeedsAttributJpa> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<FeedsAttributJpa> feeds) {
		this.feeds = feeds;
	}

	@Override
	public String toString() {
		return "UserJpa{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", role=" + role +
				", desactive=" + desactive +
				", listeCategorie=" + listeCategorie +
				", feeds=" + feeds +
				'}';
	}

}
