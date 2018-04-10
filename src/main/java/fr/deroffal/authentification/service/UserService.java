package fr.deroffal.authentification.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.deroffal.authentification.dao.UserDao;
import fr.deroffal.authentification.dto.UserDto;
import fr.deroffal.authentification.entity.UserEntity;
import fr.deroffal.authentification.mapping.UserMapper;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao dao;

	@Autowired
	private UserMapper userMapper;

	public UserEntity getByLogin(final String login) {
		return dao.findByLogin(login);
	}

	public UserDto createUser(final UserDto user) {
		final UserEntity userEntity = userMapper.toEntityAndEncorePassword(user);
		return userMapper.toDto(dao.save(userEntity));
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
		final UserEntity user = getByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("Login " + login + " non trouvé!");
		}
		return User.withUsername(user.getLogin()).password(user.getPassword()).roles(user.getRolesAsStrings()).build();
	}
}
