package com.poly.datn.Repository;


import com.poly.datn.Entity.User.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.user.username LIKE %?1%")
    List<Account> searchByUsernameAccount(String keyword);

}
