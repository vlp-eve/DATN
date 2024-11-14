package com.poly.datn.Service.Impl;


import com.poly.datn.Entity.User.Account;
import com.poly.datn.Repository.AccountRepository;
import com.poly.datn.Repository.RoleRepository;
import com.poly.datn.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account addAccount(Account account) {

       return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }


    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }



    public Account updateAccount(Long accountId, Account accountDetails) {
        Account account = accountRepository.getReferenceById(accountId);
        account.setRole(accountDetails.getRole());
        return account;
    }
    @Override
    public Account  save(Account entity) {
        return accountRepository.save(entity);
    }

}

