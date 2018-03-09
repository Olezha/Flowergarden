package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.storage.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class BouquetRepositoryJdbcImpl implements BouquetRepository {

    private JdbcConnectionPool connectionPool;

    @Autowired
    public BouquetRepositoryJdbcImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Bouquet saveOrUpdate(Bouquet bouquet) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Bouquet findOne(int id) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Iterable<Bouquet> findAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
    }

    @Override
    public void delete(Bouquet bouquet) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
    }
}
