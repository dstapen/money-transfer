package com.github.dstapen.acme.processing.model.persistence.internal.codec;

import com.github.dstapen.acme.processing.model.Account;

import java.math.BigDecimal;
import java.sql.ResultSet;

import static com.github.dstapen.acme.processing.model.Account.newAccount;

public enum AccountMapper implements Mapper<Account> {

    ACCOUNT_MAPPER;

    @Override
    public Account map(ResultSet rs) throws Exception {
        final String id = rs.getObject("id").toString();
        final String ownerId = rs.getObject("owner_id").toString();
        final BigDecimal initialBalance = rs.getBigDecimal("initial_balance");
        final String currencyCode = rs.getString("currency");
        final String description = rs.getString("description");
        return newAccount(id, ownerId, initialBalance, currencyCode, description);
    }
}
