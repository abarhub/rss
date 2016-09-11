package org.rss.db.dao;

import com.google.common.base.Preconditions;
import org.rss.beans.OutilsGeneriques;
import org.rss.db.dao.jpa.RoleJpa;
import org.rss.db.dao.jpa.UserJpa;
import org.rss.db.dao.repository.RoleRepository;
import org.rss.db.dao.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alain on 11/09/2016.
 */
@Repository
@Transactional
public class UserDao implements IUserDao {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	//@PersistenceContext
	//private EntityManager em;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public UserJpa addUser(UserJpa userJpa) throws ErrorJpaException {
		Preconditions.checkNotNull(userJpa);
		Preconditions.checkArgument(userJpa.getId()==null);

		if(findUserByLogin(userJpa.getLogin())!=null) {
			throw new ErrorJpaException("Un autre utilisateur a ce login");
		}
		return userRepository.save(userJpa);
	}

	public UserJpa findUserByLogin(String login) throws ErrorJpaException {
		UserJpa tmp;
		Preconditions.checkArgument(!OutilsGeneriques.vide(login));
		tmp=new UserJpa();
		tmp.setLogin(login);
		Example<UserJpa> example;
		example=Example.of(tmp);
		List<UserJpa> list=userRepository.findAll(example);
		if(list==null||list.isEmpty()){
			return null;
		} else if(list.size()==1){
			return list.get(0);
		} else {
			throw new ErrorJpaException("Plusieurs users avec ce login");
		}
	}

	public RoleJpa addRole(String nom,String description) throws ErrorJpaException {
		RoleJpa roleJpa;

		Preconditions.checkArgument(!OutilsGeneriques.vide(nom));
		Preconditions.checkArgument(!OutilsGeneriques.vide(description));

		if(findRoleByNom(nom)!=null){
			throw new ErrorJpaException("Ce role est déjà présent dans la base");
		}

		roleJpa=new RoleJpa();
		roleJpa.setNom(nom);
		roleJpa.setAdmin(false);
		roleJpa.setAnonymous(false);
		roleJpa.setDescription(description);

		return roleRepository.save(roleJpa);
	}

	public RoleJpa findRoleByNom(String nom) throws ErrorJpaException {
		RoleJpa roleJpa;

		Preconditions.checkArgument(!OutilsGeneriques.vide(nom));

		Example<RoleJpa> example;
		roleJpa=new RoleJpa();
		roleJpa.setNom(nom);
		example=Example.of(roleJpa);
		List<RoleJpa> list=roleRepository.findAll(example);
		if(list==null||list.isEmpty()){
			return null;
		} else if(list.size()==1){
			return list.get(0);
		} else {
			throw new ErrorJpaException("Ce role est présent plusieurs fois dans la base");
		}
	}
}
