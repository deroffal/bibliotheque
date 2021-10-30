package fr.deroffal.bibliotheque.authentification.utilisateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long> {

	@EntityGraph(attributePaths = "roles")
	Optional<UserEntity> findByLogin(final String login);

	boolean existsByLogin(final String login);

}
