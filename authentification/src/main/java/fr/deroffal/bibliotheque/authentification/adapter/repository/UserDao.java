package fr.deroffal.bibliotheque.authentification.adapter.repository;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.adapter.repository.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserDao extends CrudRepository<UserEntity, Long> {

	@EntityGraph(attributePaths = "roles")
	Optional<UserEntity> findByLogin(final String login);

	boolean existsByLogin(final String login);

}
