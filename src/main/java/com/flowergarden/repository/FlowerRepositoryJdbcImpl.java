package com.flowergarden.repository;

import com.flowergarden.model.flowers.*;
import com.flowergarden.model.properties.FreshnessInteger;
import com.flowergarden.sql.JdbcConnectionPool;
import com.flowergarden.sql.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private JdbcConnectionPool connectionPool;

    private SqlStatements sql;

    @Autowired
    public FlowerRepositoryJdbcImpl(
            JdbcConnectionPool connectionPool,
            SqlStatements sql) {
        this.connectionPool = connectionPool;
        this.sql = sql;
    }

    @Override
    public Flower saveOrUpdate(Flower flower) throws SQLException {
        return saveOrUpdate(flower, null);
    }

    @Override
    public Flower saveOrUpdate(Flower flower, Integer bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            if (flower.getId() == null) {
                try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_SAVE"))) {
                    statement.setString(1, flower.getClass().getSimpleName().toLowerCase());
                    statement.setInt(2, flower.getLength());
                    if (flower instanceof GeneralFlower) {
                        GeneralFlower generalFlower = (GeneralFlower) flower;
                        statement.setInt(3, generalFlower.getFreshness().getFreshness());
                    }
                    statement.setBigDecimal(4, flower.getPrice());
                    if (flower instanceof Chamomile) {
                        Chamomile chamomile = (Chamomile) flower;
                        statement.setInt(5, chamomile.getPetals());
                    }
                    if (flower instanceof Rose) {
                        Rose rose = (Rose) flower;
                        statement.setBoolean(6, rose.getSpike());
                    }
                    statement.setInt(7, bouquetId);
                    statement.executeUpdate();

                    flower.setId(statement.getGeneratedKeys().getInt(1));
                }
            }
            else {
                try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_UPDATE"))) {
                    statement.setInt(1, flower.getLength());
                    if (flower instanceof GeneralFlower) {
                        GeneralFlower generalFlower = (GeneralFlower) flower;
                        statement.setInt(2, generalFlower.getFreshness().getFreshness());
                    }
                    statement.setBigDecimal(3, flower.getPrice());
                    if (flower instanceof Chamomile) {
                        Chamomile chamomile = (Chamomile) flower;
                        statement.setInt(4, chamomile.getPetals());
                    }
                    if (bouquetId != null)
                    statement.setInt(5, bouquetId);
                    statement.setInt(6, flower.getId());
                    statement.executeUpdate();
                }
            }
        }
        return flower;
    }

    @Override
    public void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId) throws SQLException {
        for (Flower flower : flowers)
            saveOrUpdate(flower, bouquetId);
    }

    @Override
    public Flower findOne(int id) throws SQLException {
        List<Flower> flowers;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_ONE"))) {
            statement.setInt(1, id);
            flowers = convert(statement.executeQuery());
        }

        if (flowers.isEmpty())
            return null;

        if (flowers.size() > 1)
            throw new SQLIntegrityConstraintViolationException("Unique id");

        return flowers.get(0);
    }

    @Override
    public Iterable<Flower> findAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            return convert(statement.executeQuery(sql.get("FLOWER_FIND_ALL")));
        }
    }

    @Override
    public Iterable<Flower> findBouquetFlowers(int bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_BOUQUET_FLOWERS"))) {
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
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE"))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Flower flower) throws SQLException {
        if (flower.getId() == null)
            throw new IllegalArgumentException("Not persisted entity");
        delete(flower.getId());
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.get("FLOWER_DELETE_ALL"));
        }
    }

    @Override
    public void deleteBouquetFlowers(int bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE_BOUQUET_FLOWERS"))) {
            statement.setInt(1, bouquetId);
            statement.executeUpdate();
        }
    }

    private List<Flower> convert(ResultSet resultSet) throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        while (resultSet.next()) {
            Flower flower = null;
            switch (resultSet.getString("name")) {
                case "chamomile": {
                    Chamomile chamomile = new Chamomile(
                            resultSet.getInt("petals"),
                            resultSet.getInt("length"),
                            new BigDecimal(resultSet.getString("price")),
                            new FreshnessInteger(resultSet.getInt("freshness")));
                    chamomile.setId(resultSet.getInt("id"));
                    flower = chamomile;
                    break;
                }
                case "rose": {
                    Rose rose = new Rose(
                            resultSet.getBoolean("spike"),
                            resultSet.getInt("length"),
                            new BigDecimal(resultSet.getString("price")),
                            new FreshnessInteger(resultSet.getInt("freshness")));
                    rose.setId(resultSet.getInt("id"));
                    flower = rose;
                    break;
                }
                case "tulip": {
                    Tulip tulip = new Tulip();
                    tulip.setFreshness(new FreshnessInteger(resultSet.getInt("freshness")));
                    tulip.setLength(resultSet.getInt("length"));
                    tulip.setPrice(new BigDecimal(resultSet.getString("price")));
                    tulip.setId(resultSet.getInt("id"));
                    flower = tulip;
                    break;
                }
            }
            flowers.add(flower);
        }
        return flowers;
    }
}
