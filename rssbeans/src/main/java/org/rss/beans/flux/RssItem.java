package org.rss.beans.flux;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * Created by Alain on 28/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RssItem {

	private String title;
	private String description;
	private String link;
	private DateTimeZone pubDate;
	private String guid;

	public RssItem() {
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

	public DateTimeZone getPubDate() {
		return pubDate;
	}

	public void setPubDate(DateTimeZone pubDate) {
		this.pubDate = pubDate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public String toString() {
		return "RssItem{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", link='" + link + '\'' +
				", pubDate='" + pubDate + '\'' +
				", guid='" + guid + '\'' +
				'}';
	}
}
