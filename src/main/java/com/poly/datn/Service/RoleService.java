package com.poly.datn.Service;

import com.poly.datn.Entity.User.Role;

import java.util.Optional;

public interface RoleService {
    public void deleteRole(Long id);
    Optional<Role> findById(String id);
}
