package com.datn.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datn.entity.Account;
import com.datn.entity.Role;
import com.datn.entity.User1;

import com.datn.repository.UserRepository;
import com.datn.service.AccountService;
import com.datn.service.RoleService;
import com.datn.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	@Lazy
	private UserService userService;
	
	@Autowired
	@Lazy
	private AccountService accountService;
	
	@Autowired
	@Lazy
	private RoleService roleService;

	@Override
	public User1 save(User1 entity) {
		return userRepository.save(entity);
	}
	
	@Override
	public User1 findByUsernameUser(String username) {
		return userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
	

	@Override
	@Transactional
	public void registerUser(User1 user) {
		
		user.setAvata("123");
		User1 saveUser = userService.save(user);
		
		
		Role role = roleService.findById("CUST").orElse(new Role("CUST","Customer", null));
		
		
		Account account = new Account();
		account.setUser(saveUser);
		account.setRole(role);
		accountService.save(account);
	}
	
}
