package com.poly.datn.Service;


import com.poly.datn.Entity.User.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    public List<Account> getAllAccounts();
    public Account addAccount(Account account);
    public Optional<Account> getAccountById(Long id);

    public void deleteAccount(Long id);
    public Account updateAccount(Long accountId, Account accountDetails);
    Account save(Account entity);
}
