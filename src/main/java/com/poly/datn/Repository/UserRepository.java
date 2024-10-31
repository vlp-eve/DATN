package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // Tìm người dùng theo email
}

