package com.github.dstapen.acme.processing.api.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import com.github.dstapen.acme.processing.api.TransactionDTO;
import com.github.dstapen.acme.processing.model.service.TransactionService;

import javax.inject.Inject;
import java.util.List;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static java.util.stream.Collectors.toList;

@Controller(value = "/api/transactions", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
public class TransactionController {

    private final TransactionService aTransactionService;

    @Inject
    public TransactionController(TransactionService aTransactionService) {
        this.aTransactionService = aTransactionService;
    }

    @Get
    List<TransactionDTO> transactions() {
        return aTransactionService.fetchAllTransactions().stream()
                .map(TransactionDTO::new)
                .collect(toList());
    }

}
