package com.datn.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.entity.Role;
import com.datn.repository.RoleRepository;
import com.datn.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<Role> findById(String id) {
		return roleRepository.findById(id);
	}

	
	
	
}
