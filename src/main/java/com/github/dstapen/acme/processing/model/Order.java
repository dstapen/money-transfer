package com.github.dstapen.acme.processing.model;

import com.google.auto.value.AutoValue;
import com.github.dstapen.acme.processing.api.OrderDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.github.dstapen.acme.processing.model.Order.Kind.TRANSFER;

@AutoValue
public abstract class Order {

    @Nullable
    public abstract String id();

    @Nonnull
    public abstract Kind kind();

    public abstract long version();

    @Nullable
    public abstract String fromAccountId();

    public abstract String toAccountId();

    public abstract BigDecimal amount();

    public abstract State state();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String note();

    public abstract LocalDateTime created();

    public enum Kind {
        INITIAL_BALANCE, TRANSFER;
    }

    public enum State {
        SUBMITTED, PROCESSING, PROVISIONED, DECLINED;
    }

    @Nonnull
    public static Order newOrder(@Nullable String id,
                                 Kind kind,
                                 long version,
                                 @Nullable String fromAccountId,
                                 String toAccountId,
                                 BigDecimal amount,
                                 State state,
                                 String description,
                                 String note,
                                 LocalDateTime created) {
        return new AutoValue_Order(id, kind, version, fromAccountId, toAccountId, amount, state, description, note, created);
    }

    @Nonnull
    public static Order newOrder(OrderDTO aTransferOrderDTO, LocalDateTime created) {
        return new AutoValue_Order(
                null,
                TRANSFER,
                1L,
                aTransferOrderDTO.fromAccountId(),
                aTransferOrderDTO.toAccountId(),
                aTransferOrderDTO.amount(),
                State.SUBMITTED,
                aTransferOrderDTO.description(),
                null,
                created);
    }
}
