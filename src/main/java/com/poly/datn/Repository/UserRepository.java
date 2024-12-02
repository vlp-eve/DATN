package com.poly.datn.Repository;


import com.poly.datn.Entity.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // Tìm người dùng theo email

    User getById(Long Id);

    Optional<User> findByUsername(String username);
    List<User> findByIsDeletedFalse();
    User findUserById(Long id);

    @Query("SELECT u FROM User u JOIN u.accounts a JOIN a.role r WHERE r.id = 'CUST'")
    Page<User> findUsersByRoleNameCust(Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.username LIKE %?1%")
    List<User> searchUserFullname(String keyword);
    @Query("SELECT u FROM User u JOIN u.accounts a JOIN a.role r WHERE r.id = 'CUST' AND u.username LIKE %?1%")
    List<User> findUsersByFullnameAndRoleCUST(String fullname);
}

