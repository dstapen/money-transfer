package com.github.dstapen.acme.processing.model.persistence.plumbering;

import com.zaxxer.hikari.HikariDataSource;
import com.github.dstapen.acme.processing.model.persistence.*;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactoryImpl implements DAOFactory {

    private final HikariDataSource aHikariDataSource;


    DAOFactoryImpl(HikariDataSource aHikariDataSource) {
        this.aHikariDataSource = aHikariDataSource;
    }

    @Nonnull
    @Override
    public DAO dao() {
        return new DAOImpl(newJdbcConnectionTenant());
    }

    @Nonnull
    public JdbcConnectionTenant newJdbcConnectionTenant() {
        try {
            return new JdbcConnectionTenant(aHikariDataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static class DAOImpl implements DAO {

        private final JdbcConnectionTenant jdbcConnectionTenant;

        DAOImpl(JdbcConnectionTenant jdbcConnectionTenant) {
            this.jdbcConnectionTenant = jdbcConnectionTenant;
        }

        @Nonnull @Override
        public Connection connection() {
            return jdbcConnectionTenant.connection();
        }

        @Nonnull @Override
        public TxTemplate newTxTemplate() {
            return jdbcConnectionTenant.txTemplate();
        }

        @Nonnull @Override
        public OrderDAO orderDAO() {
            return new OrderDAOImpl(connection());
        }

        @Nonnull @Override
        public AccountDAO accountDAO() {
            return new AccountDAOImpl(connection());
        }

        @Nonnull
        public SchemaDAO schemaDAO() {
            return new SchemaDAOImpl(connection());
        }

        @Nonnull @Override
        public TransactionDAO transactionDAO() {
            return new TransactionDAOImpl(connection());
        }

        @Override
        public void close() throws Exception {
            jdbcConnectionTenant.close();
        }
    }
}


