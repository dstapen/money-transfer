package com.github.dstapen.acme.processing.model.persistence.internal.codec;

import com.github.dstapen.acme.processing.model.Transaction;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.UUID;

import static com.github.dstapen.acme.processing.model.Transaction.newTransaction;

public enum TransactionMapper implements Mapper<Transaction> {
    TRANSACTION_MAPPER;

    @Override
    @SuppressWarnings("unchecked")
    public Transaction map(ResultSet rs) throws Exception {
        final UUID id = (UUID) rs.getObject("id");
        final UUID ownerId = (UUID) rs.getObject("order_id");
        final String accountId = rs.getString("account_id");
        final String fromAccountId = rs.getString("from_account_id");
        final String toAccountId = rs.getString("to_account_id");
        final BigDecimal amount = rs.getBigDecimal("amount");
        final String description = rs.getString("description");
        final String note = rs.getString("note");
        return newTransaction(id, ownerId, accountId, fromAccountId, toAccountId, amount, description, note);

    }
}
