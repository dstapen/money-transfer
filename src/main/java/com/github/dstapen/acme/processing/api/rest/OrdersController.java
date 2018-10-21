package com.github.dstapen.acme.processing.api.rest;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import com.github.dstapen.acme.processing.api.OrderDTO;

import com.github.dstapen.acme.processing.model.service.OrderService;
import com.github.dstapen.acme.processing.model.service.TransactionService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static org.slf4j.LoggerFactory.getLogger;


@Controller(value = "/api/orders", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
@SuppressWarnings("unused")
public class OrdersController {
    private static final Logger LOG = getLogger(OrdersController.class);
    private final TransactionService aTransactionService;
    private final OrderService anOrderService;

    @Inject
    public OrdersController(TransactionService aTransactionService, OrderService anOrderService) {
        this.aTransactionService = aTransactionService;
        this.anOrderService = anOrderService;
    }

    @Get
    List<OrderDTO> orders() {
        return anOrderService.orders().stream()
                .map(OrderDTO::newOrderDTO)
                .collect(Collectors.toList());
    }

    @Get("operation-log")
    List<OrderDTO> operationLog() {
        return anOrderService.fetchAllOrders().stream()
                .map(OrderDTO::newOrderDTO)
                .collect(Collectors.toList());
    }

}
