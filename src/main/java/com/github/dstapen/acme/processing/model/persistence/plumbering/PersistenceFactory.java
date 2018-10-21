package com.github.dstapen.acme.processing.model.persistence.plumbering;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.service.SchemaServiceImpl;

import javax.inject.Singleton;

import static org.slf4j.LoggerFactory.getLogger;

@Factory
@SuppressWarnings("unused")
public class PersistenceFactory  {
    private static final Logger LOG = getLogger(PersistenceFactory.class);


    @Singleton
    HikariConfig provideHikariConfig(PersistenceConfig aPersistenceConfig) {
        LOG.info("configuring DB connection...");
        HikariConfig aHikariConfig = new HikariConfig();
        LOG.info("DB url = {}", aPersistenceConfig.getUrl());
        aHikariConfig.setJdbcUrl(aPersistenceConfig.getUrl());
        aHikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        aHikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        aHikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return aHikariConfig;
    }

    @Singleton
    HikariDataSource provideHikariDataSource(HikariConfig aHikariConfig) {
        return new HikariDataSource(aHikariConfig);
    }

    @Singleton
    DAOFactory provideDAOFactory(HikariDataSource aHikariDataSource) {
        DAOFactory daoFactory = new DAOFactoryImpl(aHikariDataSource);
        new SchemaServiceImpl(daoFactory).createSchema();
        return daoFactory;
    }
}
