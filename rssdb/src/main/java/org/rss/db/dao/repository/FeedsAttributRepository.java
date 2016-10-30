package org.rss.db.dao.repository;

import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.FeedsAttributJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Alain on 11/09/2016.
 */
public interface FeedsAttributRepository extends CrudRepository<FeedsAttributJpa,Integer>,JpaRepository<FeedsAttributJpa,Integer> {


}
