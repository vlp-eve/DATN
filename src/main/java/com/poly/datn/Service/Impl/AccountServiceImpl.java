package com.poly.datn.Service.Impl;


import com.poly.datn.Entity.User.Account;
import com.poly.datn.Repository.AccountRepository;
import com.poly.datn.Repository.RoleRepository;
import com.poly.datn.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Override
    public List<Account> searchByUsernameAccount(String keyword) {
        return accountRepository.searchByUsernameAccount(keyword);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Page<Account> findAll(Integer pageNo) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pageNo-1, 3);
        return this.accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> searchAccountKeyword(String keyword, Integer pageNo) {
        List<Account> list = this.searchByUsernameAccount(keyword);

        Pageable pageable = PageRequest.of(pageNo-1, 2);

        Integer start = (int) pageable.getOffset();

        Integer end = (int) ((pageable.getOffset()+pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<Account>(list, pageable, searchByUsernameAccount(keyword).size());
    }

}

