package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.storage.JdbcConnectionFromPool;
import com.flowergarden.storage.JdbcConnectionPool;

public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private JdbcConnectionPool connectionPool;

    @Override
    public Flower saveOrUpdate(Flower flower) throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Flower findOne(int id) throws Exception {
        try (JdbcConnectionFromPool connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Iterable<Flower> findAll() throws Exception {
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
    public void delete(Flower flower) throws Exception {
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
