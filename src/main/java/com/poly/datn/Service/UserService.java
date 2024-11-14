package com.poly.datn.Service;


import com.poly.datn.Entity.User.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAllUsers();
    public User addUser(User user);
    public Optional<User> getUserById(Long id);
    public void deleteUser(Long id);
    public Optional<User> updateUser(Long id, User updatedUser);

    User save(User entity);

    User findByUsernameUser(String username);

    void registerUser(User user);
}
