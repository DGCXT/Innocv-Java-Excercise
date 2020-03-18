package com.david.innocv.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.david.innocv.presentation.UserDTO;
import com.david.innocv.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private static UserDTO david;
	private static UserDTO liza;
	private static String uuidId;
	
	private static List<UserDTO> users;
	
	@BeforeAll
	public static void setup()
	{
		uuidId="02a8bf66-13e6-4ab9-88a6-326627f195cd";
		david = new UserDTO(null, "david", "gómez", "david@mail.com");
		liza = new UserDTO(uuidId,"liza", "mish", "liza@mail.com");
		users = new ArrayList<UserDTO>();
		users.add(david);
		users.add(liza);
	}
	
	@Test
	public void testGetUserById() throws Exception
	{		
		String getUserByIdUrl = "/users/" + uuidId;
		given(userService.findById(uuidId)).willReturn(david);
		
		mockMvc.perform(get(getUserByIdUrl).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(david.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(david.getLastName()))
				.andExpect(jsonPath("$.email").value(david.getEmail()));
	}
	
	@Test
	public void testCreateUser() throws Exception
	{		
		given(userService.createUser(any(UserDTO.class))).willReturn(david);
		
		String jsonDavid = asJsonString(new UserDTO(null, "david", "gomez", "david@mail.com"));
		
		mockMvc.perform(post("/users")
		       .content(jsonDavid)
		       .contentType(MediaType.APPLICATION_JSON))
			   .andDo(print())
			   .andExpect(status().isCreated())
			   .andExpect(jsonPath("$.firstName").value(david.getFirstName()))
			   .andExpect(jsonPath("$.lastName").value(david.getLastName()))
			   .andExpect(jsonPath("$.email").value(david.getEmail()));
	}
	
	@Test
	public void testUpdateUser() throws Exception
	{
		given(userService.updateUser(any(String.class), any(UserDTO.class))).willReturn(liza);
		
		String jsonDavid = asJsonString(liza);
	    		
		mockMvc.perform(put("/users/{uuidId}", uuidId)
		       .content(jsonDavid)
		       .contentType(MediaType.APPLICATION_JSON))
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.firstName").value(liza.getFirstName()))
			   .andExpect(jsonPath("$.lastName").value(liza.getLastName()))
			   .andExpect(jsonPath("$.email").value(liza.getEmail()));
	}
	
	@Test
	public void testGetAllUsers() throws Exception
	{
		String allUsersUrl = "/users";
		given(userService.findAll()).willReturn(users);
		
		mockMvc.perform(get(allUsersUrl).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName").value(david.getFirstName()));
	}
	
	@Test
	public void testDeleteUser() throws Exception
	{
		String deleteUserUrl = "/users/{id}";
		
		mockMvc.perform(delete(deleteUserUrl, uuidId))
			   .andDo(print())
			   .andExpect(status().isNoContent());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	        return objectMapper.writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
