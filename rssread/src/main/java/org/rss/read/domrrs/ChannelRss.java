package org.rss.read.domrrs;

import java.util.List;

/**
 * Created by Alain on 18/10/2015.
 */
public class ChannelRss {

	private String title;
	private String description;
	private String link;
	private String language;
	private String lastBuildDate;
	private String pubDate;

	private List<ItemRss> listItem;

	public ChannelRss(){

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

	public List<ItemRss> getListItem() {
		return listItem;
	}

	public void setListItem(List<ItemRss> listItem) {
		this.listItem = listItem;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	@Override
	public String toString() {
		return "ChannelRss{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", link='" + link + '\'' +
				", language='" + language + '\'' +
				", lastBuildDate='" + lastBuildDate + '\'' +
				", pubDate='" + pubDate + '\'' +
				", listItem=" + listItem +
				'}';
	}
}
