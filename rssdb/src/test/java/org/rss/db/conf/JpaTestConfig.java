package org.rss.db.conf;

import org.rss.db.dao.jpa.FeedsGenericJpa;
import org.rss.db.dao.repository.UrlRepository;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Alain on 28/08/2016.
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = UrlRepository.class)
@EntityScan(basePackageClasses=FeedsGenericJpa.class)
public class JpaTestConfig {
}
