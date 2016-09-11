package org.rss.db.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rss.db.dao.jpa.UrlJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alain on 28/08/2016.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {JpaTestConfig.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
@Transactional
public class DAOUrlITest {

	@Autowired
	private UrlDao daoUrl;

	@Test
	public void save1OK() throws Exception {
		UrlJpa urlJpa;
		urlJpa=new UrlJpa();
		urlJpa.setUrl("http://test.com/");
		urlJpa.setNom("aaa");
		daoUrl.save(urlJpa);

		List<UrlJpa> listeUrl = daoUrl.getListeUrl();
		assertNotNull(listeUrl);
		assertEquals(1,listeUrl.size());
		UrlJpa urlJpa2=listeUrl.get(0);
		assertNotNull(urlJpa2);
		assertEquals("aaa",urlJpa2.getNom());
		assertEquals("http://test.com/",urlJpa2.getUrl());
	}

	@Test
	public void save2OK() throws Exception {
		UrlJpa urlJpa;
		urlJpa=new UrlJpa();
		urlJpa.setUrl("http://test1.com/");
		urlJpa.setNom("aaa01");
		daoUrl.save(urlJpa);

		List<UrlJpa> listeUrl = daoUrl.getListeUrl();
		assertNotNull(listeUrl);
		assertEquals(1,listeUrl.size());
		UrlJpa urlJpa2=listeUrl.get(0);
		assertNotNull(urlJpa2);
		assertEquals("aaa01",urlJpa2.getNom());
		assertEquals("http://test1.com/",urlJpa2.getUrl());

		urlJpa=new UrlJpa();
		urlJpa.setUrl("http://test2.com/");
		urlJpa.setNom("aaa02");
		daoUrl.save(urlJpa);

		listeUrl = daoUrl.getListeUrl();
		assertNotNull(listeUrl);
		assertEquals(2,listeUrl.size());
		urlJpa2=listeUrl.get(0);
		assertNotNull(urlJpa2);
		assertEquals("aaa01",urlJpa2.getNom());
		assertEquals("http://test1.com/",urlJpa2.getUrl());

		urlJpa2=listeUrl.get(1);
		assertNotNull(urlJpa2);
		assertEquals("aaa02",urlJpa2.getNom());
		assertEquals("http://test2.com/",urlJpa2.getUrl());
	}

}