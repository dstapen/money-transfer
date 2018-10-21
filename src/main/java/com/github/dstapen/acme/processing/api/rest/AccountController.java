package com.github.dstapen.acme.processing.api.rest;


import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
import org.slf4j.Logger;
import com.github.dstapen.acme.processing.api.AccountDTO;
import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.service.AccountService;
import com.github.dstapen.acme.processing.model.service.TransactionService;

import javax.inject.Inject;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.api.AccountDTO.newAccountDTO;
import static com.github.dstapen.acme.processing.model.Account.newAccount;

@Controller(value = "/api/account", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
@SuppressWarnings("unused")
public class AccountController {
    private static final Logger LOG = getLogger(AccountController.class);
    private final AccountService anAccountService;
    private final TransactionService aTransactionService;

    @Inject
    public AccountController(AccountService anAccountService,
                             TransactionService aTransactionService) {
        this.anAccountService = anAccountService;
        this.aTransactionService = aTransactionService;
    }

    @Get("/{id}")
    Single<AccountDTO> findAccount(@Value("${id}") String id) {
        return aTransactionService.balance(id)
                .map(entry -> Single.just(newAccountDTO(entry.getKey(), entry.getValue())))
                .orElse(Single.never());
    }

    @Post
    Single<AccountDTO> provideAccount(@Body AccountDTO anAccount) {
        Account input = newAccount(anAccount);
        String id = anAccountService.provideAccount(input);
        return Single.just(newAccountDTO(id, input.description(), input.ownerId(),
                input.initialBalance().toString(), anAccount.balance(), input.currencyCode()));
    }

}
