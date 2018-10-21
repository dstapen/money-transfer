package com.github.dstapen.acme.processing.model.persistence;

import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.github.dstapen.acme.processing.util.Resource.getResource;

public interface TransactionDAO {
    String SQL_FETCH_ALL = "SELECT * FROM rt_tx";
    String SQL_CALCULATE_BALANCE = getResource("/sql/balance.sql");
    String SQL_CALCULATE_BALANCES = getResource("/sql/balances.sql");
    String SQL_INSERT_ONE = "INSERT INTO rt_tx(id, order_id, account_id, from_account_id, to_account_id, amount, description, note) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


    List<Transaction> fetchAll();

    Optional<Map.Entry<Account, BigDecimal>> calculateBalance(String anAccountId);

    List<Map.Entry<Account, BigDecimal>> calculateBalances();

    UUID insertOne(Transaction transaction);
}
