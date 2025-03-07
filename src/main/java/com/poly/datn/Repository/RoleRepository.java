package com.poly.datn.Repository;


import com.poly.datn.Entity.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String roleName);


    Optional<Role> findById(String id);
}
