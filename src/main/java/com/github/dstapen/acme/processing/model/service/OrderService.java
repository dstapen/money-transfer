package com.github.dstapen.acme.processing.model.service;

import com.github.dstapen.acme.processing.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> findOne(String id);

    List<Order> orders();

    List<Order> fetchAllOrders();

    Order placeTransferOrder(Order aTransferOrder);

    void processOrders();

}
