package org.rss.db.dao;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rss.db.dao.jpa.CategorieJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Alain on 11/09/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
@Transactional
public class RssDaoITest {

	@Autowired
	private IRssDao rssDao;

	@Autowired
	private TestFixtureDao testFixtureDao;

	@Test
	public void addCategorieOK() throws Exception {
		CategorieJpa categorieJpa,categorieJpa2;
		categorieJpa=new CategorieJpa();
		categorieJpa.setName("AAA");
		categorieJpa.setDescription("BBB");
		categorieJpa.setUserJpa(testFixtureDao.getUser("CCC"));

		categorieJpa2=rssDao.addCategorie(categorieJpa);

		assertNotNull(categorieJpa2);
		assertNotNull(categorieJpa2.getId());
		assertEquals("AAA",categorieJpa.getName());
		assertEquals("BBB",categorieJpa.getDescription());
		assertNotNull(categorieJpa.getUserJpa());
		assertEquals("CCC",categorieJpa.getUserJpa().getLogin());
	}

	@Test
	public void findCategorieByNameOK() throws Exception {
		UserJpa user = testFixtureDao.getUser("User1");
		CategorieJpa categorieJpa=testFixtureDao.getCategorie(user,"categ1");
		assertNotNull(categorieJpa.getId());
		assertTrue(categorieJpa.getId()>0);

		List<CategorieJpa> list = rssDao.findCategorieByName(user, "categ1");
		assertNotNull(list);
		assertEquals(1,list.size());
		CategorieJpa categorieJpa2=list.get(0);
		assertNotNull(categorieJpa2.getId());
		assertEquals(categorieJpa.getId(),categorieJpa2.getId());
		assertEquals("categ1",categorieJpa2.getName());
	}


	@Test
	public void findCategorieByName2OK() throws Exception {
		UserJpa user = testFixtureDao.getUser("User1");
		CategorieJpa categorieJpa;
		Map<Integer,CategorieJpa> map= Maps.newHashMap();

		for(int i=1;i<=5;i++) {
			categorieJpa = testFixtureDao.getCategorie(user, "categ"+i);
			assertNotNull(categorieJpa.getId());
			assertTrue(categorieJpa.getId() > 0);
			map.put(i,categorieJpa);
		}

		for(int i=1;i<=5;i++) {
			List<CategorieJpa> list = rssDao.findCategorieByName(user, "categ"+i);
			assertNotNull(list);
			assertEquals(1, list.size());
			CategorieJpa categorieJpa2 = list.get(0);
			assertNotNull(categorieJpa2.getId());
			assertTrue(categorieJpa2.getId() > 0);
			categorieJpa=map.get(i);
			assertNotNull(categorieJpa);
			assertEquals(categorieJpa.getId(), categorieJpa2.getId());
			assertEquals("categ"+i, categorieJpa2.getName());
		}
	}

}