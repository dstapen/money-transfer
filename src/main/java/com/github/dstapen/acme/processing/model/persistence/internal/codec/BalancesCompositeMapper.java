package com.github.dstapen.acme.processing.model.persistence.internal.codec;

import com.github.dstapen.acme.processing.model.Account;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.AbstractMap;
import java.util.Map;

import static com.github.dstapen.acme.processing.model.persistence.internal.codec.AccountMapper.ACCOUNT_MAPPER;

public enum BalancesCompositeMapper implements Mapper<Map.Entry<Account, BigDecimal>> {
    BALANCES_COMPOSITE_MAPPER;

    @Override
    public Map.Entry<Account, BigDecimal> map(ResultSet rs) throws Exception {
        Account account = ACCOUNT_MAPPER.map(rs);
        BigDecimal balance = rs.getBigDecimal("balance");
        return new AbstractMap.SimpleImmutableEntry<>(account, balance);
    }
}
