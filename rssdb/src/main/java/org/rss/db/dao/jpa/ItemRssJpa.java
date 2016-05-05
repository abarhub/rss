package org.rss.db.dao.jpa;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alain on 01/11/2015.
 */
@Entity//(name = "item")
@Table(name="FEEDS_ITEM")
public class ItemRssJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column//(nullable = false)
	private String title;

	@Column(nullable = false,length = 5000)
	private String description;

	@Column//(nullable = false)
	private String link;

	@Column//(nullable = false)
	private Date pubDate;

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

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
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
}
