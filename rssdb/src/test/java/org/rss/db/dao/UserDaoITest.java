package org.rss.db.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rss.db.dao.jpa.RoleJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alain on 11/09/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserDaoITest {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private TestFixtureDao testFixture;

	@Test
	public void addRoleOK() throws Exception {
		RoleJpa roleJpa;
		final String login = "Test3";

		roleJpa=userDao.addRole("AAA","BBB");
		assertNotNull(roleJpa);
		assertNotNull(roleJpa.getId());
		assertEquals("AAA",roleJpa.getNom());
		assertEquals("BBB",roleJpa.getDescription());
		assertEquals(false,roleJpa.isAdmin());
		assertEquals(false,roleJpa.isAnonymous());

		roleJpa=userDao.findRoleByNom("AAA");
		assertNotNull(roleJpa);
		assertNotNull(roleJpa.getId());
		assertEquals("AAA",roleJpa.getNom());
		assertEquals("BBB",roleJpa.getDescription());
		assertEquals(false,roleJpa.isAdmin());
		assertEquals(false,roleJpa.isAnonymous());
	}

	@Test
	public void addRoleKO() throws Exception {
		RoleJpa roleJpa;
		final String nom = "AAA";

		roleJpa=userDao.addRole(nom,"BBB");
		assertNotNull(roleJpa);
		assertNotNull(roleJpa.getId());
		assertEquals(nom,roleJpa.getNom());
		assertEquals("BBB",roleJpa.getDescription());
		assertEquals(false,roleJpa.isAdmin());
		assertEquals(false,roleJpa.isAnonymous());

		roleJpa=userDao.findRoleByNom(nom);
		assertNotNull(roleJpa);
		assertNotNull(roleJpa.getId());
		assertEquals(nom,roleJpa.getNom());
		assertEquals("BBB",roleJpa.getDescription());
		assertEquals(false,roleJpa.isAdmin());
		assertEquals(false,roleJpa.isAnonymous());

		try {
			userDao.addRole(nom, "BBB2");
			fail("Error");
		}catch(ErrorJpaException e){
			assertEquals("Ce role est déjà présent dans la base",e.getMessage());
		}
	}

	@Test
	public void findRoleByNomOK() throws Exception {
		RoleJpa roleJpa;
		final String nom = "AAA2";

		roleJpa=userDao.findRoleByNom(nom);
		assertNull(roleJpa);

		userDao.addRole(nom,"BBB2");

		roleJpa=userDao.findRoleByNom(nom);
		assertNotNull(roleJpa);
	}

	@Test
	public void addUserOK() throws Exception {
		UserJpa userJpa;
		final String login="test1";

		userJpa=userDao.findUserByLogin(login);
		assertNull(userJpa);

		userJpa=new UserJpa();
		userJpa.setLogin(login);
		userJpa.setDesactive(false);
		userJpa.setNom("AAAA");
		userJpa.setPrenom("BBBB");
		userJpa.setPassword("DDDD");
		userJpa.setRole(testFixture.getRole("Role1"));
		userDao.addUser(userJpa);

		userJpa=userDao.findUserByLogin(login);
		assertNotNull(userJpa);
		assertEquals(login,userJpa.getLogin());
		assertEquals("AAAA",userJpa.getNom());
		assertEquals("BBBB",userJpa.getPrenom());
		assertEquals("DDDD",userJpa.getPassword());
	}

	@Test
	public void addUser2OK() throws Exception {
		UserJpa userJpa;
		final String login="Test2";

		userJpa=userDao.findUserByLogin(login);
		assertNull(userJpa);

		userJpa=new UserJpa();
		userJpa.setLogin(login);
		userJpa.setDesactive(false);
		userJpa.setNom("AAAA2");
		userJpa.setPrenom("BBBB2");
		userJpa.setPassword("DDDD2");
		userJpa.setRole(testFixture.getRole("Role2"));
		userDao.addUser(userJpa);

		userJpa=userDao.findUserByLogin(login);
		assertNotNull(userJpa);
		assertEquals(login,userJpa.getLogin());
		assertEquals("AAAA2",userJpa.getNom());
		assertEquals("BBBB2",userJpa.getPrenom());
		assertEquals("DDDD2",userJpa.getPassword());
	}

	@Test
	public void addUserKO() throws Exception {
		UserJpa userJpa;
		final String login="Test3";

		userJpa=new UserJpa();
		userJpa.setLogin(login);
		userJpa.setDesactive(false);
		userJpa.setNom("AAAA2");
		userJpa.setPrenom("BBBB2");
		userJpa.setPassword("DDDD2");
		userJpa.setRole(testFixture.getRole("Role3"));
		userDao.addUser(userJpa);

		userJpa=new UserJpa();
		userJpa.setLogin(login);
		userJpa.setDesactive(false);
		userJpa.setNom("AAAA4");
		userJpa.setPrenom("BBBB5");
		userJpa.setPassword("DDDD6");
		userJpa.setRole(testFixture.getRole("Role2"));
		try {
			userDao.addUser(userJpa);
			fail("Error");
		}catch(ErrorJpaException e){
			assertEquals("Un autre utilisateur a ce login",e.getMessage());
		}

	}

	@Test
	public void findUserOK() throws Exception {
		UserJpa userJpa;
		final String login="Test4";

		userJpa=userDao.findUserByLogin(login);
		assertNull(userJpa);

		userJpa=new UserJpa();
		userJpa.setLogin(login);
		userJpa.setDesactive(false);
		userJpa.setNom("aaaa7");
		userJpa.setPrenom("BBBB7");
		userJpa.setPassword("DDDD7");
		userJpa.setRole(testFixture.getRole("Role4"));

		userDao.addUser(userJpa);

		userJpa=userDao.findUserByLogin(login);
		assertNotNull(userJpa);
		assertEquals(login,userJpa.getLogin());
		assertEquals("aaaa7",userJpa.getNom());
		assertEquals("BBBB7",userJpa.getPrenom());
		assertEquals("DDDD7",userJpa.getPassword());
	}

}