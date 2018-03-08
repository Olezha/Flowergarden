package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.storage.JdbcConnectionFromPool;
import com.flowergarden.storage.JdbcConnectionPool;

public class BouquetRepositoryJdbcImpl implements BouquetRepository {

    private JdbcConnectionPool connectionPool;

    @Override
    public Bouquet saveOrUpdate(Bouquet bouquet) throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Bouquet findOne(int id) throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Iterable<Bouquet> findAll() throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public void delete(int id) throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
    }

    @Override
    public void delete(Bouquet bouquet) throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
    }

    @Override
    public void deleteAll() throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
    }
}
