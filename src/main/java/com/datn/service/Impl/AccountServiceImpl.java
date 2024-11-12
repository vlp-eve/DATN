package com.datn.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.entity.Account;
import com.datn.repository.AccountRepository;
import com.datn.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account  save(Account entity) {
		return accountRepository.save(entity);
	}
	
	
}
