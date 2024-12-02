package com.poly.datn.Service;


import com.poly.datn.Entity.User.Account;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    public List<Account> getAllAccounts();
    public Account addAccount(Account account);
    public Optional<Account> getAccountById(Long id);

    public void deleteAccount(Long id);
    public Account updateAccount(Long accountId, Account accountDetails);
    Account save(Account entity);
    public List<Account> searchByUsernameAccount(String keyword);
    public Page<Account> findAll(Integer pageNo);
    public Page<Account> searchAccountKeyword(String keyword, Integer pageNo);
    public Optional<Account> findById(Long id);
}
