package fr.deroffal.user.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.deroffal.user.entity.UserEntity;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long>{

	UserEntity findByLogin(final String login);

}
