package org.rss.db.dao.repository;

import org.rss.db.dao.jpa.FeedsRssJpa;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Alain on 30/10/2016.
 */
public interface Rss3Repository extends CrudRepository<FeedsRssJpa, Integer> {

	Optional<FeedsRssJpa> findByUrl(String url);

	Optional<FeedsRssJpa> findByName(String name);
}
