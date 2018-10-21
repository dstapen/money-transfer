package com.github.dstapen.acme.processing.model.persistence;

import static com.github.dstapen.acme.processing.util.Resource.getResource;

public interface SchemaDAO {

    String SQL_CREATE_SCHEMA = getResource("/sql/schema.sql");

    void createSchema();
}
