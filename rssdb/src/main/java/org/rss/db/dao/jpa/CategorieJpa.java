package org.rss.db.dao.jpa;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alain on 10/09/2016.
 */
@Entity
@Table(name="CATEGORIE")
public class CategorieJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false,length = 50)
	private String name;

	@Column(nullable = false,length = 100)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="categorieJpa")
	private List<FeedsAttributJpa> feeds;

	@ManyToOne
	private UserJpa userJpa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FeedsAttributJpa> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<FeedsAttributJpa> feeds) {
		this.feeds = feeds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserJpa getUserJpa() {
		return userJpa;
	}

	public void setUserJpa(UserJpa userJpa) {
		this.userJpa = userJpa;
	}

	@Override
	public String toString() {
		return "CategorieJpa{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", feeds=" + feeds +
				", userJpa=" + userJpa +
				'}';
	}
}
