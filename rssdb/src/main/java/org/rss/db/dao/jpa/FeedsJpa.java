package org.rss.db.dao.jpa;

/**
 * Created by Alain on 13/02/2016.
 */
public interface FeedsJpa {

	public String getUrl();

	public void setUrl(String url);

	public Integer getId();

	public void setId(Integer id);

	public String getTitle();

	public void setTitle(String title);

	public String getDescription();

	public void setDescription(String description);

}
