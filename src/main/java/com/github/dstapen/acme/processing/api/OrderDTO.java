package com.github.dstapen.acme.processing.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.github.dstapen.acme.processing.model.Order;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static com.github.dstapen.acme.processing.model.Order.State;

@AutoValue
@JsonSerialize(as = OrderDTO.class)
public abstract class OrderDTO {
    private static final String PATTERN = "yyyy-MM-dd HH:mm";

    @Nullable
    @JsonProperty("id")
    public abstract String id();

    @Nullable
    public abstract String kind();

    @Nullable
    @JsonProperty("from")
    public abstract String fromAccountId();

    @Nonnull
    @JsonProperty("to")
    public abstract String toAccountId();

    @JsonProperty("amount")
    public abstract BigDecimal amount();

    @Nullable
    @JsonProperty("state")
    public abstract State state();

    @Nullable
    @JsonProperty("description")
    public abstract String description();

    @Nullable
    @JsonProperty("created")
    public abstract String created();


    @Nonnull
    @JsonCreator
    public static OrderDTO newOrderDTO(@JsonProperty("id") String id,
                                       @JsonProperty("kind") String kind,
                                       @JsonProperty("from") String fromAccountId,
                                       @JsonProperty("to") String toAccountId,
                                       @JsonProperty("amount") BigDecimal amount,
                                       @JsonProperty("state") State state,
                                       @JsonProperty("description") String description,
                                       @JsonProperty("created") String created) {
        return new AutoValue_OrderDTO(id, kind, fromAccountId, toAccountId, amount, state, description, created);
    }

    @Nonnull
    public static OrderDTO newOrderDTO(Order aTransferOrder) {

        return new AutoValue_OrderDTO(
                aTransferOrder.id(),
                aTransferOrder.kind().toString(),
                aTransferOrder.fromAccountId(),
                aTransferOrder.toAccountId(),
                aTransferOrder.amount(),
                aTransferOrder.state(),
                aTransferOrder.description(),

                // convert local date time into a string value
                aTransferOrder.created().format(DateTimeFormatter.ofPattern(PATTERN)));
    }
}
