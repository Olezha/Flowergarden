package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.properties.FreshnessInteger;
import com.flowergarden.storage.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private JdbcConnectionPool connectionPool;

    private static final String FIND_BOUQUET_FLOWERS_SQL = "SELECT * FROM flower WHERE bouquet_id=?";

    @Autowired
    public FlowerRepositoryJdbcImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Flower saveOrUpdate(Flower flower) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            if (flower.getId() == null) {
                // TODO: save
            }
            else {
                // TODO: update
            }
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
    public Iterable<Flower> findBouquetFlowers(int bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOUQUET_FLOWERS_SQL)) {
            statement.setInt(1, bouquetId);
            ResultSet resultSet = statement.executeQuery();
            List<Flower> flowers = new ArrayList<>();
            while (resultSet.next()) {
                GeneralFlower flower = new GeneralFlower() {};
                flower.setId(resultSet.getInt("id"));
                flower.setFreshness(new FreshnessInteger(resultSet.getInt("freshness")));
                flower.setPrice(new BigDecimal(resultSet.getString("price")));
                flower.setLength(resultSet.getInt("length"));
                flowers.add(flower);
            }
            return flowers;
        }
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
