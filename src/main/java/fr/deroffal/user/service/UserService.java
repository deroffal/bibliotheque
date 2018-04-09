package fr.deroffal.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.deroffal.user.dao.UserDao;
import fr.deroffal.user.dto.UserDto;
import fr.deroffal.user.entity.UserEntity;
import fr.deroffal.user.mapping.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private UserMapper userMapper;

	public UserEntity getByLogin(final String login){
		return dao.findByLogin(login);
	}

	public UserDto createUser(final UserDto user){
		final UserEntity userEntity = userMapper.toEntityAndEncorePassword(user);
		return userMapper.toDto(dao.save(userEntity));
	}
}
