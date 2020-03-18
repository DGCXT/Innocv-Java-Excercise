package com.david.innocv.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.david.innocv.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findById(UUID id);
	
	Optional<UserEntity> existsById(UUID id);
}
