package com.github.dstapen.acme.processing.model.service;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.persistence.plumbering.DAOFactory;
import com.github.dstapen.acme.processing.model.service.internal.GenericService;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public final class SchemaServiceImpl extends GenericService implements SchemaService {
    private static final Logger LOG = getLogger(SchemaServiceImpl.class);

    @Inject
    public SchemaServiceImpl(DAOFactory daoFactory) {
        super(LOG, daoFactory);
    }

    @Override
    public void createSchema() {
        performInTransaction((dao) -> {
            LOG.info("provisioning database schema...");
            dao.schemaDAO().createSchema();
            LOG.info("database schema has been provisioned.");
        });
    }
}
