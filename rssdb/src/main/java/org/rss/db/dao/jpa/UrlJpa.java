package org.rss.db.dao.jpa;

import javax.persistence.*;

/**
 * Created by Alain on 25/10/2015.
 */
@Entity
@Table(name="PARAM_URL")
public class UrlJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false,length = 1000)
	private String nom;

	@Column(nullable = false,length = 1000)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY,optional = true,targetEntity = FeedsGenericJpa.class)
	private FeedsJpa liste_feeds;

	public UrlJpa() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public FeedsJpa getListe_feeds() {
		return liste_feeds;
	}

	public void setListe_feeds(FeedsJpa liste_feeds) {
		this.liste_feeds = liste_feeds;
	}
}
