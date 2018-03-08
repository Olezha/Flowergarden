package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.storage.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

public class BouquetRepositoryJdbcImpl implements BouquetRepository {

    @Autowired
    private JdbcConnectionPool connectionPool;

    @Override
    public Bouquet saveOrUpdate(Bouquet bouquet) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Bouquet findOne(int id) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Iterable<Bouquet> findAll() throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
    }

    @Override
    public void delete(Bouquet bouquet) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
    }

    @Override
    public void deleteAll() throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
    }
}
