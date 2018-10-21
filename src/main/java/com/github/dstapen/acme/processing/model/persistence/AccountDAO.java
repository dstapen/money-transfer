package com.github.dstapen.acme.processing.model.persistence;

import com.github.dstapen.acme.processing.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountDAO {
    String SQL_FETCH_ALL = "SELECT * FROM rt_account";
    String SQL_FIND_ONE = "SELECT * FROM rt_account where id = ?";
    String SQL_INSERT_ONE = "INSERT INTO rt_account(id, owner_id, initial_balance, currency, description) VALUES(?, ?, ?, ?, ?)";


    List<Account> findAll();

    Optional<Account> findOne(UUID id);

    String insertOne(Account anAccount);
}
