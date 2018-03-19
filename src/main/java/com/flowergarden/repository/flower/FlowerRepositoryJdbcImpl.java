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
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private final DataSource dataSource;
    private final SqlStatements sql;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FlowerRepositoryJdbcImpl(
            DataSource dataSource,
            SqlStatements sql) {
        this.dataSource = dataSource;
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

        try (Connection connection = dataSource.getConnection()) {
            if (flower.getId() == null) {
                try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_SAVE"))) {
                    statement.setString(1, flower.getClass().getSimpleName().toLowerCase());
                    statement.setInt(2, flower.getLength());
                    if (flower instanceof GeneralFlower) {
                        GeneralFlower generalFlower = (GeneralFlower) flower;
                        statement.setInt(3, generalFlower.getFreshness().getFreshness());
                    } else
                        statement.setNull(3, Types.INTEGER);
                    statement.setBigDecimal(4, flower.getPrice());
                    if (flower instanceof Chamomile) {
                        Chamomile chamomile = (Chamomile) flower;
                        statement.setInt(5, chamomile.getPetals());
                    } else
                        statement.setNull(5, Types.INTEGER);
                    if (flower instanceof Rose) {
                        Rose rose = (Rose) flower;
                        statement.setBoolean(6, rose.getSpike());
                    } else
                        statement.setNull(6, Types.BOOLEAN);
                    statement.setInt(7, bouquetId);
                    statement.executeUpdate();

                    flower.setId(statement.getGeneratedKeys().getInt(1));
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_UPDATE"))) {
                    statement.setInt(1, flower.getLength());
                    if (flower instanceof GeneralFlower) {
                        GeneralFlower generalFlower = (GeneralFlower) flower;
                        statement.setInt(2, generalFlower.getFreshness().getFreshness());
                    } else
                        statement.setNull(2, Types.INTEGER);
                    statement.setBigDecimal(3, flower.getPrice());
                    if (flower instanceof Chamomile) {
                        Chamomile chamomile = (Chamomile) flower;
                        statement.setInt(4, chamomile.getPetals());
                    } else
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
    public Flower findOne(Integer id) {
        List<Flower> flowers;

        try {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_ONE"))) {
                statement.setInt(1, id);
                flowers = convert(statement.executeQuery());
            }

            if (flowers.isEmpty())
                return null;

            if (flowers.size() > 1)
                throw new SQLIntegrityConstraintViolationException("Unique id");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return flowers.get(0);
    }

    @Override
    public Iterable<Flower> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            Iterable<Flower> flowers = convert(statement.executeQuery(sql.get("FLOWER_FIND_ALL")));
            for (Flower flower : flowers)
                cachePut(flower);
            return flowers;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Iterable<Flower> findBouquetFlowers(int bouquetId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_BOUQUET_FLOWERS"))) {
            statement.setInt(1, bouquetId);
            ResultSet resultSet = statement.executeQuery();
            List<Flower> flowers = new ArrayList<>();
            while (resultSet.next()) {
                GeneralFlower flower = new GeneralFlower() {
                };
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
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE"))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
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
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.get("FLOWER_DELETE_ALL"));
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE_BOUQUET_FLOWERS"))) {
            statement.setInt(1, bouquetId);
            statement.executeUpdate();
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "flower", key = "#from.id"),
            @CacheEvict(value = "flower", key = "#to.id")
    })
    public void transferPartOfPrice(Flower from, Flower to, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || from.getPrice().compareTo(amount) < 0)
            throw new IllegalArgumentException();

        try (Connection connection = dataSource.getConnection()) {
            int connectionTransactionIsolation;

            connection.setAutoCommit(false);
            connectionTransactionIsolation = connection.getTransactionIsolation();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            if (databaseMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            else if (databaseMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ))
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            else
                throw new RuntimeException("Current settings do not allow work with money");

            try (PreparedStatement fromStatement = connection.prepareStatement(sql.get("FLOWER_TRANSFER_PART_OF_PRICE_FROM"));
                 PreparedStatement toStatement = connection.prepareStatement(sql.get("FLOWER_TRANSFER_PART_OF_PRICE_TO"))) {
                fromStatement.setBigDecimal(1, amount);
                fromStatement.setInt(2, from.getId());
                fromStatement.executeUpdate();

                toStatement.setBigDecimal(1, amount);
                toStatement.setInt(2, to.getId());
                toStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                log.debug("{}", e);
                connection.rollback();
                throw e;
            }

            connection.setAutoCommit(true);
            connection.setTransactionIsolation(connectionTransactionIsolation);
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
