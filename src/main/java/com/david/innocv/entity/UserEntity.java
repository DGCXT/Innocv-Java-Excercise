package com.david.innocv.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user")
public class UserEntity {

	@Id
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@Column(name="first_name", nullable=false)
	@NotBlank(message = "First name is mandatory")
	@Max(255)
	private String firstName;
	
	@Column(name="last_name", nullable=false)
	@NotBlank(message = "Last name is mandatory")
	@Max(255)
	private String lastName;
	
	@Column(unique=true, nullable=false)
	@NotBlank(message = "Email is mandatory")
	@Email
	private String email;
	
	public UserEntity() {}
	
	public UserEntity(String firstName, String lastName, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
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
