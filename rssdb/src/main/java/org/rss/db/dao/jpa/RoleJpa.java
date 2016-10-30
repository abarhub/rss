package org.rss.db.dao.jpa;

import javax.persistence.*;

/**
 * Created by Alain on 10/09/2016.
 */
@Entity
@Table(name="ROLE")
public class RoleJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false,length = 30)
	private String nom;

	@Column(nullable = false,length = 100)
	private String description;

	@Column(nullable = false)
	private boolean admin;

	@Column(nullable = false)
	private boolean anonymous;

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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	@Override
	public String toString() {
		return "RoleJpa{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", description='" + description + '\'' +
				", admin=" + admin +
				", anonymous=" + anonymous +
				'}';
	}
}
