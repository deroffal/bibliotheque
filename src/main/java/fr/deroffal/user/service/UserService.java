package fr.deroffal.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.deroffal.user.dao.UserDao;
import fr.deroffal.user.dto.UserDto;
import fr.deroffal.user.entity.UserEntity;
import fr.deroffal.user.mapping.UserMapper;

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

	@Override
	public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
		final UserEntity user = getByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("Login " + login + " non trouvé!");
		}
		return User.withUsername(user.getLogin()).password(user.getPassword()).roles("USER").build();//TODO ajouter les rôles en base!
	}
}
