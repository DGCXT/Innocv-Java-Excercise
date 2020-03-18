package com.david.innocv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.innocv.entity.UserEntity;
import com.david.innocv.exceptions.ResourceNotFoundException;
import com.david.innocv.presentation.UserDTO;
import com.david.innocv.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<UserDTO> findAll()
	{
		List<UserDTO> users = new ArrayList<UserDTO>();
		userRepository.findAll().forEach(u -> users.add(UserDTO.fromUserEntity(u)));
		return users;
	}
	
	public UserDTO findById(String id) throws ResourceNotFoundException
	{
		Optional<UserEntity> user = userRepository.findById(UUID.fromString(id));
		if (!user.isPresent())
			throw new ResourceNotFoundException(String.format("User with id %s not found.", id));
		return UserDTO.fromUserEntity(user.get());
	}
	
	public List<UserDTO> findByFirstName(String firstName) throws ResourceNotFoundException
	{
		List<UserDTO> users = new ArrayList<UserDTO>();
		userRepository.findByFirstName(firstName).forEach(u -> users.add(UserDTO.fromUserEntity(u)));
		if (users.isEmpty())
			throw new ResourceNotFoundException(String.format("No user with first name %s was found.", firstName));
		return users;
	}
	
	public UserDTO createUser(UserDTO userDTO)
	{
		UserEntity userEntity = UserDTO.toUserEntity(userDTO);
		return UserDTO.fromUserEntity(userRepository.save(userEntity));
	}
	
	public UserDTO updateUser(String id, UserDTO userDTO) throws ResourceNotFoundException
	{
		if (!userRepository.existsById(UUID.fromString(id)).isPresent())
			throw new ResourceNotFoundException(String.format("User with id %S not found.", id));
		UserEntity userEntity = UserDTO.toUserEntity(userDTO);
		userEntity.setId(UUID.fromString(id));
		return UserDTO.fromUserEntity(userRepository.save(userEntity));
	}
	
	public void deleteUserWithId(String id) throws ResourceNotFoundException
	{
		if (!userRepository.existsById(UUID.fromString(id)).isPresent())
			throw new ResourceNotFoundException(String.format("User with id %S not found.", id));
		UserEntity user = new UserEntity();
		user.setId(UUID.fromString(id));
		userRepository.delete(user);
	}
}