package com.coachar.fitness_tracking_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coachar.fitness_tracking_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
