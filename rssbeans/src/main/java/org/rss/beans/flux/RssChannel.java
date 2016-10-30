package org.rss.beans.flux;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Alain on 28/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RssChannel {

	private String id;
	private String url;
	private String urlRss;
	private List<RssItem> listeItem;
	private String title;
	private String description;
	private String language;
	private DateTimeZone lastBuildDate;
	private DateTimeZone pubDate;
	private String name;

	public RssChannel() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<RssItem> getListeItem() {
		return listeItem;
	}

	public void setListeItem(List<RssItem> listeItem) {
		this.listeItem = listeItem;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public DateTimeZone getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(DateTimeZone lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public DateTimeZone getPubDate() {
		return pubDate;
	}

	public void setPubDate(DateTimeZone pubDate) {
		this.pubDate = pubDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlRss() {
		return urlRss;
	}

	public void setUrlRss(String urlRss) {
		this.urlRss = urlRss;
	}

	@Override
	public String toString() {
		return "RssChannel{" +
				"id='" + id + '\'' +
				", url='" + url + '\'' +
				", urlRss='" + urlRss + '\'' +
				", listeItem=" + listeItem +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", language='" + language + '\'' +
				", lastBuildDate=" + lastBuildDate +
				", pubDate=" + pubDate +
				", name='" + name + '\'' +
				'}';
	}
}
