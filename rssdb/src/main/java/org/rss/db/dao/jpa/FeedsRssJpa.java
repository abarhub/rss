package org.rss.db.dao.jpa;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Alain on 01/11/2015.
 */
@Entity
@DiscriminatorValue("1")
public class FeedsRssJpa extends FeedsGenericJpa {

	@OneToMany(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
	private List<ItemRssJpa> listeItem;

	@Column(nullable = false)
	private String language;

	@Column
	private ZonedDateTime lastBuildDate;

	@Column
	private ZonedDateTime pubDate;

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

	public ZonedDateTime getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(ZonedDateTime lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public ZonedDateTime getPubDate() {
		return pubDate;
	}

	public void setPubDate(ZonedDateTime pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "FeedsRssJpa{" +
				"listeItem=" + listeItem +
				", language='" + language + '\'' +
				", lastBuildDate=" + lastBuildDate +
				", pubDate=" + pubDate +
				'}';
	}
}
