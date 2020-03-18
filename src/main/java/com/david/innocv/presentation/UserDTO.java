package com.david.innocv.presentation;

import com.david.innocv.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	
	@JsonProperty
	private String id;
	
	@JsonProperty
	private String firstName;
	
	@JsonProperty
	private String lastName;
	
	@JsonProperty
	private String email;

	public UserDTO(){}
	
	public UserDTO(String id, String firstName, String lastName, String email)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public static UserDTO fromUserEntity(UserEntity userEntity)
	{
		UserDTO userDTO = new UserDTO();
		userDTO.firstName = userEntity.getFirstName();
		userDTO.lastName = userEntity.getLastName();
		userDTO.id = userEntity.getId().toString();
		userDTO.email = userEntity.getEmail();
		return userDTO;
	}
	
	public static UserEntity toUserEntity(UserDTO userDTO)
	{
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setLastName(userDTO.getLastName());
		userEntity.setEmail(userDTO.getEmail());
		return userEntity;
	}
	
	@Override
	public String toString()
	{
		return "[" + id + ", " + firstName + ", " + lastName + ", " + email + "]";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof UserDTO))
			return false;
		UserDTO otherUser = (UserDTO) o;
		return otherUser.getEmail().equals(this.email);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
