package org.rss.db.dao.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Alain on 01/11/2015.
 */
@Entity//(name = "channel")
@DiscriminatorValue("1")
//@Table(name="FEEDS_RSS")
public class FeedsRssJpa extends FeedsGenericJpa {



	//@OneToMany(fetch = FetchType.EAGER,orphanRemoval = true)
	/*@ElementCollection
	@CollectionTable(
			name="item",
			joinColumns=@JoinColumn(name="OWNER_ID")
	)*/
	//@ManyToOne (cascade = CascadeType.ALL)
	//@JoinColumn (name = "OWNER_ID")
	@OneToMany(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
	private List<ItemRssJpa> listeItem;



	@Column(nullable = false)
	private String language;

	@Column(nullable = false)
	private Date lastBuildDate;

	@Column//(nullable = false)
	private Date pubDate;

	public FeedsRssJpa() {
	}

	public List<ItemRssJpa> getListeItem() {
		return listeItem;
	}

	public void setListeItem(List<ItemRssJpa> listeItem) {
		this.listeItem = listeItem;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

}
