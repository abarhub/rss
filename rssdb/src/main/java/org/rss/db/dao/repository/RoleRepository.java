package org.rss.db.dao.repository;

import org.rss.db.dao.jpa.ItemRssJpa;
import org.rss.db.dao.jpa.RoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Alain on 11/09/2016.
 */
public interface RoleRepository extends CrudRepository<RoleJpa,Integer>,JpaRepository<RoleJpa,Integer> {


}
