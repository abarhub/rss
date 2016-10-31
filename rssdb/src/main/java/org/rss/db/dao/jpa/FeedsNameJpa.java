package org.rss.db.dao.jpa;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alain on 15/10/2016.
 */
@Entity
@Table(name="FEEDS_NAME")
public class FeedsNameJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false,length = 50)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	private CategorieJpa categorieJpa;

	//@OneToMany(fetch = FetchType.LAZY,targetEntity = FeedsGenericJpa.class)
	@ManyToOne(fetch = FetchType.LAZY/*,targetEntity = FeedsGenericJpa.class*/)
	private FeedsRssJpa feeds;

	@ManyToOne(fetch = FetchType.EAGER)
	private UrlJpa urlJpa;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="feedsNameJpa")
	private List<FeedsAttributJpa> feedsAttributJpas;

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

	public CategorieJpa getCategorieJpa() {
		return categorieJpa;
	}

	public void setCategorieJpa(CategorieJpa categorieJpa) {
		this.categorieJpa = categorieJpa;
	}

	public UrlJpa getUrlJpa() {
		return urlJpa;
	}

	public void setUrlJpa(UrlJpa urlJpa) {
		this.urlJpa = urlJpa;
	}

	public FeedsRssJpa getFeeds() {
		return feeds;
	}

	public void setFeeds(FeedsRssJpa feeds) {
		this.feeds = feeds;
	}

	public List<FeedsAttributJpa> getFeedsAttributJpas() {
		return feedsAttributJpas;
	}

	public void setFeedsAttributJpas(List<FeedsAttributJpa> feedsAttributJpas) {
		this.feedsAttributJpas = feedsAttributJpas;
	}

	@Override
	public String toString() {
		return "FeedsNameJpa{" +
				"id=" + id +
				", name='" + name + '\'' +
				", categorieJpa=" + categorieJpa +
				", feeds=" + feeds +
				", urlJpa=" + urlJpa +
				", feedsAttributJpas=" + feedsAttributJpas +
				'}';
	}
}
