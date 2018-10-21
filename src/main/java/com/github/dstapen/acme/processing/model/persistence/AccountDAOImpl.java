package com.github.dstapen.acme.processing.model.persistence;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.Account;
import com.github.dstapen.acme.processing.model.persistence.internal.GenericDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;
import static com.github.dstapen.acme.processing.model.persistence.internal.codec.AccountMapper.ACCOUNT_MAPPER;


@Singleton
public final class AccountDAOImpl extends GenericDAO<Account> implements AccountDAO {
    private static final Logger LOG = getLogger(AccountDAOImpl.class);

    @Inject
    public AccountDAOImpl(Connection aConnection) {
        super(LOG, aConnection, ACCOUNT_MAPPER);
    }

    @Override
    public Optional<Account> findOne(UUID id) {
        return find(SQL_FIND_ONE, ps -> {
            ps.setObject(1, id);
        });
    }

    @Override
    public List<Account> findAll() {
        return fetch(SQL_FETCH_ALL);
    }


    @Override
    public String insertOne(Account anAccount) {
        UUID uuid = UUID.randomUUID();
        upsertOne(SQL_INSERT_ONE, ps -> {
            ps.setObject(1, uuid);
            ps.setString(2, anAccount.ownerId());
            ps.setBigDecimal(3, anAccount.initialBalance());
            ps.setString(4, anAccount.currencyCode());
            ps.setString(5, anAccount.description());
        });
        LOG.info("inserted account key {}", uuid);
        return uuid.toString();
    }
}


