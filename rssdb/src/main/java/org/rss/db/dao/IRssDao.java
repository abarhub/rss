package org.rss.db.dao;

import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.FeedsGenericJpa;
import org.rss.db.dao.jpa.FeedsJpa;
import org.rss.db.dao.jpa.UserJpa;

import java.util.List;

/**
 * Created by Alain on 11/09/2016.
 */
public interface IRssDao {
	CategorieJpa addCategorie(CategorieJpa categorieJpa);

	List<CategorieJpa> findCategorieByName(UserJpa userJpa, String nom);

	public FeedsJpa addRss(CategorieJpa categorieJpa, FeedsGenericJpa feedsGenericJpa) throws ErrorJpaException;

}
