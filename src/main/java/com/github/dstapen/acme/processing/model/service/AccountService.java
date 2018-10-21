package com.github.dstapen.acme.processing.model.service;


import com.github.dstapen.acme.processing.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    String provideAccount(Account anAccount);

    Optional<Account> findOne(String id);

    List<Account> findAll();
}
