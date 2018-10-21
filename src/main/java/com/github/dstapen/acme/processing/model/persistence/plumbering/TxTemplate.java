package com.github.dstapen.acme.processing.model.persistence.plumbering;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

import static org.slf4j.LoggerFactory.getLogger;

public class TxTemplate {
    private static final Logger LOG = getLogger(TxTemplate.class);

    private final Connection aConnection;

    public TxTemplate(Connection aConnection) {
        this.aConnection = aConnection;
    }


    public void perform(Runnable action) {
        try {
            aConnection.setAutoCommit(false);
            action.run();
            aConnection.commit();
        } catch (Exception e) {
            try {
                aConnection.rollback();
            } catch (SQLException ex) {
                LOG.error("aw snap", ex);
            }
            throw new RuntimeException(e);
        }
    }


    public <T> T execute(Supplier<T> supplier) {
        try {
            aConnection.setAutoCommit(false);
            T result = supplier.get();
            aConnection.commit();
            return result;
        } catch (Exception e) {
            try {
                aConnection.rollback();
            } catch (SQLException ex) {
                LOG.error("aw snap", ex);
            }
            throw new RuntimeException(e);
        }
    }

}
