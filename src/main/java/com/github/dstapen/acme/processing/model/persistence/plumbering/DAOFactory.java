package com.github.dstapen.acme.processing.model.persistence.plumbering;

import com.github.dstapen.acme.processing.model.persistence.AccountDAO;
import com.github.dstapen.acme.processing.model.persistence.OrderDAO;
import com.github.dstapen.acme.processing.model.persistence.SchemaDAO;
import com.github.dstapen.acme.processing.model.persistence.TransactionDAO;

import javax.annotation.Nonnull;
import java.sql.Connection;

public interface DAOFactory {

    @Nonnull
    DAO dao();

    public interface DAO extends AutoCloseable {

        @Nonnull
        OrderDAO orderDAO();

        @Nonnull
        AccountDAO accountDAO();

        @Nonnull
        TransactionDAO transactionDAO();

        @Nonnull
        SchemaDAO schemaDAO();

        @Nonnull
        Connection connection();

        @Nonnull
        TxTemplate newTxTemplate();


    }
}
