package org.rss.db.dao;

import org.rss.db.dao.repository.RssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Alain on 11/09/2016.
 */
@Repository
@Transactional
public class RssDao {

	@Autowired
	private RssRepository rssRepository;

}
