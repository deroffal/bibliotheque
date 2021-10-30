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

    private final UserDao dao;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findByLogin(final String login) {
        return dao.findByLogin(login).map(userMapper::toDto);
    }

    @Override
    public boolean existsByLogin(final String login) {
        return dao.existsByLogin(login);
    }

    @Override
    public UserDto create(final UserDto userDto) {
        final UserEntity userEntity = userMapper.toEntityAndEncorePassword(userDto);
        final UserEntity user = dao.save(userEntity);
        return userMapper.toDto(user);
    }
}
