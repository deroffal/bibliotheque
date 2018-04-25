package fr.deroffal.portail.authentification.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.deroffal.portail.authentification.entity.UserEntity;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long>{

	@EntityGraph(attributePaths = "roles")
	UserEntity findByLogin(final String login);

}
