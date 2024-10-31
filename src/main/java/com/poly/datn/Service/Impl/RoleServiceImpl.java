package com.poly.datn.Service.Impl;

import com.poly.datn.Repository.RoleRepository;
import com.poly.datn.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

