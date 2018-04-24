package fr.deroffal.portail.authentification.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.deroffal.portail.authentification.exception.UserNotExistingException;
import fr.deroffal.portail.authentification.dao.UserDao;
import fr.deroffal.portail.authentification.dto.UserDto;
import fr.deroffal.portail.authentification.entity.UserEntity;
import fr.deroffal.portail.authentification.mapping.UserMapper;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao dao;

	@Autowired
	private UserMapper userMapper;

	public UserEntity getByLogin(final String login) {
		final UserEntity user = dao.findByLogin(login);
		if(user == null){
			throw new UserNotExistingException(login);
		}
		return user;
	}

	public UserDto createUser(final UserDto user) {
		final UserEntity userEntity = userMapper.toEntityAndEncorePassword(user);
		return userMapper.toDto(dao.save(userEntity));
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(final String login) {
		final UserEntity user = dao.findByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("Login " + login + " non trouvé!");
		}
		return User.withUsername(user.getLogin()).password(user.getPassword()).roles(user.getRolesAsStrings()).build();
	}
}
