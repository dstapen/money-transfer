package com.github.dstapen.acme.processing.api.rest;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
import org.slf4j.Logger;
import com.github.dstapen.acme.processing.api.OrderDTO;
import com.github.dstapen.acme.processing.model.Order;
import com.github.dstapen.acme.processing.model.service.OrderService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.api.OrderDTO.newOrderDTO;
import static com.github.dstapen.acme.processing.model.Order.Kind.TRANSFER;
import static com.github.dstapen.acme.processing.model.Order.newOrder;
import static com.github.dstapen.acme.processing.util.Util.randomOrZero;

@Controller(value = "/api/order", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
@SuppressWarnings("unused")
public class OrderController {
    private static final Logger LOG = getLogger(OrderController.class);

    private final OrderService anOrderService;

    @Inject
    public OrderController(OrderService anOrderService) {
        this.anOrderService = anOrderService;
    }

// region easy dev populate
//    @Post("/populate-random-transfer-orders/{nr}")
//    List<OrderDTO> placeRandomTransferOrders(@Value("nr") String nr) {
//        final long number = Long.parseLong(nr);
//        if (number <= 0) {
//            return Collections.EMPTY_LIST;
//        } else {
//            return LongStream.rangeClosed(1, number).boxed()
//                    .map(counter -> {
//                        final Order aTransferOrder = Order.newOrder(null, TRANSFER, 1, counter.toString(), counter.toString(),
//                                randomOrZero(100, 10_000),
//                                Order.State.SUBMITTED, counter.toString(), counter.toString(), now());
//                        return newOrderDTO(anOrderService.placeTransferOrder(aTransferOrder));
//                    })
//                    .collect(Collectors.toList());
//        }
//    }
// endregion

    @Post("/transfer-order")
    Single<OrderDTO> placeTransferOrder(@Body OrderDTO dto) {
        LOG.info("placing order...");
        OrderDTO safeDTO = newOrderDTO(null,
                TRANSFER.toString(),
                dto.fromAccountId(),
                dto.toAccountId(),
                dto.amount(),
                Order.State.SUBMITTED,
                dto.description(),
                dto.created());

        return Single.just(newOrderDTO(anOrderService.placeTransferOrder(newOrder(safeDTO, now()))));
    }

}
