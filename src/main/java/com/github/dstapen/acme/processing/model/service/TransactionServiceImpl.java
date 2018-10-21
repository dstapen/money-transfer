package com.github.dstapen.acme.processing.model.service;


import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.Transaction;
import com.github.dstapen.acme.processing.model.persistence.plumbering.DAOFactory;
import com.github.dstapen.acme.processing.model.service.internal.GenericService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
@SuppressWarnings("unused")
public final class TransactionServiceImpl extends GenericService implements TransactionService {
    private static final Logger LOG = getLogger(TransactionServiceImpl.class);

    @Inject
    public TransactionServiceImpl(DAOFactory daoFactory) {
        super(LOG, daoFactory);
    }

    @Override
    public List<Transaction> fetchAllTransactions() {
        return executeInTransaction(dao -> dao.transactionDAO().fetchAll());
    }

    @Override
    public Optional<Map.Entry<Account, BigDecimal>> balance(String accountId) {
        return executeInTransaction(dao -> dao.transactionDAO().calculateBalance(accountId));
    }

    @Override
    public List<Map.Entry<Account, BigDecimal>> balances() {
        return executeInTransaction(dao -> dao.transactionDAO().calculateBalances());
    }
}
