package com.github.dstapen.acme.processing.model.service;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.Order;
import com.github.dstapen.acme.processing.model.persistence.plumbering.DAOFactory;
import com.github.dstapen.acme.processing.model.service.internal.GenericService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.model.Order.Kind.TRANSFER;
import static com.github.dstapen.acme.processing.model.Order.State;
import static com.github.dstapen.acme.processing.model.Order.State.DECLINED;
import static com.github.dstapen.acme.processing.model.Order.State.PROVISIONED;
import static com.github.dstapen.acme.processing.model.Order.newOrder;
import static com.github.dstapen.acme.processing.model.Transaction.newTransaction;

@Singleton
@SuppressWarnings("unused")
public final class OrderServiceImpl extends GenericService implements OrderService {
    private static final Logger LOG = getLogger(OrderServiceImpl.class);

    @Inject
    public OrderServiceImpl(DAOFactory daoFactory) {
        super(LOG, daoFactory);
    }



    @Override
    public Optional<Order> findOne(String id) {
        return Optional.empty();
    }

    @Override
    public List<Order> orders() {
        return executeInTransaction(dao -> dao.orderDAO().orders());
    }

    @Override
    public List<Order> fetchAllOrders() {
        return executeInTransaction(dao -> dao.orderDAO().findAll());
    }

    @Override
    public Order placeTransferOrder(Order anOrder) {
        checkArgument(anOrder.fromAccountId() != null);
        checkArgument(anOrder.toAccountId() != null);
        return executeInTransaction(dao -> {
            Optional<Account> accFrom = dao.accountDAO().findOne(UUID.fromString(anOrder.fromAccountId()));
            Optional<Account> accTo = dao.accountDAO().findOne(UUID.fromString(anOrder.toAccountId()));
            if (accFrom.isPresent() && accTo.isPresent()) {
                final String anOrderId = dao.orderDAO().insertOne(anOrder);
                return newOrder(anOrderId, TRANSFER, anOrder.version(), anOrder.fromAccountId(), anOrder.toAccountId(), anOrder.amount(), anOrder.state(), anOrder.description(), anOrder.note(), now());
            } else throw new RuntimeException("AccountFrom or AccountTo are not fined from order " + anOrder.id());
        });
    }

    @Override
    public void processOrders() {
        performInTransaction(dao -> {
            List<Order> someOrders = dao.orderDAO().someOrders();
            for (Order someOrder : someOrders) {
                State state = processOrder(dao, someOrder);
                Order newOne = newOrder(someOrder.id(),
                        someOrder.kind(),
                        someOrder.version() + 1,
                        someOrder.fromAccountId(), someOrder.toAccountId(), someOrder.amount(),
                        state,
                        someOrder.description(),
                        someOrder.note(),
                        someOrder.created());
                dao.orderDAO().insertOne(newOne);
            }
        });
    }

    private State processOrder(DAOFactory.DAO dao, Order someOrder) {
        try {
            switch (someOrder.kind()) {
                case TRANSFER:
                    if (someOrder.fromAccountId() == null) {
                        LOG.info("Transfer order {} is without accountFrom, ex. InitialBalance {}", someOrder.id());
                        return DECLINED;
                    }
                    Optional<Map.Entry<Account, BigDecimal>> optionalBalance = dao.transactionDAO().calculateBalance(someOrder.fromAccountId());
                    if (optionalBalance.isPresent() && null != optionalBalance.get().getValue()) {
                        if (optionalBalance.get().getValue().compareTo(someOrder.amount()) >= 0) {
                            // region do credit tx
                            UUID creditTransactionId = UUID.randomUUID();
                            dao.transactionDAO().insertOne(
                                    newTransaction(creditTransactionId, UUID.fromString(someOrder.id()),
                                            someOrder.fromAccountId(),
                                            someOrder.fromAccountId(),
                                            someOrder.toAccountId(),
                                            someOrder.amount().negate(), // sic! negative amount
                                            someOrder.description(),
                                            "CR"));
                            // endregion
                        } else {
                            LOG.info("Not enough money on account {} according to orderId {}",
                                    someOrder.fromAccountId(), someOrder.id());
                            return DECLINED;
                        }
                    } else {
                        LOG.info("Balance for order {} is not defined", someOrder.id());
                        return DECLINED;
                    }
                    break;

                case INITIAL_BALANCE:
                    if (someOrder.fromAccountId() != null) {
                        LOG.warn("Order {} with kind {} can't have parameter <fromAccountId> {}",
                                someOrder.id(), someOrder.kind(), someOrder.fromAccountId());
                        return DECLINED;
                    }
                    break;
                default:
                    LOG.warn("Unknown kind of order: {}", someOrder.kind());
                    return DECLINED;

            }
            // region do debit tx
            UUID debigTransactionId = UUID.randomUUID();
            dao.transactionDAO().insertOne(
                    newTransaction(debigTransactionId, UUID.fromString(someOrder.id()), someOrder.toAccountId(), someOrder.fromAccountId(), someOrder.toAccountId(), someOrder.amount(), someOrder.description(), "DT")
            );
            // endregion

            return PROVISIONED;
        } catch (Exception e) {
            LOG.error("Aw snap", e);
            return DECLINED;
        }
    }

}
