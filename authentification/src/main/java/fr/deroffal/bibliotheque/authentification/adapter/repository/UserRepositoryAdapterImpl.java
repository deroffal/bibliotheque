package fr.deroffal.bibliotheque.authentification.adapter.repository;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.adapter.repository.mapping.UserMapper;
import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryAdapterImpl implements UserRepositoryAdapter {

    private final UserRepository dao;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findByUsername(final String username) {
        return dao.findByUsername(username).map(userMapper::toDto);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return dao.existsByUsername(username);
    }

    @Override
    public UserDto create(final UserDto userDto) {
        final UserEntity userEntity = userMapper.toEntityAndEncorePassword(userDto);
        final UserEntity user = dao.save(userEntity);
        return userMapper.toDto(user);
    }
}
