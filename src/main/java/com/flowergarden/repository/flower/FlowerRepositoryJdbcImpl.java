package com.flowergarden.repository.flower;

import com.flowergarden.model.flowers.*;
import com.flowergarden.model.properties.FreshnessInteger;
import com.flowergarden.repository.DataAccessException;
import com.flowergarden.sql.SqlStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SqlStatements sql;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FlowerRepositoryJdbcImpl(
            JdbcTemplate jdbcTemplate,
            SqlStatements sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
    }

    @Override
    @CachePut(value = "flower", key = "#flower.id")
    public Flower saveOrUpdate(Flower flower) {
        if (flower == null)
            throw new IllegalArgumentException("Null entity");

        try {
            return saveOrUpdate(flower, null);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    @CachePut(value = "flower", key = "#flower.id")
    public Flower saveOrUpdate(Flower flower, Integer bouquetId) throws SQLException {
        if (flower == null)
            throw new IllegalArgumentException("Null entity");

            if (flower.getId() == null) {
                // TODO sql.get("FLOWER_SAVE")
                throw new UnsupportedOperationException("Not implemented, yet");
            } else {
                // TODO sql.get("FLOWER_UPDATE")
                throw new UnsupportedOperationException("Not implemented, yet");
            }
//        return flower;
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId) throws SQLException {
        for (Flower flower : flowers)
            saveOrUpdate(flower, bouquetId);
    }

    @Override
    @Cacheable("flower")
    public Flower findOne(Integer id) {
        return jdbcTemplate.queryForObject(sql.get("FLOWER_FIND_ONE"),
                new FlowerMapper(), id);
    }

    @Override
    public Iterable<Flower> findAll() {
        throw new UnsupportedOperationException("Not implemented, yet");
//        try (Connection connection = dataSource.getConnection();
//             Statement statement = connection.createStatement()) {
//            Iterable<Flower> flowers = convert(statement.executeQuery(sql.get("FLOWER_FIND_ALL")));
//            for (Flower flower : flowers)
//                cachePut(flower);
//            return flowers;
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
    }

    @Override
    public Iterable<Flower> findBouquetFlowers(int bouquetId) throws SQLException {
        throw new UnsupportedOperationException("Not implemented, yet");
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_BOUQUET_FLOWERS"))) {
//            statement.setInt(1, bouquetId);
//            ResultSet resultSet = statement.executeQuery();
//            List<Flower> flowers = new ArrayList<>();
//            while (resultSet.next()) {
//                GeneralFlower flower = new GeneralFlower() {
//                };
//                flower.setId(resultSet.getInt("id"));
//                flower.setFreshness(new FreshnessInteger(resultSet.getInt("freshness")));
//                flower.setPrice(resultSet.getBigDecimal("price"));
//                flower.setLength(resultSet.getInt("length"));
//                flowers.add(flower);
//                cachePut(flower);
//            }
//            return flowers;
//        }
    }

    @Override
    @CacheEvict(value = "flower")
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented, yet");
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE"))) {
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
    }

    @Override
    @CacheEvict(value = "flower", key = "#flower.id")
    public void delete(Flower flower) {
        if (flower == null || flower.getId() == null)
            throw new IllegalArgumentException("Not persisted entity");
        delete(flower.getId());
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented, yet");
//        try (Connection connection = dataSource.getConnection();
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate(sql.get("FLOWER_DELETE_ALL"));
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
    }

    @Override
    public boolean exists(Integer integer) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void deleteBouquetFlowers(int bouquetId) throws SQLException {
        throw new UnsupportedOperationException("Not implemented, yet");
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE_BOUQUET_FLOWERS"))) {
//            statement.setInt(1, bouquetId);
//            statement.executeUpdate();
//        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "flower", key = "#from.id"),
            @CacheEvict(value = "flower", key = "#to.id")
    })
    public void transferPartOfPrice(Flower from, Flower to, BigDecimal amount) throws SQLException {
        throw new UnsupportedOperationException("Not implemented, yet");
//        if (amount.compareTo(BigDecimal.ZERO) < 0 || from.getPrice().compareTo(amount) < 0)
//            throw new IllegalArgumentException();
//
//        try (Connection connection = dataSource.getConnection()) {
//            int connectionTransactionIsolation;
//
//            connection.setAutoCommit(false);
//            connectionTransactionIsolation = connection.getTransactionIsolation();
//            DatabaseMetaData databaseMetaData = connection.getMetaData();
//            if (databaseMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
//                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//            else if (databaseMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ))
//                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//            else
//                throw new RuntimeException("Current settings do not allow work with money");
//
//            try (PreparedStatement fromStatement = connection.prepareStatement(sql.get("FLOWER_TRANSFER_PART_OF_PRICE_FROM"));
//                 PreparedStatement toStatement = connection.prepareStatement(sql.get("FLOWER_TRANSFER_PART_OF_PRICE_TO"))) {
//                fromStatement.setBigDecimal(1, amount);
//                fromStatement.setInt(2, from.getId());
//                fromStatement.executeUpdate();
//
//                toStatement.setBigDecimal(1, amount);
//                toStatement.setInt(2, to.getId());
//                toStatement.executeUpdate();
//
//                connection.commit();
//            } catch (SQLException e) {
//                log.debug("{}", e);
//                connection.rollback();
//                throw e;
//            }
//
//            connection.setAutoCommit(true);
//            connection.setTransactionIsolation(connectionTransactionIsolation);
//        }
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

    @CachePut(value = "flower", key = "#flower.id")
    public Flower cachePut(Flower flower) {
        return flower;
    }
}
