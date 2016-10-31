package org.rss.db.dao.repository;

import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.FeedsNameJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Alain on 11/09/2016.
 */
public interface FeedsNameRepository extends CrudRepository<FeedsNameJpa,Integer>,JpaRepository<FeedsNameJpa,Integer> {

	//FeedsNameJpa findByNameAndCategorieJpa(String nom, CategorieJpa categorieJpa);
	FeedsNameJpa findByUrlJpa(UrlJpa urlJpa);

	@Query("select f from FeedsNameJpa f where f.feeds.id=:id")
	FeedsNameJpa findByIdFeeds(@Param("id")int idFeeds);

}
