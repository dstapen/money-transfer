package com.github.dstapen.acme.processing.model.persistence;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.Transaction;
import com.github.dstapen.acme.processing.model.persistence.internal.GenericDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.model.persistence.internal.codec.BalancesCompositeMapper.BALANCES_COMPOSITE_MAPPER;
import static com.github.dstapen.acme.processing.model.persistence.internal.codec.TransactionMapper.TRANSACTION_MAPPER;


public final class TransactionDAOImpl extends GenericDAO<Transaction> implements TransactionDAO {
    private static final Logger LOG = getLogger(TransactionDAOImpl.class);

    public TransactionDAOImpl(Connection aConnection) {
        super(LOG , aConnection, TRANSACTION_MAPPER);
    }


    @Override
    public List<Transaction> fetchAll() {
        LOG.info("sql {}", SQL_FETCH_ALL);
        return fetch(SQL_FETCH_ALL);
    }

    @Override
    public Optional<Map.Entry<Account, BigDecimal>> calculateBalance(String anAccountId) {
        UUID accId = UUID.fromString(anAccountId);
        return findCustom(SQL_CALCULATE_BALANCE, ps -> {
            ps.setObject(1, accId);
        }, BALANCES_COMPOSITE_MAPPER);

    }

    @Override
    public List<Map.Entry<Account, BigDecimal>> calculateBalances() {
        return fetch(SQL_CALCULATE_BALANCES, EMPTY_VISITOR, BALANCES_COMPOSITE_MAPPER);
    }

    @Override
    public UUID insertOne(Transaction aTransaction) {
        UUID uuid = ofNullable(aTransaction.id()).orElseGet(UUID::randomUUID);
        LOG.info("\nacc {}\nDR {} CR {} amount {}", aTransaction.accountId(), aTransaction.fromAccountId(), aTransaction.toAccountId(), aTransaction.amount());

        upsertOne(SQL_INSERT_ONE, ps -> {
            ps.setObject(1, uuid);
            ps.setObject(2, aTransaction.orderId());
            ps.setString(3, aTransaction.accountId());
            ps.setString(4, aTransaction.fromAccountId());
            ps.setString(5, aTransaction.toAccountId());
            ps.setBigDecimal(6, aTransaction.amount());
            ps.setString(7, aTransaction.description());
            ps.setString(8, aTransaction.note());
        });
        LOG.info("inserted transaction key {}", uuid);
        return uuid;

    }
}


