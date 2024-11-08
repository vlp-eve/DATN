package com.poly.datn.Repository;


import com.poly.datn.Entity.User.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {


}
