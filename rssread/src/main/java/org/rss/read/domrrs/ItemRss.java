package org.rss.read.domrrs;

/**
 * Created by Alain on 18/10/2015.
 */
public class ItemRss {

	private String title;
	private String description;
	private String link;
	private String pubDate;
	private String guid;

	public ItemRss() {
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

	@Override
	public String toString() {
		return "ItemRss{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", link='" + link + '\'' +
				", pubDate='" + pubDate + '\'' +
				", guid='" + guid + '\'' +
				'}';
	}

}
