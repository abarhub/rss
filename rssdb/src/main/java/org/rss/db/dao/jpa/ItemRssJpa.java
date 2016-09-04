package org.rss.db.dao.jpa;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Alain on 01/11/2015.
 */
@Entity
@Table(name="FEEDS_ITEM")
public class ItemRssJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String title;

	@Column(nullable = false,length = 5000)
	private String description;

	@Column
	private String link;

	@Column
	private ZonedDateTime pubDate;

	@Column(nullable = false)
	private String guid;

	public ItemRssJpa() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ZonedDateTime getPubDate() {
		return pubDate;
	}

	public void setPubDate(ZonedDateTime pubDate) {
		this.pubDate = pubDate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean similaire(ItemRssJpa itemRssJpa){
		if(!StringUtils.isEmpty(getLink())&&!StringUtils.isEmpty(itemRssJpa.getLink())) {
			return getLink().equals(itemRssJpa.getLink());
		}
		if(!StringUtils.isEmpty(getGuid())&&!StringUtils.isEmpty(itemRssJpa.getGuid())) {
			return getGuid().equals(itemRssJpa.getGuid());
		}
		return false;
	}

	@Override
	public String toString() {
		return "ItemRssJpa{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", link='" + link + '\'' +
				", pubDate=" + pubDate +
				", guid='" + guid + '\'' +
				'}';
	}
}
