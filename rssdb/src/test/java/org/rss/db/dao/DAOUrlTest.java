package org.rss.db.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.rss.db.dao.jpa.UrlJpa;
import org.rss.db.dao.repository.RssRepository;
import org.rss.db.dao.repository.UrlRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Alain on 14/08/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DAOUrlTest {

	@Mock
	private UrlRepository repository;

	@Mock
	private RssRepository repo_rss;

	@InjectMocks
	private UrlDao daoUrl;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void getListeUrl() throws Exception {
		List<UrlJpa> listeResponse;
		UrlJpa urlJpa;
		listeResponse=new ArrayList<>();
		urlJpa = getUrlJpa(7,"AAA","http://test");
		listeResponse.add(urlJpa);
		when(repository.findAllUrl()).thenReturn(listeResponse);

		listeResponse=daoUrl.getListeUrl();

		assertNotNull(listeResponse);
		assertEquals(1,listeResponse.size());
		urlJpa=listeResponse.get(0);
		assertEquals(7,(int)urlJpa.getId());
		assertEquals("AAA",urlJpa.getNom());
		assertEquals("http://test",urlJpa.getUrl());
	}

	private UrlJpa getUrlJpa(int id,String nom,String url) {
		UrlJpa urlJpa;
		urlJpa=new UrlJpa();
		urlJpa.setId(id);
		urlJpa.setNom(nom);
		urlJpa.setUrl(url);
		return urlJpa;
	}

}