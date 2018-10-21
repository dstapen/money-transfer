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
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.model.Order.Kind.INITIAL_BALANCE;
import static com.github.dstapen.acme.processing.model.Order.State.SUBMITTED;
import static com.github.dstapen.acme.processing.model.Order.newOrder;

@Singleton
@SuppressWarnings("unused")
public final class AccountServiceImpl extends GenericService implements AccountService {
    private static final Logger LOG = getLogger(AccountServiceImpl.class);

    @Inject
    public AccountServiceImpl(DAOFactory daoFactory) {
        super(LOG, daoFactory);
    }

    @Override
    public String provideAccount(final Account anAccount) {
        return executeInTransaction(dao -> {
                final String anAccontId = dao.accountDAO().insertOne(anAccount);
                if (0 > BigDecimal.ZERO.compareTo(anAccount.initialBalance())) {
                    Order order = newOrder(null, INITIAL_BALANCE, 0, null, anAccontId, anAccount.initialBalance(), SUBMITTED, "INITIAL BALANCE", "WAT!?", now() );
                    final String anOrderId = dao.orderDAO().insertOne(order);
                }
                return anAccontId;
        });
    }

    @Override
    public Optional<Account> findOne(String id) {
        final UUID uuid = UUID.fromString(id);
        return executeInTransaction(dao -> dao.accountDAO().findOne(uuid));
    }

    @Override
    public List<Account> findAll() {
        return executeInTransaction(dao -> dao.accountDAO().findAll());
    }
}
