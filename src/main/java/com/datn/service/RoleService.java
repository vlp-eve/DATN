package com.datn.service;

import java.util.Optional;

import com.datn.entity.Role;

public interface RoleService {

	Optional<Role> findById(String id);



}
