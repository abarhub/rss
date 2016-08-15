package org.rss.ui.bean;

import org.rss.beans.flux.DateTimeZone;

/**
 * Created by Alain on 01/11/2015.
 */
public class ItemUi {

	private String title;
	private String description;
	private String link;
	private String pubDate;
	private DateTimeZone pubDate2;
	private String guid;

	public ItemUi() {
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

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public DateTimeZone getPubDate2() {
		return pubDate2;
	}

	public void setPubDate2(DateTimeZone pubDate2) {
		this.pubDate2 = pubDate2;
	}
}
