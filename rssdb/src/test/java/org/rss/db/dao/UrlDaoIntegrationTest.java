package org.rss.db.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.FeedsNameJpa;
import org.rss.db.dao.jpa.FeedsRssJpa;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alain on 30/10/2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlDaoIntegrationTest {

	private static final String URL1="fgdfgs";

	@Autowired
	//private RssRepository repo_rss;
	private Rss2Repository repo_rss;

	@Autowired
	private UrlDao urlDao;

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private FeedsNameRepository feedsNameRepository;

	@Autowired
	private TestFixtureDao testFixtureDao;

	@Before
	public void setUp() throws Exception {
		saveUrl(URL1,"BBB");

	}


	@Test
	public void enregistre_rssOK() throws Exception {
		FeedsRssJpa rss;
		String url,description;
		url="http://aaabb;ùdsfsqfdsf";
		description="GGG";
		rss = testFixtureDao.getFeedsRssJpa(url,description);
		urlDao.enregistre_rss(rss);
		assertTrue(repo_rss.urlExiste(url));
		//FeedsRssJpa rss2;
		//rss2=repo_rss.
		List<FeedsRssJpa> liste = repo_rss.listeChannel();
		assertNotNull(liste);
		assertEquals(1,liste.size());
		FeedsRssJpa rss2;
		rss2=liste.get(0);
		assertNotNull(rss2);
		assertEquals(url,rss2.getUrl());
		assertEquals(description,rss2.getDescription());
		assertEquals(rss.getLanguage(),rss2.getLanguage());
	}

	@Test
	public void enregistre_rss2OK() throws Exception {
		FeedsRssJpa rss;
		String url;
		url="http://tttbb;ùdsfsqfdsf";
		rss = testFixtureDao.getFeedsRssJpa(url,"GGG");
		urlDao.enregistre_rss(rss);
		assertTrue(repo_rss.urlExiste(url));
		FeedsRssJpa rss2=findRssJpa(url);
		assertNotNull(rss2);
		assertEquals(url,rss2.getUrl());
	}

	@Test
	public void enregistre_rss3OK() throws Exception {
		FeedsRssJpa rss;
		String url;
		url="http://tttbb;ùdsfsqfdsf";

		saveUrl(url,"CCC");

		UrlJpa urlJpa=urlRepository.findByUrl(url);
		assertNotNull(urlJpa);
		assertEquals(url,urlJpa.getUrl());
		FeedsNameJpa feedsNameJpa=feedsNameRepository.findByUrlJpa(urlJpa);
		assertNull(feedsNameJpa);

		feedsNameJpa = testFixtureDao.getFeedsNameJpa(urlJpa);
		feedsNameRepository.save(feedsNameJpa);
		feedsNameJpa=feedsNameRepository.findByUrlJpa(urlJpa);
		assertNotNull(feedsNameJpa);
		assertNull(feedsNameJpa.getFeeds());

		// methode à tester
		rss = testFixtureDao.getFeedsRssJpa(url,"GGG");
		urlDao.enregistre_rss(rss);

		assertTrue(repo_rss.urlExiste(url));
		FeedsRssJpa rss2=findRssJpa(url);
		assertNotNull(rss2);
		assertEquals(url,rss2.getUrl());
		urlJpa=urlRepository.findByUrl(url);
		assertNotNull(urlJpa);

		feedsNameJpa=feedsNameRepository.findByUrlJpa(urlJpa);
		assertNotNull(feedsNameJpa);
		assertNotNull(feedsNameJpa.getFeeds());
	}

	private FeedsRssJpa findRssJpa(String url){
		List<FeedsRssJpa> liste = repo_rss.listeChannel();
		if(CollectionUtils.isEmpty(liste)){
			return null;
		} else {
			for(FeedsRssJpa feedsRssJpa:liste){
				if(feedsRssJpa.getUrl().equals(url)){
					return feedsRssJpa;
				}
			}
			return null;
		}
	}

	private void saveUrl(String url,String name) {
		UrlJpa urlJpa=new UrlJpa();
		urlJpa.setUrl(url);
		urlJpa.setNom(name);
		urlJpa.setListe_feeds(null);
		urlRepository.save(urlJpa);
	}
}