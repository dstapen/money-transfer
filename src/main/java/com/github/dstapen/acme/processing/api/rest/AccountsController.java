package com.github.dstapen.acme.processing.api.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import com.github.dstapen.acme.processing.api.AccountDTO;
import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.service.AccountService;
import com.github.dstapen.acme.processing.model.service.TransactionService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static com.github.dstapen.acme.processing.api.AccountDTO.newAccountDTO;
import static com.github.dstapen.acme.processing.model.Account.newAccount;
import static com.github.dstapen.acme.processing.util.Util.*;

@Controller(value = "/api/accounts", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
@SuppressWarnings("unused")
public class AccountsController {

    private final AccountService anAccountService;
    private final TransactionService aTransactionService;

    @Inject
    public AccountsController(AccountService anAccountService,
                              TransactionService aTransactionService) {
        this.anAccountService = anAccountService;
        this.aTransactionService = aTransactionService;
    }

    @Get()
    List<AccountDTO> fetchAll() {
        return aTransactionService.balances().stream()
                .map(accAndBalance -> newAccountDTO(accAndBalance.getKey(), accAndBalance.getValue()))
                .collect(Collectors.toList());
    }

// region for easy dev
//    @Post("/populate")
//    AccountDTO populate() {
//        String currency = fiftyFifty() ? "USD" : "EUR";
//        String ownerId = randomString();
//        String description = fiftyFifty() ? "Fizz" : "Buzz";
//        BigDecimal initialBalance = randomBigDecimal(73, 100_000);
//        Account anAccount = newAccount(null, ownerId, initialBalance, currency, description);
//        String id = anAccountService.provideAccount(anAccount);
//        return newAccountDTO(id, anAccount.description(), anAccount.ownerId(), anAccount.initialBalance().toString(),
//                BigDecimal.ZERO, anAccount.currencyCode());
//    }
// endregion
}
