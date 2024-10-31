package com.poly.datn.Controller.RestController;



import com.poly.datn.Entity.User.Account;
import com.poly.datn.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private  AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<Account> getAccountByUsername(@PathVariable String username) {
//
//        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping
    public List<Account> getAllAcc() {
        return accountService.getAllAccounts();
    }
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.addAccount(account);
        return ResponseEntity.status(201).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        Optional<Account> updatedAccount = accountService.updateAccount(id, accountDetails);
        return updatedAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}

