package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.storage.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private JdbcConnectionPool connectionPool;

    @Autowired
    public FlowerRepositoryJdbcImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Flower saveOrUpdate(Flower flower) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Flower findOne(int id) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // TODO
        }
        return null;
    }

    @Override
    public Iterable<Flower> findAll() throws SQLException {
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
    public void delete(Flower flower) throws SQLException {
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
