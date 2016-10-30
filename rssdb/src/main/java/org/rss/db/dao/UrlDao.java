package org.rss.db.dao;

import com.google.common.base.Preconditions;
import org.rss.db.dao.jpa.*;
import org.rss.db.dao.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Alain on 30/01/2016.
 */
@Repository
@Transactional
public class UrlDao implements IUrlDao {

	public static final Logger LOGGER = LoggerFactory.getLogger(UrlDao.class);

	@Autowired
	private UrlRepository repository;

	@Autowired
	//private RssRepository repo_rss;
	private Rss2Repository repo_rss;

	@Autowired
	private FeedsNameRepository feedsNameRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategorieRepository categorieRepository;

	public UrlDao() {
	}

	@Override
	public void save(UrlJpa url) {
		Preconditions.checkNotNull(url);
		LOGGER.info("Sauve url : "+url.getUrl());
		repository.save(url);
	}

	@Override
	public List<UrlJpa> getListeUrl() {
		List<UrlJpa> liste1;

		liste1 = repository.findAllUrl();

		return liste1;
	}

	public void enregistre_rss(FeedsRssJpa rss) {
		Preconditions.checkNotNull(rss);
		if (repo_rss.channelExiste(rss)) {
			LOGGER.info("rss '" + rss.getUrl() + "' existe deja");
			repo_rss.updateChannel(rss);
		} else {
			LOGGER.info("Enregistrement rss '" + rss.getUrl() + "' ...");
			repo_rss.addChannel(rss);
			Preconditions.checkNotNull(rss.getId());
			UrlJpa u = repository.findByUrl(rss.getUrl());
			if(u!=null) {
				Preconditions.checkNotNull(u);
				Preconditions.checkNotNull(rss.getId());
				u.setListe_feeds(rss);
				repository.save(u);
				FeedsNameJpa feedsNameJpa=feedsNameRepository.findByUrlJpa(u);
				if(feedsNameJpa!=null){
					if(feedsNameJpa.getFeeds()==null){
						feedsNameJpa.setFeeds(rss);
						feedsNameRepository.save(feedsNameJpa);
						LOGGER.info("Asso feedsName Ok");
					}
				}
			}
			LOGGER.info("Enregistrement rss Ok");
		}
	}

	public List<FeedsRssJpa> liste_rss() {
		List<FeedsRssJpa> liste;

		liste = repo_rss.listeChannel();

		return liste;
	}

	public boolean isUrlExiste(String url) {
		Preconditions.checkNotNull(url);
		return repo_rss.urlExiste(url);
	}

	public boolean isNomExiste(String nom) {
		Preconditions.checkNotNull(nom);
		return repo_rss.nomExiste(nom);
	}

	public List<FeedsRssJpa> listeRssUser(String userId){
		List<FeedsRssJpa> liste=new ArrayList<>();

		LOGGER.info("Recuperation des flux rss de l'utilisateur "+userId);

		Preconditions.checkNotNull(userId);

		//liste = repo_rss.listeChannel();
		Optional<UserJpa> optUser = userRepository.findByLogin(userId);

		if(optUser.isPresent()){
			UserJpa user = optUser.get();

			LOGGER.info("L'utilisateur "+userId+" existe");
			LOGGER.info("Recuperation des categories");
			List<CategorieJpa> listeCategorie = categorieRepository.findByUserJpa(user);

			if(!CollectionUtils.isEmpty(listeCategorie)){
				LOGGER.info("L'utilisateur a "+listeCategorie.size()+" categories");
				for(CategorieJpa c:listeCategorie){
					if(c.isTout()){
						LOGGER.info("L'utilisateur a une categorie globale");
						List<FeedsNameJpa> liste2 = c.getFeeds();
						if(!CollectionUtils.isEmpty(liste2)){
							LOGGER.info("La categorie a "+liste2.size()+" feeds");
							for(FeedsNameJpa f:liste2){
								if(f.getFeeds()!=null) {
									Preconditions.checkNotNull(f.getFeeds());
									liste.add((FeedsRssJpa) f.getFeeds());
								}
							}
						}
						break;
					}
				}
			}
		}
		LOGGER.info("L'utilisateur a "+liste.size()+" feeds");

		return liste;
	}
}
