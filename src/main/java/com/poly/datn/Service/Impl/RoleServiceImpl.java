package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.User.Role;
import com.poly.datn.Repository.RoleRepository;
import com.poly.datn.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role findById(Long id) {
        return roleRepository.getReferenceById(id);
    }
}

