package fr.deroffal.bibliotheque.authentification.adapter.repository;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.adapter.repository.mapping.UserMapper;
import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryAdapterImpl implements UserRepositoryAdapter {

    private final UserRepository dao;
    private final UserMapper userMapper;

    @Override
    public Optional<Utilisateur> findByUsername(final String username) {
        return dao.findByUsername(username).map(userMapper::toDto);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return dao.existsByUsername(username);
    }

    @Override
    public Utilisateur create(final Utilisateur utilisateur) {
        final UserEntity userEntity = userMapper.toEntityAndEncorePassword(utilisateur);
        final UserEntity user = dao.save(userEntity);
        return userMapper.toDto(user);
    }
}
