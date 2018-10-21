package com.github.dstapen.acme.processing.model.service;

import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.Transaction;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public interface TransactionService {

    List<Transaction> fetchAllTransactions();

    Optional<Map.Entry<Account, BigDecimal>> balance(String id);

    List<Map.Entry<Account, BigDecimal>> balances();
}
