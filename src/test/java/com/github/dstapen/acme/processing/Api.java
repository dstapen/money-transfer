package com.github.dstapen.acme.processing;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;
import com.github.dstapen.acme.processing.api.AccountDTO;
import com.github.dstapen.acme.processing.api.OrderDTO;

@Client("/api")
public interface Api {

    @Post("/account")
    Single<AccountDTO> createNewAccount(@Body AccountDTO input);

    @Post("/order/transfer-order")
    Single<OrderDTO> placeTransferOrder(@Body OrderDTO dto);

    @Get("/account/{id}")
    Single<AccountDTO> getAccount(String id);
}
