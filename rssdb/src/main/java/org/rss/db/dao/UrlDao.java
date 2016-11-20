package org.rss.db.dao;

import com.google.common.base.Preconditions;
import org.rss.beans.flux.Categorie;
import org.rss.beans.flux.ListCategories;
import org.rss.beans.flux.RssChannel;
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

	public void saveRss(String url,FeedsRssJpa rss) {
		Preconditions.checkNotNull(url);
		Preconditions.checkNotNull(rss);
		if (repo_rss.urlExiste(url)) {
			LOGGER.info("rss '" + url + "' existe deja");
			rss=repo_rss.updateChannel(url,rss);
		} else {
			LOGGER.info("Enregistrement rss '" + url + "' ...");
			repo_rss.addChannel(rss);
		}
		Preconditions.checkNotNull(rss.getId());
		LOGGER.info("id rss =" + rss.getId());
		UrlJpa u = repository.findByUrl(url);
		if(u!=null) {
			Preconditions.checkNotNull(u);
			Preconditions.checkNotNull(rss.getId());
			u.setListe_feeds(rss);
			LOGGER.info("url mis à jour ...");
			u=repository.save(u);
			LOGGER.info("url mis à jour OK");
			LOGGER.info("recuperation feedsNameJpa...");
			FeedsNameJpa feedsNameJpa=feedsNameRepository.findByUrlJpa(u);
			if(feedsNameJpa!=null){
				LOGGER.info("feedsNameJpa trouvé");
				if(feedsNameJpa.getFeeds()==null){
					LOGGER.info("feedsNameJpa.feed vide");
					feedsNameJpa.setFeeds(rss);
					feedsNameRepository.save(feedsNameJpa);
					LOGGER.info("Asso feedsName Ok");
				}
			} else {
				LOGGER.info("feedsNameJpa non trouvé");
				List<FeedsNameJpa> liste2 = feedsNameRepository.findAll();
				LOGGER.info("liste.feedsNameJpa="+liste2);
			}
		} else {
			LOGGER.error("Erreur pour enregistrer le rss '"+url+"' url inexistante");
		}
		LOGGER.info("Enregistrement rss Ok");
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

	@Override
	public List<FeedsRssJpa> listeRssCategorie(String userId, int id) {
		List<FeedsRssJpa> liste=new ArrayList<>();

		LOGGER.info("Recuperation des flux rss de l'utilisateur "+userId+" pour la categorie "+id);

		Preconditions.checkNotNull(userId);

		Optional<UserJpa> optUser = userRepository.findByLogin(userId);

		if(optUser.isPresent()){
			UserJpa user = optUser.get();

			LOGGER.info("L'utilisateur "+userId+" existe");
			LOGGER.info("Recuperation des categories");
			CategorieJpa c=categorieRepository.getOne(id);
			if(c!=null){
				List<FeedsNameJpa> liste2 = c.getFeeds();
				if(!CollectionUtils.isEmpty(liste2)){
					LOGGER.info("La categorie a "+liste2.size()+" feeds");
					for(FeedsNameJpa f:liste2){
						if(f.getFeeds()!=null) {
							Preconditions.checkNotNull(f.getFeeds());
							Preconditions.checkNotNull(f.getFeeds().getId());
							/*FeedsJpa tmp = f.getFeeds();
							FeedsRssJpa f2=new FeedsRssJpa();
							f2.setName(tmp.getTitle());
							f2.setDescription(tmp.getDescription());
							f2.setUrl(tmp.getUrl());
							liste.add(f2);*/
							liste.add(f.getFeeds());
						}
					}
				}
			}
		}
		LOGGER.info("L'utilisateur a "+liste.size()+" feeds");

		return liste;
	}

	@Override
	public List<FeedsRssJpa> listeRssFlux(String userId, int id) {
		List<FeedsRssJpa> liste=new ArrayList<>();

		LOGGER.info("Recuperation des flux rss de l'utilisateur "+userId);

		Preconditions.checkNotNull(userId);

		Optional<UserJpa> optUser = userRepository.findByLogin(userId);

		if(optUser.isPresent()){
			UserJpa user = optUser.get();

			LOGGER.info("L'utilisateur "+userId+" existe");
			LOGGER.info("Recuperation du flux");

			FeedsNameJpa f = feedsNameRepository.findByIdFeeds(id);

			if(f!=null){
				if(f.getFeeds()!=null) {
					Preconditions.checkNotNull(f.getFeeds());
					liste.add((FeedsRssJpa) f.getFeeds());
				}
			}
		}
		LOGGER.info("L'utilisateur a "+liste.size()+" feeds");

		return liste;
	}

	@Override
	public ListCategories listeCategorie(String userId) {
		ListCategories liste=new ListCategories();
		Categorie c2;

		LOGGER.info("Recuperation des catégories de l'utilisateur "+userId);

		Preconditions.checkNotNull(userId);

		Optional<UserJpa> optUser = userRepository.findByLogin(userId);

		if(optUser.isPresent()) {
			UserJpa user = optUser.get();

			List<CategorieJpa> listeCategorie = categorieRepository.findByUserJpa(user);

			if(!CollectionUtils.isEmpty(listeCategorie)){
				liste.setCategorieList(new ArrayList<>());

				for(CategorieJpa c:listeCategorie){
					c2=new Categorie();
					c2.setId(""+c.getId());
					c2.setNom(c.getName());
					c2.setRssChannelList(new ArrayList<>());
					liste.getCategorieList().add(c2);

					if(!CollectionUtils.isEmpty(c.getFeeds())){
						for(FeedsNameJpa f:c.getFeeds()){
							if(f.getFeeds()!=null){
								FeedsJpa f2 = f.getFeeds();
								RssChannel r;
								r=new RssChannel();
								r.setId(""+f2.getId());
								r.setName(f2.getTitle());
								c2.getRssChannelList().add(r);
							}
						}
					}
				}
			}
		}

		return liste;
	}
}
