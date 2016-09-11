package org.rss.db.dao;

import com.google.common.base.Preconditions;
import org.rss.beans.OutilsGeneriques;
import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.rss.db.dao.repository.CategorieRepository;
import org.rss.db.dao.repository.RssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alain on 11/09/2016.
 */
@Repository
@Transactional
public class RssDao implements IRssDao {

	@Autowired
	private RssRepository rssRepository;

	@Autowired
	private CategorieRepository categorieRepository;

	@Override
	public CategorieJpa addCategorie(CategorieJpa categorieJpa){
		Preconditions.checkNotNull(categorieJpa);
		Preconditions.checkNotNull(categorieJpa.getUserJpa());
		Preconditions.checkNotNull(categorieJpa.getUserJpa().getId());
		Preconditions.checkArgument(categorieJpa.getId()==null);

		return categorieRepository.save(categorieJpa);
	}

	@Override
	public List<CategorieJpa> findCategorieByName(UserJpa userJpa, String nom){
		Preconditions.checkNotNull(userJpa);
		Preconditions.checkNotNull(userJpa.getId());
		Preconditions.checkArgument(!OutilsGeneriques.vide(nom));

		CategorieJpa categorieJpa;
		Example<CategorieJpa> categorieJpaExample;
		categorieJpa=new CategorieJpa();
		categorieJpa.setName(nom);
		categorieJpa.setUserJpa(userJpa);
		categorieJpaExample=Example.of(categorieJpa);
		List<CategorieJpa> list=categorieRepository.findAll(categorieJpaExample);
		if(list==null||list.isEmpty()){
			return null;
		} else {
			return list;
		}
	}
}
