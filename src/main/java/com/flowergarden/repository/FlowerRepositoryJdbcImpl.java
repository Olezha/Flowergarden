package com.flowergarden.repository;

import com.flowergarden.model.flowers.*;
import com.flowergarden.model.properties.FreshnessInteger;
import com.flowergarden.sql.Connection;
import com.flowergarden.sql.ConnectionPool;
import com.flowergarden.sql.SqlStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private ConnectionPool connectionPool;
    private SqlStatements sql;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FlowerRepositoryJdbcImpl(
            ConnectionPool connectionPool,
            SqlStatements sql) {
        this.connectionPool = connectionPool;
        this.sql = sql;
    }

    @Override
    @CachePut(value = "flower", key = "#flower.id")
    public Flower saveOrUpdate(Flower flower) throws SQLException {
        if (flower == null)
            throw new IllegalArgumentException("Null entity");

        return saveOrUpdate(flower, null);
    }

    @Override
    @CachePut(value = "flower", key = "#flower.id")
    public Flower saveOrUpdate(Flower flower, Integer bouquetId) throws SQLException {
        if (flower == null)
            throw new IllegalArgumentException("Null entity");

        try (Connection connection = connectionPool.getConnection()) {
            if (flower.getId() == null) {
                try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_SAVE"))) {
                    statement.setString(1, flower.getClass().getSimpleName().toLowerCase());
                    statement.setInt(2, flower.getLength());
                    if (flower instanceof GeneralFlower) {
                        GeneralFlower generalFlower = (GeneralFlower) flower;
                        statement.setInt(3, generalFlower.getFreshness().getFreshness());
                    }
                    else
                        statement.setNull(3, Types.INTEGER);
                    statement.setBigDecimal(4, flower.getPrice());
                    if (flower instanceof Chamomile) {
                        Chamomile chamomile = (Chamomile) flower;
                        statement.setInt(5, chamomile.getPetals());
                    }
                    else
                        statement.setNull(5, Types.INTEGER);
                    if (flower instanceof Rose) {
                        Rose rose = (Rose) flower;
                        statement.setBoolean(6, rose.getSpike());
                    }
                    else
                        statement.setNull(6, Types.BOOLEAN);
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
                    else
                        statement.setNull(2, Types.INTEGER);
                    statement.setBigDecimal(3, flower.getPrice());
                    if (flower instanceof Chamomile) {
                        Chamomile chamomile = (Chamomile) flower;
                        statement.setInt(4, chamomile.getPetals());
                    }
                    else
                        statement.setNull(4, Types.INTEGER);
                    if (bouquetId != null)
                        statement.setInt(5, bouquetId);
                    else
                        statement.setNull(5, Types.INTEGER);
                    statement.setInt(6, flower.getId());
                    statement.executeUpdate();
                }
            }
        }
        return flower;
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId) throws SQLException {
        for (Flower flower : flowers)
            saveOrUpdate(flower, bouquetId);
    }

    @Override
    @Cacheable("flower")
    public Flower findOne(int id) throws SQLException {
        List<Flower> flowers;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_ONE"))) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            log.warn("flower id {}, col 5 name {}; col 5 type {}; total cols {}",
                    id,
                    resultSetMetaData.getColumnName(5),
                    resultSetMetaData.getColumnType(5),
                    resultSetMetaData.getColumnCount());
            flowers = convert(resultSet);
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
            Iterable<Flower> flowers = convert(statement.executeQuery(sql.get("FLOWER_FIND_ALL")));
            for (Flower flower : flowers)
                cachePut(flower);
            return flowers;
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
                flower.setPrice(resultSet.getBigDecimal("price"));
                flower.setLength(resultSet.getInt("length"));
                flowers.add(flower);
                cachePut(flower);
            }
            return flowers;
        }
    }

    @Override
    @CacheEvict(value = "flower")
    public void delete(int id) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE"))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    @CacheEvict(value = "flower", key = "#flower.id")
    public void delete(Flower flower) throws SQLException {
        if (flower == null || flower.getId() == null)
            throw new IllegalArgumentException("Not persisted entity");
        delete(flower.getId());
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void deleteAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.get("FLOWER_DELETE_ALL"));
        }
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void deleteBouquetFlowers(int bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE_BOUQUET_FLOWERS"))) {
            statement.setInt(1, bouquetId);
            statement.executeUpdate();
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "flower", key = "#fromFlowerId"),
            @CacheEvict(value = "flower", key = "#toFlowerId")
    })
    public void movePartOfPrice(int fromFlowerId, int toFlowerId, BigDecimal val) throws SQLException {
        // TODO transaction
        java.sql.Connection connection = null;

        connection.setAutoCommit(false);

        // try 123
        connection.commit();
        // catch ex
        connection.rollback();

        connection.setAutoCommit(true);
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
                            resultSet.getBigDecimal("price"),
                            new FreshnessInteger(resultSet.getInt("freshness")));
                    chamomile.setId(resultSet.getInt("id"));
                    flower = chamomile;
                    break;
                }
                case "rose": {
                    Rose rose = new Rose(
                            resultSet.getBoolean("spike"),
                            resultSet.getInt("length"),
                            resultSet.getBigDecimal("price"),
                            new FreshnessInteger(resultSet.getInt("freshness")));
                    rose.setId(resultSet.getInt("id"));
                    flower = rose;
                    break;
                }
                case "tulip": {
                    Tulip tulip = new Tulip();
                    tulip.setFreshness(new FreshnessInteger(resultSet.getInt("freshness")));
                    tulip.setLength(resultSet.getInt("length"));
                    tulip.setPrice(resultSet.getBigDecimal("price"));
                    tulip.setId(resultSet.getInt("id"));
                    flower = tulip;
                    break;
                }
            }
            flowers.add(flower);
        }
        return flowers;
    }

    @CacheEvict(value = "flower", key = "#id")
    public void cacheEvict(int id) {}

    @CachePut(value = "flower", key = "#flower.id")
    public Flower cachePut(Flower flower) {
        return flower;
    }
}
