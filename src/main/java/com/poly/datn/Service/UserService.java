package com.poly.datn.Service;


import com.poly.datn.Entity.User.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public interface UserService {
    public List<User> getAllUsers();
    public User addUser(User user);
    public Optional<User> getUserById(Long id);
    public void deleteUser(Long id);
    public Optional<User> updateUser(Long id, User updatedUser);

    User save(User entity);

    User findByUsernameUser(String username);

    void registerUser(User user);

    List<User> findUserWithNonDelete();
    String getRoleName();

    public boolean existsByEmail(String email);

    public boolean existsByPhone(String phone);
    public boolean existsByUsername(String username);
    public Page<User> findAllUserCust(Integer pageNo);
    public Page<User> searchUser(String keyword, Integer pageNo);
    public Page<User> searchUserCust(String keyword, Integer pageNo);
    public void softDeleteUser(Long userId);
    public Page<User> findAll(Integer pageNo);
    public List<User> searchUserFullName(String keyword);
    public List<User> findUsersByFullnameAndRoleCUST(String keyword);

}
