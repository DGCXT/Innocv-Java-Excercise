package com.david.innocv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.innocv.controllers.UserController;
import com.david.innocv.entity.UserEntity;
import com.david.innocv.exceptions.ResourceNotFoundException;
import com.david.innocv.presentation.UserDTO;
import com.david.innocv.repositories.UserRepository;

@Service
public class UserService {

	private static final Logger LOGGER=LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserDTO> findAll()
	{
		List<UserDTO> users = new ArrayList<UserDTO>();
		userRepository.findAll().forEach(u -> users.add(UserDTO.fromUserEntity(u)));
		LOGGER.info("Found users {}", users);
		return users;
	}
	
	public UserDTO findById(String id) throws ResourceNotFoundException
	{
		Optional<UserEntity> user = userRepository.findById(UUID.fromString(id));
		if (!user.isPresent())
			throw new ResourceNotFoundException(String.format("User with id %s not found.", id));
		LOGGER.info("Found user {}", user.get());
		return UserDTO.fromUserEntity(user.get());
	}
	
	public List<UserDTO> findByFirstName(String firstName) throws ResourceNotFoundException
	{
		List<UserDTO> users = new ArrayList<UserDTO>();
		userRepository.findByFirstName(firstName).forEach(u -> users.add(UserDTO.fromUserEntity(u)));
		if (users.isEmpty())
			throw new ResourceNotFoundException(String.format("No user with first name %s was found.", firstName));
		LOGGER.info("Found user {}", users);
		return users;
	}
	
	public List<UserDTO> findByLastName(String lastName) throws ResourceNotFoundException
	{
		List<UserDTO> users = new ArrayList<UserDTO>();
		userRepository.findByLastName(lastName).forEach(u -> users.add(UserDTO.fromUserEntity(u)));
		if (users.isEmpty())
			throw new ResourceNotFoundException(String.format("No user with last name %s was found.", lastName));
		LOGGER.info("Found user {}", users);
		return users;
	}
	
	public UserDTO createUser(UserDTO userDTO)
	{
		UserEntity userEntity = UserDTO.toUserEntity(userDTO);
		LOGGER.info("Created user {}", userEntity);
		return UserDTO.fromUserEntity(userRepository.save(userEntity));
	}
	
	public UserDTO updateUser(String id, UserDTO userDTO) throws ResourceNotFoundException
	{
		if (!userRepository.existsById(UUID.fromString(id)).isPresent())
			throw new ResourceNotFoundException(String.format("User with id %S not found.", id));
		UserEntity userEntity = UserDTO.toUserEntity(userDTO);
		userEntity.setId(UUID.fromString(id));
		userEntity = userRepository.save(userEntity);
		LOGGER.info("Updated user {}", userEntity);
		return UserDTO.fromUserEntity(userEntity);
	}
	
	public void deleteUser(String id) throws ResourceNotFoundException
	{
		if (!userRepository.existsById(UUID.fromString(id)).isPresent())
			throw new ResourceNotFoundException(String.format("User with id %S not found.", id));
		UserEntity user = new UserEntity();
		user.setId(UUID.fromString(id));
		userRepository.delete(user);
		LOGGER.info("Deleted user {}", user);
	}
}