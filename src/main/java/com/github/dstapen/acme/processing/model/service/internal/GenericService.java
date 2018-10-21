package com.github.dstapen.acme.processing.model.service.internal;

import org.slf4j.Logger;
import com.github.dstapen.acme.processing.model.persistence.plumbering.DAOFactory;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.dstapen.acme.processing.model.persistence.plumbering.DAOFactory.DAO;

public abstract class GenericService {
    private final Logger log;
    protected final DAOFactory daoFactory;

    public GenericService(Logger log, DAOFactory daoFactory) {
        this.log = log;
        this.daoFactory = daoFactory;
    }

    protected void performInTransaction(Consumer<DAO> visitor) {
        try (DAO dao = daoFactory.dao()) {
            dao.newTxTemplate().perform(() -> visitor.accept(dao));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T executeInTransaction(Function<DAO,T> fn) {
        try (DAO dao = daoFactory.dao()) {
            return dao.newTxTemplate().execute(() -> fn.apply(dao));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
