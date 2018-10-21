package com.github.dstapen.acme.processing.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.github.dstapen.acme.processing.model.Account;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

@AutoValue
@JsonSerialize(as = AccountDTO.class)
public abstract class AccountDTO {

    @Nullable
    @JsonProperty("id")
    public abstract String id();

    @Nullable
    @JsonProperty("description")
    public abstract String description();

    @Nullable
    @JsonProperty("ownerId")
    public abstract String ownerId();

    @Nullable
    @JsonProperty("initialBalance")
    public abstract String initialBalance();

    @Nullable
    @JsonProperty("balance")
    public abstract BigDecimal balance();

    @Nullable
    @JsonProperty("currency")
    public abstract String currencyCode();


    @Nonnull
    @JsonCreator
    public static AccountDTO newAccountDTO(@JsonProperty("id") String id,
                                           @JsonProperty("description") String description,
                                           @JsonProperty("ownerId") String ownerId,
                                           @JsonProperty("initialBalance") String initialBalance,
                                           @JsonProperty("balance") BigDecimal balance,
                                           @JsonProperty("currency") String currency) {
        return new AutoValue_AccountDTO(id, description, ownerId, initialBalance, balance, currency);
    }

    @Nonnull
    public static AccountDTO newAccountDTO(Account account) {
        return newAccountDTO(account, BigDecimal.ZERO);
    }

    @Nonnull
    public static AccountDTO newAccountDTO(Account account, BigDecimal balance) {
        final String initialBalance = ofNullable(account.initialBalance())
                .map(BigDecimal::toString)
                .orElse("0");
        return new AutoValue_AccountDTO(account.id(), account.description(),
                account.ownerId(), initialBalance, balance, account.currencyCode());

    }
}
