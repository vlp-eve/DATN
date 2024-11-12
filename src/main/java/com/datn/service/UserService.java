package com.datn.service;



import com.datn.entity.User1;

public interface UserService {

	User1 save(User1 entity);

	User1 findByUsernameUser(String username);

	void registerUser(User1 user);



}
