package com.david.innocv.services;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.david.innocv.entity.UserEntity;
import com.david.innocv.exceptions.ResourceNotFoundException;
import com.david.innocv.presentation.UserDTO;
import com.david.innocv.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	private static UserDTO davidDTO;
	
	private static UserEntity davidEntity;
	
	private static List<UserEntity> users;
	
	private static String uuidId = "6568c5a2-0795-4fea-982c-ea94f82b8829";
	
	@BeforeAll
	private static void setup()
	{
		davidDTO = new UserDTO(null, "david", "gomez", "david@email");
		davidEntity = UserDTO.toUserEntity(davidDTO);
		davidEntity.setId(UUID.fromString(uuidId));
		users = new ArrayList<UserEntity>();
		users.add(davidEntity);
	}
	
	@Test
	@DisplayName("Create a new user")
	public void testCreateUser()
	{
		given(userRepository.save(any(UserEntity.class))).willReturn(davidEntity);
		
		UserDTO createdUser = userService.createUser(davidDTO);
		assertEquals(davidDTO, createdUser);
	}
	
	@Test
	@DisplayName("Find all users")
	public void testFindAll()
	{
		given(userRepository.findAll()).willReturn(users);
		
		List<UserDTO> users = userService.findAll();
		assertAll(
				() -> assertEquals(users.size(), 1),
				() -> assertEquals(users.get(0), davidDTO)
		);
	}
	
	@Test
	@DisplayName("Find an user by its ID")
	public void testFindUserById() throws ResourceNotFoundException
	{
		given(userRepository.findById(UUID.fromString(uuidId))).willReturn(Optional.of(davidEntity));
		
		assertEquals(userService.findById(uuidId), davidDTO);
	}
	
	@Test
	@DisplayName("Find an user given an ID not present in the DB.")
	public void testFindUserByWrongId_expectException()
	{
		String wrongUuid = "656855a2-0795-4fea-982c-ea94f82b8829";
		
		given(userRepository.findById(UUID.fromString(wrongUuid)))
		.willReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> 
			userService.findById(wrongUuid)
		);
	}
	
	@Test
	@DisplayName("Find all users with the given first name.")
	public void testFindByFirstName() throws ResourceNotFoundException
	{
		given(userRepository.findByFirstName(any(String.class))).willReturn(users);
		
		List<UserDTO> usersDTO = userService.findByFirstName("david");
		assertAll(
				() -> assertEquals(usersDTO.size(), 1),
				() -> assertEquals(usersDTO.get(0), davidDTO)
		);
	}
	
	@Test
	@DisplayName("Update an user with the given id.")
	public void testUpdateUserWithId() throws ResourceNotFoundException
	{
		given(userRepository.save(any(UserEntity.class))).willReturn(davidEntity);
		given(userRepository.existsById(any(UUID.class))).willReturn(Optional.of(davidEntity));
		
		assertEquals(userService.updateUser(uuidId, davidDTO), davidDTO);
	}
	
	@Test
	@DisplayName("Update an user with an ID not present in the DB.")
	public void testUpdateUserWithWrongId_expectException()
	{	
		String wrongUuid = "656855a2-0795-4fea-982c-ea94f82b8829";
		
		given(userRepository.existsById(any(UUID.class)))
		.willReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> 
			userService.updateUser(wrongUuid, davidDTO)
		);
	}
	
	
	@Test
	@DisplayName("Delete an user with an ID not present in the DB.")
	public void testDeleteUserWithWrongId_expectException()
	{	
		String wrongUuid = "656855a2-0795-4fea-982c-ea94f82b8829";
		
		given(userRepository.existsById(any(UUID.class)))
		.willReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> 
			userService.deleteUserWithId(wrongUuid)
		);
	}
}