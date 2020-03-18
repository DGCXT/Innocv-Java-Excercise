package com.david.innocv.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.david.innocv.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findById(UUID id);
	
	List<UserEntity> findByFirstName(String firstName);
	
	List<UserEntity> findByLastName(String lastName);
	
	Optional<UserEntity> existsById(UUID id);
}
