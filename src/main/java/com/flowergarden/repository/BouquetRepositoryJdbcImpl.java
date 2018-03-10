package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.storage.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BouquetRepositoryJdbcImpl implements BouquetRepository {

    private JdbcConnectionPool connectionPool;

    private static final String FIND_ONE_SQL = "SELECT * FROM bouquet WHERE id=?";

    @Autowired
    public BouquetRepositoryJdbcImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Bouquet saveOrUpdate(Bouquet bouquet) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            if (bouquet.getId() == null) {
                // TODO: save
            }
            else {
                // TODO: update
            }
        }
        return null;
    }

    @Override
    public Bouquet<Flower> findOne(int id) throws SQLException {
        List<Bouquet<Flower>> bouquets = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ONE_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bouquet<Flower> bouquet = new MarriedBouquet();
                bouquet.setId(resultSet.getInt("id"));
                bouquet.setAssemblePrice(new BigDecimal(resultSet.getString("assemble_price")));
                bouquets.add(bouquet);
            }
        }

        if (bouquets.isEmpty())
            return null;

        if (bouquets.size() > 1)
            throw new SQLIntegrityConstraintViolationException("unique id");

        return bouquets.get(0);
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
