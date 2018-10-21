package com.github.dstapen.acme.processing.model.persistence.internal;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.persistence.internal.codec.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public abstract class GenericDAO<T> {
    protected static final Visitor<PreparedStatement> EMPTY_VISITOR = ps -> {};
    private final Logger log;
    protected final Connection aConnection;
    private final Mapper<T> mapper;


    public GenericDAO(Logger log, Connection aConnection, Mapper<T> mapper) {
        this.log = log;
        this.aConnection = aConnection;
        this.mapper = mapper;
    }

    protected <V> Optional<V> findCustom(String sqlText, Visitor<PreparedStatement> preparedStatementVisitor, Mapper<V> customMapper) {
        checkState(customMapper != null);
        checkState(preparedStatementVisitor != null);
        try (PreparedStatement ps = aConnection.prepareStatement(sqlText)) { // ps
            preparedStatementVisitor.visit(ps);
            final ResultSet rs = ps.executeQuery();
            return rs.next() ? of(customMapper.map(rs)) : empty();
        } catch (Exception e) {
            log.warn("something goes wrong during following SQL:\n{}", sqlText, e);
            throw new RuntimeException("Something goes wrong", e);
        }
    }

    protected <V> List<V> fetchCustom(String sqlText, Mapper<V> customMapper, Visitor<PreparedStatement> preparedStatementVisitor) {
        checkState(preparedStatementVisitor != null);
        checkState(customMapper != null);
        try (PreparedStatement ps = aConnection.prepareStatement(sqlText)) {
            preparedStatementVisitor.visit(ps);
            final ResultSet rs = ps.executeQuery();
            final List<V> list = new ArrayList<>();
            while (rs.next()) {
                list.add(customMapper.map(rs));
            }
            return list;
        } catch (Exception e) {
            log.warn("something goes wrong during following SQL:\n{}", sqlText, e);
            throw new RuntimeException("Something goes wrong", e);
        }
    }

    protected void upsertOne(String sqlText, Visitor<PreparedStatement> preparedStatementVisitor) {
        checkState(preparedStatementVisitor != null);
        log.info("upsert {}", sqlText);
        try (PreparedStatement ps = aConnection.prepareStatement(sqlText)) {
            preparedStatementVisitor.visit(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("something goes wrong during following SQL:\n{}", sqlText, e);
            throw new RuntimeException("Something goes wrong", e);
        }
    }

    protected List<T> fetch(String sql) {
        return fetch(sql, EMPTY_VISITOR);
    }

    protected List<T> fetch(String sql, Visitor<PreparedStatement> preparedStatementVisitor) {
        return fetch(sql, preparedStatementVisitor, mapper);
    }

    protected <V> List<V> fetch(String sql, Visitor<PreparedStatement> preparedStatementVisitor, Mapper<V> customMapper) {
        checkState(preparedStatementVisitor != null);
        checkState(mapper != null);
        try (PreparedStatement ps = aConnection.prepareStatement(sql)) {
            preparedStatementVisitor.visit(ps);
            final ResultSet rs = ps.executeQuery();
            final List<V> list = new ArrayList<>();
            while (rs.next()) {
                list.add(customMapper.map(rs));
            }
            return list;
        } catch (Exception e) {
            log.warn("something goes wrong during following SQL:\n{}", sql, e);
            throw new RuntimeException("Something goes wrong", e);
        }
    }

    protected Optional<T> find(String sql, Visitor<PreparedStatement> preparedStatementVisitor) {
        return findCustom(sql, preparedStatementVisitor, mapper);
    }



    protected <V> List<V> fetchCustom(String sql, Mapper<V> aMapper) {
        return fetchCustom(sql, aMapper, EMPTY_VISITOR);
    }



    protected void upsertOne(String sql) {
        upsertOne(sql, EMPTY_VISITOR);
    }



    public interface Visitor<V> {
        void visit(V elem) throws SQLException;
    }
}
