package fr.deroffal.portail.api.authentification.utilisateur;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long> {

	@EntityGraph(attributePaths = "roles")
	UserEntity findByLogin(final String login);

	boolean existsByLogin(final String login);

}
