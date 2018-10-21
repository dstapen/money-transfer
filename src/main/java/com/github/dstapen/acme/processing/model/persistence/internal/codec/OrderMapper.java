package com.github.dstapen.acme.processing.model.persistence.internal.codec;

import com.github.dstapen.acme.processing.model.Order;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import static com.github.dstapen.acme.processing.model.Order.*;

public enum OrderMapper implements Mapper<Order> {

    ORDER_MAPPER;

    @Override
    public Order map(ResultSet rs) throws Exception {
        final String id = rs.getObject("id").toString();
        final Kind kind = Kind.valueOf(rs.getString("kind"));
        final long version = rs.getLong("version");
        final String fromAccountId = rs.getString("from_account_id");
        final String toAccountId = rs.getString("to_account_id");
        final BigDecimal amount = rs.getBigDecimal("amount");
        final State state = State.valueOf(rs.getString("state"));
        final String description = rs.getString("description");
        final String note = rs.getString("note");
        final LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
        return newOrder(id, kind, version, fromAccountId, toAccountId, amount, state, description, note, created);
    }
}
