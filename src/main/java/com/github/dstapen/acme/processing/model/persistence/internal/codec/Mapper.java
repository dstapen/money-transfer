package com.github.dstapen.acme.processing.model.persistence.internal.codec;

import java.sql.ResultSet;

public interface Mapper<T> {

    T map(ResultSet rs) throws Exception;

}
