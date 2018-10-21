package com.github.dstapen.acme.processing.model.persistence;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.persistence.internal.GenericDAO;

import java.sql.Connection;

import static org.slf4j.LoggerFactory.getLogger;

public final class SchemaDAOImpl extends GenericDAO implements SchemaDAO {
    private static final Logger LOG = getLogger(SchemaDAOImpl.class);

    public SchemaDAOImpl(Connection aConnection) {
        super(LOG, aConnection, null);
    }

    @Override
    public void createSchema() {
        LOG.info("schema:\n{}", SQL_CREATE_SCHEMA);
        upsertOne(SQL_CREATE_SCHEMA);
    }
}
