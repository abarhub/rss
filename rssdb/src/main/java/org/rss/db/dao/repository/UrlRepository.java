package org.rss.db.dao.repository;

import org.rss.beans.param.RssUrl;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Alain on 30/01/2016.
 */
public interface UrlRepository extends CrudRepository<UrlJpa, Integer> {

	@Query("SELECT e FROM UrlJpa e")
	List<UrlJpa> findAllUrl();

	UrlJpa findByUrl(String url);

}
