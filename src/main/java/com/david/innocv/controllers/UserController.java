package com.david.innocv.controllers;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.cfg.NotYetImplementedException;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public List<UserDTO> findAll()
	{
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public UserDTO findById(@PathVariable("id") @NotBlank String id) throws ResourceNotFoundException
	{
		return userService.findById(id);
	}

	@GetMapping("/firstName/{firstName}")
	public List<UserDTO> findByFirstName(@PathVariable("firstName") @NotBlank String firstName) throws ResourceNotFoundException
	{
		return userService.findByFirstName(firstName);
	}

	@GetMapping("/lastName/{lastName}")
	public void findByLastName(@PathVariable("lastName") @NotBlank String lastName)
	{
		throw new NotYetImplementedException("");
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody @NotNull UserDTO user)
	{	
		return new ResponseEntity<UserDTO>(userService.createUser(user), HttpStatus.CREATED);	
	}

	@PutMapping("/{id}")
	public UserDTO updateUser(@PathVariable("id") @NotNull String id, @RequestBody UserDTO user) throws ResourceNotFoundException
	{	
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable(value="id") String id) throws ResourceNotFoundException
	{
		userService.deleteUser(id);
		return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
	}
}
