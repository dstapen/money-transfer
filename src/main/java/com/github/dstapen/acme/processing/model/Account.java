package com.github.dstapen.acme.processing.model;

import com.google.auto.value.AutoValue;
import com.github.dstapen.acme.processing.api.AccountDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@AutoValue
public abstract class Account {
    @Nullable
    public abstract String id();

    public abstract String ownerId();

    @Nullable
    public abstract BigDecimal initialBalance();

    public abstract String currencyCode();

    public abstract String description();

    @Nonnull
    public static Account newAccount(
            @Nullable String id,
            String ownerId,
            @Nullable BigDecimal initialBalance,
            String currencyCode,
            String description) {
        return new AutoValue_Account(id, ownerId, initialBalance, currencyCode, description);
    }

    @Nonnull
    public static Account newAccount(AccountDTO dto) {
        final BigDecimal initialBalance = Optional.ofNullable(dto.initialBalance()).map(BigDecimal::new).orElse(BigDecimal.ZERO);
        return new AutoValue_Account(dto.id(), dto.ownerId(), initialBalance, dto.currencyCode(), dto.description());
    }
}
