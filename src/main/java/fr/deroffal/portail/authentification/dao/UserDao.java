package fr.deroffal.portail.authentification.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.deroffal.portail.authentification.entity.UserEntity;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long>{

	UserEntity findByLogin(final String login);

}
