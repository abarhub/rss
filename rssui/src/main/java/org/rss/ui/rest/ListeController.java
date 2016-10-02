package org.rss.ui.rest;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.rss.beans.metier.SearchUsersResponseDTO;
import org.rss.beans.metier.UserDTO;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.param.RssUrl;
import org.rss.registry.IRestDb;
import org.rss.registry.impl.RestDb;
import org.rss.ui.bean.*;
import org.rss.ui.service.UIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;


import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsFirst;
import static org.rss.beans.OutilsGeneriques.vide;


/**
 * Created by Alain on 24/10/2015.
 */
@RestController
public class ListeController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ListeController.class);
	public static final Marker markerTrace = MarkerFactory.getMarker("TRACE");

	@Autowired
	private IRestDb restDb;

	@Autowired
	private UIService uiService;

	@RequestMapping("/add_url")
	public String addUrl(@RequestParam(value = "name") String nom,
	                     @RequestParam(value = "url") String url) {
		String res = "";

		LOGGER.info("ajout url ...");

		ResponseEntity<String> tmp = restDb.add_url(nom, url);

		LOGGER.info("fin url : " + tmp.getBody());

		return res;
	}


	@RequestMapping("/listeUrl")
	public ListChannelUi listeUrl() {

		ListChannelUi res;
		ChannelUi c;
		ItemUi item;

		res = new ListChannelUi();

		//String url;
		RssChannel[] liste_url;
		List<ChannelUi> liste_channel;

		liste_channel = Lists.newArrayList();
		res.setListe_channel(liste_channel);

		ResponseEntity<RssChannel[]> tmp = restDb.listeRssDetaille();

		liste_url = tmp.getBody();

		if (liste_url != null && liste_url.length > 0) {
			for (RssChannel tmp2 : liste_url) {
				Preconditions.checkNotNull(tmp2.getId());
				Preconditions.checkState(!tmp2.getId().trim().isEmpty());

				c = new ChannelUi();
				c.setDescription(tmp2.getDescription());
				c.setLanguage(tmp2.getLanguage());
				//c.setLastBuildDate(tmp2.);
				c.setTitle(tmp2.getTitle());
				c.setUrl(tmp2.getUrl());
				c.setId(tmp2.getId());
				c.setName(tmp2.getName());

				liste_channel.add(c);
			}
			if (!liste_channel.isEmpty()) {
				String titre = "Tous les flux";
				c = new ChannelUi();
				c.setDescription(titre);
				c.setLanguage("");
				//c.setLastBuildDate(tmp2.);
				c.setTitle(titre);
				c.setUrl("");
				c.setId(UIService.idTousFlux);
				c.setName(titre);

				liste_channel.add(c);
			}
		}

		return res;
	}

	@RequestMapping("/listeMessages")
	public ChannelUi listeRss(@RequestParam(value = "id", defaultValue = "", required = false) String id) {

		ListChannelUi res;
		ChannelUi c = null;

		res = new ListChannelUi();

		RssChannel[] liste_url;
		List<ChannelUi> liste_channel;

		liste_channel = Lists.newArrayList();
		res.setListe_channel(liste_channel);

		ResponseEntity<RssChannel[]> tmp = restDb.listeRssDetaille();

		liste_url = tmp.getBody();

		if (liste_url != null && liste_url.length > 0) {
			c = uiService.donneListeRss(id, liste_url);
		}

		return c;
	}

	@RequestMapping(value = "/traces", method = RequestMethod.POST)
	public void traceMessages(@RequestParam(value = "niveauErreur", defaultValue = "", required = false) String niveauErreur,
	                          @RequestParam(value = "composant", defaultValue = "", required = false) String composant,
	                          @RequestParam(value = "message", defaultValue = "", required = false) String message) {
		if (niveauErreur != null) {
			if (niveauErreur.equals("Info")) {
				LOGGER.info(markerTrace, composant + " : " + message);
			} else if (niveauErreur.equals("Erreur")) {
				LOGGER.error(markerTrace, composant + " : " + message);
			} else {
				LOGGER.info(markerTrace, niveauErreur + " ; " + composant + " : " + message);
			}
		} else {
			LOGGER.info(markerTrace, niveauErreur + " ; " + composant + " : " + message);
		}
	}


	@RequestMapping("/searchUsers")
	public ListUsersUI searchUser(@RequestParam(value = "nom", defaultValue = "", required = false) String nom) {
		ListUsersUI res=new ListUsersUI();
		UserUI userUI;

		ResponseEntity<SearchUsersResponseDTO> res2 = restDb.searchUser(nom);
		if(res2.getStatusCode().is2xxSuccessful()){
			if(res2.hasBody()){
				SearchUsersResponseDTO res3 = res2.getBody();
				if(!CollectionUtils.isEmpty(res3.getListUserDTO())){
					res.setListUserUI(new ArrayList<>());
					for(UserDTO tmp:res3.getListUserDTO()){
						userUI=new UserUI();
						userUI.setNom(tmp.getNom());
						userUI.setPrenom(tmp.getPrenom());
						userUI.setLogin(tmp.getLogin());
						userUI.setNonModifiable(tmp.isNonModifiable());
						userUI.setId(tmp.getId());
						res.getListUserUI().add(userUI);
					}
				}
			}
		}

		return res;
	}


	@RequestMapping(value = "/addUser",method = RequestMethod.POST)
	public ResponseUI addUser(@RequestBody AddUserUI addUserUI) {
		Preconditions.checkNotNull(addUserUI);
		String res;
		UserDTO user;
		ResponseUI responseUI;
		user=new UserDTO();
		user.setNom(addUserUI.getNom());
		user.setPrenom(addUserUI.getPrenom());
		user.setLogin(addUserUI.getLogin());
		user.setPassword(addUserUI.getPassword());
		ResponseEntity<Boolean> res2 = restDb.addUser(user);
		if(res2.getStatusCode().is2xxSuccessful()){
			if(res2.hasBody()&& res2.getBody()){
				responseUI=new ResponseUI();
			} else {
				responseUI=new ResponseUI("Error");
			}
		} else {
			responseUI=new ResponseUI("Error");
		}

		return responseUI;
	}

}
