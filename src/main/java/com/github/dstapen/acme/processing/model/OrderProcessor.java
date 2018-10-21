package com.github.dstapen.acme.processing.model;

import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.service.OrderService;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
@SuppressWarnings("unused")
public class OrderProcessor  {
    private final static Logger LOG = getLogger(OrderProcessor.class);
    private final OrderService anOrderService;

    @Inject
    public OrderProcessor(OrderService anOrderService) {
        this.anOrderService = anOrderService;
    }

    @Scheduled(fixedRate = "1s")
    void configuredTask() {
        try {
            anOrderService.processOrders();
        } catch (Exception e) {
            LOG.trace("aw snap", e);
        }
    }
}
