package com.github.dstapen.acme.processing.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.dstapen.acme.processing.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@JsonSerialize(as = TransactionDTO.class)
public final class TransactionDTO {

    @JsonProperty("id")
    public final String id;

    @JsonProperty("orderId")
    public final String orderId;

    @JsonProperty("dt")
    public final String dt;

    @JsonProperty("cr")
    public final String cr;

    @JsonProperty("amount")
    public final BigDecimal amount;

    @JsonProperty("description")
    public final String description;

    @JsonProperty("note")
    public final String note;


    @JsonCreator
    public TransactionDTO(@JsonProperty("id") String id,
                          @JsonProperty("orderId") String orderId,
                          @JsonProperty("dt") String dt,
                          @JsonProperty("cr") String cr,
                          @JsonProperty("amount") BigDecimal amount,
                          @JsonProperty("description") String description,
                          @JsonProperty("note") String note) {
        this.id = id;
        this.orderId = orderId;
        this.dt = dt;
        this.cr = cr;
        this.amount = amount;
        this.description = description;
        this.note = note;
    }

    public TransactionDTO(Transaction tx) {
        this.id = ofNullable(tx.id()).map(UUID::toString).orElse(null);
        this.orderId = tx.orderId().toString();
        this.cr = tx.fromAccountId();
        this.dt = tx.toAccountId();
        this.amount = tx.amount();
        this.description = tx.description();
        this.note = tx.note();
    }
}
