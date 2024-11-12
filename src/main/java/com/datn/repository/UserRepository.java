package com.datn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.entity.User1;

public interface UserRepository extends JpaRepository<User1, Long>{
	Optional<User1> findByUsername(String username);
}
