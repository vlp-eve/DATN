package com.poly.datn.Service.Impl;


import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.User.Account;
import com.poly.datn.Entity.User.Role;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Repository.AccountRepository;
import com.poly.datn.Repository.RoleRepository;
import com.poly.datn.Repository.UserRepository;
import com.poly.datn.Service.AccountService;
import com.poly.datn.Service.RoleService;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private RoleService  roleService;

    @Autowired
    private AccountService accountService;

    @Override
    public Page<User> findAllUserCust(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 2);
        return userRepository.findUsersByRoleNameCust(pageable);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Người dùng đã tồn tại với email này: " + user.getEmail());
        }
        User newUser = userRepository.save(user);

        // Tạo tài khoản cho người dùng
        Account account = new Account();
        account.setUser(newUser);
        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Vai trò không tồn tại")); // Kiểm tra nếu không tìm thấy

        account.setRole(role); // Thiết lập vai trò
        account.setUser(newUser);

        // Lưu tài khoản
        accountRepository.save(account);
        return newUser;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        // Tìm người dùng hiện có theo ID
        return userRepository.findById(id).map(user -> {
            // Cập nhật các thuộc tính từ đối tượng updatedUser
            user.setFullname(updatedUser.getFullname());
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
            user.setPhone(updatedUser.getPhone());
            user.setGender(updatedUser.getGender());
            user.setPassword(updatedUser.getPassword());
            // Lưu người dùng đã cập nhật
            return userRepository.save(user);
        });
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User findByUsernameUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    @Override
    @Transactional
    public void registerUser(User user) {

        user.setAvata("123");
        User saveUser = userService.save(user);


        Role role = roleService.findById("CUST").orElse(new Role("CUST","Customer", null));


        Account account = new Account();
        account.setUser(saveUser);
        account.setRole(role);
        accountService.save(account);
    }

    @Override
    public List<User> findUserWithNonDelete() {
        return userRepository.findByIsDeletedFalse();
    }

    @Override
    public String getRoleName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Lấy username từ authentication

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getAccounts().isEmpty()) {
                Account account = user.getAccounts().get(0); // Lấy account đầu tiên
                return account.getRole().getRoleName(); // Lấy tên vai trò từ Role
            }
        }
        return "Chưa có vai trò"; // Nếu không tìm thấy vai trò
    }

    @Override
    public boolean existsByEmail(String email) {
        User probe = new User();
        probe.setEmail(email);
        Example<User> example = Example.of(probe);
        return userRepository.exists(example);
    }

    @Override
    public Page<User> findAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 2);
        return userRepository.findAll(pageable);
    }
    @Override
    public boolean existsByPhone(String phone) {
        User probe = new User();
        probe.setPhone(phone);
        Example<User> example = Example.of(probe);
        return userRepository.exists(example);
    }

    // Kiểm tra trùng lặp username
    @Override
    public boolean existsByUsername(String username) {
        User probe = new User();
        probe.setUsername(username);
        Example<User> example = Example.of(probe);
        return userRepository.exists(example);
    }

    @Override
    public Page<User> searchUser(String keyword, Integer pageNo) {
        List<User> list = this.searchUserFullName(keyword);

        Pageable pageable = PageRequest.of(pageNo-1, 2);

        Integer start = (int) pageable.getOffset();

        Integer end = (int) ((pageable.getOffset()+pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<User>(list, pageable, searchUserFullName(keyword).size());
    }
    @Override
    public Page<User> searchUserCust(String keyword, Integer pageNo) {
        List<User> list = this.findUsersByFullnameAndRoleCUST(keyword);

        Pageable pageable = PageRequest.of(pageNo-1, 2);

        Integer start = (int) pageable.getOffset();

        Integer end = (int) ((pageable.getOffset()+pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<User>(list, pageable, searchUserFullName(keyword).size());
    }
    @Override
    public List<User> searchUserFullName(String keyword) {
        // TODO Auto-generated method stub
        return userRepository.searchUserFullname(keyword);
    }

    @Override
    public List<User> findUsersByFullnameAndRoleCUST(String keyword) {
        return userRepository.findUsersByFullnameAndRoleCUST(keyword);
    }

    @Override
    public void softDeleteUser(Long userId) {
        User user = userRepository.findUserById(userId);
        user.setIsDeleted(IsDelete.DELETED.getValue());
        userRepository.save(user);
    }
}

