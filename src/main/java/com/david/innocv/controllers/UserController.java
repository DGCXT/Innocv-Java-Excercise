package com.david.innocv.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.david.innocv.exceptions.ResourceNotFoundException;
import com.david.innocv.presentation.UserDTO;
import com.david.innocv.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOGGER=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;

	@GetMapping
	public List<UserDTO> findAll()
	{
		LOGGER.info("Finding all users");
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public UserDTO findById(@NotBlank @PathVariable("id") String id) throws ResourceNotFoundException
	{
		LOGGER.info("Finding user {}", id);
		return userService.findById(id);
	}

	@GetMapping("/firstName/{firstName}")
	public List<UserDTO> findByFirstName(@NotBlank @PathVariable("firstName") String firstName) throws ResourceNotFoundException
	{
		LOGGER.info("Finding users with first name {}", firstName);
		return userService.findByFirstName(firstName);
	}

	@GetMapping("/lastName/{lastName}")
	public List<UserDTO> findByLastName(@NotBlank @PathVariable("lastName") String lastName) throws ResourceNotFoundException
	{
		LOGGER.info("Finding users with last name {}", lastName);
		return userService.findByLastName(lastName);
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user)
	{	
		LOGGER.info("Creating user {}", user);
		return new ResponseEntity<UserDTO>(userService.createUser(user), HttpStatus.CREATED);	
	}

	@PutMapping("/{id}")
	public UserDTO updateUser(@NotBlank @PathVariable("id") String id, @Valid @RequestBody UserDTO user) throws ResourceNotFoundException
	{	
		LOGGER.info("Updating user {} with new values {}", id, user);
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable(value="id") String id) throws ResourceNotFoundException
	{
		LOGGER.info("Deleting user {}", id);
		userService.deleteUser(id);
		return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
	}
}
