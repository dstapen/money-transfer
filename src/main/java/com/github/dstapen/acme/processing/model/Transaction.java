package com.github.dstapen.acme.processing.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.UUID;

@AutoValue
public abstract class Transaction {

    @Nullable
    public abstract UUID id();

    @Nonnull
    public abstract UUID orderId();

    public abstract String accountId();

    @Nullable
    public abstract String fromAccountId();

    public abstract String toAccountId();

    public abstract BigDecimal amount();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String note();

    @Nonnull
    public static Transaction newTransaction(@Nullable UUID id,
                                             UUID orderId,
                                             String accountId,
                                             @Nullable String fromAccountId,
                                             String toAccountId,
                                             BigDecimal amount,
                                             @Nullable String description,
                                             @Nullable String note) {
        return new AutoValue_Transaction(id, orderId, accountId, fromAccountId, toAccountId, amount, description, note);
    }

}
