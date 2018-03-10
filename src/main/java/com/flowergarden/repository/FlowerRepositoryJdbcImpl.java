package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.properties.FreshnessInteger;
import com.flowergarden.sql.JdbcConnectionPool;
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

    private static final String SAVE_SQL =
            "INSERT INTO flower " +
                    "(name, length, freshness, price, petals, spike, bouquet_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_ONE_SQL = "SELECT * FROM flower WHERE id=?";

    private static final String FIND_ALL_SQL = "SELECT * FROM flower";

    private static final String FIND_BOUQUET_FLOWERS_SQL =
            "SELECT * FROM flower WHERE bouquet_id=?";

    private static final String UPDATE_SQL =
            "UPDATE flower SET " +
                    "length=?, freshness=?, price=?, petals=?, bouquet_id=? WHERE id=?";

    private static final String DELETE_SQL = "DELETE FROM flower WHERE id=?";

    private static final String DELETE_ALL_SQL = "DELETE FROM flower";

    private static final String DELETE_BOUQUET_FLOWERS_SQL =
            "DELETE FROM flower WHERE bouquet_id=?";

    @Autowired
    public FlowerRepositoryJdbcImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Flower saveOrUpdate(Flower flower) throws SQLException {
        return saveOrUpdate(flower, null);
    }

    @Override
    public Flower saveOrUpdate(Flower flower, Integer bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            if (flower.getId() == null) {
                try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
                    // TODO
//                    statement.setString(1, name);
                    statement.setInt(2, flower.getLength());
//                    statement.setInt(3, flower.getFreshness().getFreshness());
                    statement.setBigDecimal(4, flower.getPrice());
//                    statement.setInt(5, petals);
//                    statement.setBoolean(6, spike);
                    statement.setInt(7, bouquetId);
                    statement.executeUpdate();
                    flower.setId(statement.getGeneratedKeys().getInt(1));
                }
            }
            else {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
//                    length=?, freshness=?, price=?, petals=?, bouquet_id=? WHERE id=?
                    // TODO
                    statement.setInt(1, flower.getLength());
//                    statement.setInt(2, flower.getFreshness().getFreshness());
                    statement.setBigDecimal(3, flower.getPrice());
//                    statement.setInt(4, petals);
//                    statement.setInt(5, bouquet.id);
                    statement.setInt(6, flower.getId());
                    statement.executeUpdate();
                }
            }
        }
        return null;
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
             PreparedStatement statement = connection.prepareStatement(FIND_ONE_SQL)) {
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
            return convert(statement.executeQuery(FIND_ALL_SQL));
        }
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
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
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
            statement.executeUpdate(DELETE_ALL_SQL);
        }
    }

    @Override
    public void deleteBouquetFlowers(int bouquetId) throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOUQUET_FLOWERS_SQL)) {
            statement.setInt(1, bouquetId);
            statement.executeUpdate();
        }
    }

    private List<Flower> convert(ResultSet resultSet) throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        while (resultSet.next()) {
            // TODO
//            Flower flower = new Flower();
//            flower.setId(resultSet.getInt("id"));
//            flower.setPrice(new BigDecimal(resultSet.getString("price")));
//            ...
//            flowers.add(flower);
        }
        return flowers;
    }
}
