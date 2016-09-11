package org.rss.db.dao;

import org.rss.db.dao.jpa.RoleJpa;
import org.rss.db.dao.jpa.UserJpa;

/**
 * Created by Alain on 11/09/2016.
 */
public interface IUserDao {

	public UserJpa addUser(UserJpa userJpa) throws ErrorJpaException;

	public UserJpa findUserByLogin(String login) throws ErrorJpaException;

	public RoleJpa addRole(String nom, String description) throws ErrorJpaException;

	public RoleJpa findRoleByNom(String nom) throws ErrorJpaException;

}
