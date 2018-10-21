package com.github.dstapen.acme.processing.model.persistence;

import com.github.dstapen.acme.processing.model.Order;

import java.util.List;

import static com.github.dstapen.acme.processing.util.Resource.getResource;

public interface OrderDAO {

    String SQL_ORDERS = getResource("/sql/orders.sql");
    String SQL_SOME_ORDERS = getResource("/sql/some_orders.sql");
    String SQL_FETCH_ALL = "SELECT * FROM rt_order";
    String SQL_INSERT_ONE = "INSERT INTO rt_order(id, kind, version, from_account_id, to_account_id, amount, state, description, note, created) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    List<Order> findAll();

    List<Order> orders();

    List<Order> someOrders();

    String insertOne(Order aTransferOrder);
}
