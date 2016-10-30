package org.rss.db.dao.repository;

import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Alain on 11/09/2016.
 */
public interface CategorieRepository extends CrudRepository<CategorieJpa,Integer>,JpaRepository<CategorieJpa,Integer> {

	CategorieJpa findByNameAndUserJpa(String nom,UserJpa userJpa);

	List<CategorieJpa> findByUserJpa(UserJpa userJpa);

}
