package org.rss.registry;

import org.rss.beans.flux.Categorie;
import org.rss.beans.flux.ListCategories;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.metier.LoginResponseDTO;
import org.rss.beans.metier.SearchUsersResponseDTO;
import org.rss.beans.metier.UserDTO;
import org.rss.beans.param.RssListeUrl;
import org.rss.beans.security.UserInfoDTO;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Alain on 08/05/2016.
 */
public interface IRestDb {
	ResponseEntity<RssChannel[]> listeRssDetaille(String idUser) throws UnsupportedEncodingException;

	ResponseEntity<RssChannel[]> listeRssCategorie(String idUser,String id) throws UnsupportedEncodingException;

	ResponseEntity<RssChannel[]> listeRssFlux(String idUser,String id) throws UnsupportedEncodingException;

	ResponseEntity<String> add_url(String idUser,String nom, String url) throws UnsupportedEncodingException;

	ResponseEntity<String> addRss(RssChannel rss);

	ResponseEntity<RssListeUrl> lectureParametres();

	ResponseEntity<LoginResponseDTO> connecteUser(String login, String password);

	ResponseEntity<SearchUsersResponseDTO> searchUser(String nom);

	ResponseEntity<Boolean> addUser(UserDTO userDTO);

	ResponseEntity<UserInfoDTO> getUser(String userId);

	ResponseEntity<ListCategories> listeCategorie(String userId);
}
