package org.rss.db.dao.repository;

import com.google.common.collect.Lists;
import org.rss.beans.param.RssUrl;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alain on 25/10/2015.
 */
@Repository
@Transactional
public class UrlRssRepository {

	@PersistenceUnit
	private EntityManagerFactory emf;

	public void addUrl(UrlJpa url){

		EntityManager em = emf.createEntityManager();
		//em.persist(url);
		//em.flush();
		em.merge(url);
	}

	public List<UrlJpa> getListe()
	{
		List<UrlJpa> res;

		res= Lists.newArrayList();
		Query query;
		query = emf.createEntityManager().createQuery("SELECT e FROM UrlJpa e");
		//query = emf.createEntityManager().createQuery("SELECT e FROM url e");
		Collection<UrlJpa> liste1 = (Collection<UrlJpa>) query.getResultList();

		if(liste1!=null&&!liste1.isEmpty())
		{
			for(UrlJpa u:liste1)
			{
				res.add(u);
			}
		}

		return res;
	}
}
