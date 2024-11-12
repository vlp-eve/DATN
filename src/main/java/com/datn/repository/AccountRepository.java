package com.datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
