package com.github.dstapen.acme.processing.model.persistence.plumbering;

import javax.annotation.Nonnull;
import java.sql.Connection;

public final class JdbcConnectionTenant implements AutoCloseable {

    private final Connection aConnection;

    public JdbcConnectionTenant(Connection aConnection) {
        this.aConnection = aConnection;
    }

    @Nonnull
    public Connection connection() {
        return aConnection;
    }

    @Nonnull
    public TxTemplate txTemplate() {
        return new TxTemplate(aConnection);
    }

    @Override
    public void close() throws Exception {
        aConnection.close();
    }
}
