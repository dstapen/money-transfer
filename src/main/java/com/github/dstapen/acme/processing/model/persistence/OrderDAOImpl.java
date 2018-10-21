package com.github.dstapen.acme.processing.model.persistence;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.Order;
import com.github.dstapen.acme.processing.model.persistence.internal.GenericDAO;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.model.persistence.internal.codec.OrderMapper.ORDER_MAPPER;


public final class OrderDAOImpl extends GenericDAO<Order> implements OrderDAO {
    private static final Logger LOG = getLogger(OrderDAOImpl.class);

    public OrderDAOImpl(Connection aConnection) {
        super(LOG, aConnection, ORDER_MAPPER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> findAll() {
        return fetch(SQL_FETCH_ALL);
    }

    @Override
    public List<Order> orders() {
        return fetch(SQL_ORDERS);
    }

    @Override
    public List<Order> someOrders() {
        return fetch(SQL_SOME_ORDERS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String insertOne(Order anOrder) {
        UUID uuid = ofNullable(anOrder.id()).map(UUID::fromString).orElseGet(UUID::randomUUID);
        upsertOne(SQL_INSERT_ONE, ps -> {
            LOG.info("\nDR {} CR {} amount {}", anOrder.fromAccountId(), anOrder.toAccountId(), anOrder.amount());
            ps.setObject(1, uuid);
            ps.setString(2, anOrder.kind().toString());
            ps.setLong(3, anOrder.version());
            ps.setString(4, anOrder.fromAccountId());
            ps.setString(5, anOrder.toAccountId());
            ps.setBigDecimal(6, anOrder.amount());
            ps.setString(7, anOrder.state().toString());
            ps.setString(8, anOrder.description());
            ps.setString(9, anOrder.note());
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
        });
        LOG.info("inserted order key {}", uuid);
        return uuid.toString();
    }
}

