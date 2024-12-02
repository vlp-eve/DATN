package com.poly.datn.Service;

import com.poly.datn.Entity.User.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public void deleteRole(String id);
    Optional<Role> findById(String id);
    public List<Role> findAll();
}
