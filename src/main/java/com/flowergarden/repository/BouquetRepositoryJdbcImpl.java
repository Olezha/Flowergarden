package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.sql.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class BouquetRepositoryJdbcImpl implements BouquetRepository {

    private JdbcConnectionPool connectionPool;

    private FlowerRepository flowerRepository;

    private static final String SAVE_SQL =
            "INSERT INTO bouquet (name, assemble_price) VALUES ('married', ?)";

    private static final String FIND_ONE_SQL = "SELECT * FROM bouquet WHERE id=?";

    private static final String FIND_ALL_SQL = "SELECT * FROM bouquet";

    private static final String BOUQUET_PRICE_SQL =
            "SELECT SUM(price) + b.assemble_price " +
                    "FROM bouquet b JOIN flower f ON b.id=f.bouquet_id WHERE b.id=?";

    private static final String UPDATE_SQL =
            "UPDATE bouquet SET assemble_price=? WHERE id=?";

    private static final String DELETE_SQL = "DELETE FROM bouquet WHERE id=?";

    private static final String DELETE_ALL_SQL = "DELETE FROM bouquet";

    @Autowired
    public BouquetRepositoryJdbcImpl(
            JdbcConnectionPool connectionPool,
            FlowerRepository flowerRepository) {
        this.connectionPool = connectionPool;
        this.flowerRepository = flowerRepository;
    }

    @Override
    public Bouquet saveOrUpdate(Bouquet bouquet) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            if (bouquet.getId() == null) {
                try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
                    statement.setBigDecimal(1, bouquet.getAssemblePrice());
                    statement.executeUpdate();
                    bouquet.setId(statement.getGeneratedKeys().getInt(1));
                }
            }
            else {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setBigDecimal(1, bouquet.getAssemblePrice());
                    statement.setInt(2, bouquet.getId());
                    statement.executeUpdate();
                }
            }
        }
        flowerRepository.saveOrUpdateBouquetFlowers(bouquet.getFlowers(), bouquet.getId());
        return bouquet;
    }

    @Override
    public Bouquet<Flower> findOne(int id) throws SQLException {
        List<Bouquet<Flower>> bouquets;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ONE_SQL)) {
            statement.setInt(1, id);
            bouquets = convert(statement.executeQuery());
        }

        if (bouquets.isEmpty())
            return null;

        if (bouquets.size() > 1)
            throw new SQLIntegrityConstraintViolationException("Unique id");

        return new LazyBouquet(flowerRepository, bouquets.get(0));
    }

    @Override
    public Iterable<Bouquet<Flower>> findAll() throws SQLException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            return convert(statement.executeQuery(FIND_ALL_SQL));
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        flowerRepository.deleteBouquetFlowers(id);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Bouquet bouquet) throws SQLException {
        if (bouquet.getId() == null)
            throw new IllegalArgumentException("Not persisted entity");
        delete(bouquet.getId());
    }

    @Override
    public void deleteAll() throws SQLException {
        flowerRepository.deleteAll();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL_SQL);
        }
    }

    @Override
    public BigDecimal getBouquetPrice(int bouquetId) throws SQLException {
        // TODO: change db money columns to INTEGER
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(BOUQUET_PRICE_SQL)) {
            statement.setInt(1, bouquetId);
            return new BigDecimal(statement.executeQuery().getString(1));
        }
    }

    private List<Bouquet<Flower>> convert(ResultSet resultSet) throws SQLException {
        List<Bouquet<Flower>> bouquets = new ArrayList<>();
        while (resultSet.next()) {
            Bouquet<Flower> bouquet = new MarriedBouquet();
            bouquet.setId(resultSet.getInt("id"));
            bouquet.setAssemblePrice(new BigDecimal(resultSet.getString("assemble_price")));
            bouquets.add(bouquet);
        }
        return bouquets;
    }
}

class LazyBouquet implements Bouquet<Flower> {

    private FlowerRepository flowerRepository;

    private Bouquet<Flower> bouquet;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    LazyBouquet(FlowerRepository flowerRepository, Bouquet<Flower> bouquet) {
        this.flowerRepository = flowerRepository;
        this.bouquet = bouquet;
    }

    private void loadFlowers() {
        try {
            for (Flower flower : flowerRepository.findBouquetFlowers(bouquet.getId()))
                bouquet.addFlower(flower);
        } catch (SQLException e) {
            log.error("{}", e);
        }
    }

    @Override
    public BigDecimal getPrice() {
        if (bouquet.getFlowers().isEmpty())
            loadFlowers();
        return bouquet.getPrice();
    }

    @Override
    public void addFlower(Flower flower) {
        if (bouquet.getFlowers().isEmpty())
            loadFlowers();
        bouquet.addFlower(flower);
    }

    @Override
    public Collection<Flower> searchFlowersByLength(int start, int end) {
        if (bouquet.getFlowers().isEmpty())
            loadFlowers();
        return bouquet.searchFlowersByLength(start, end);
    }

    @Override
    public void sortByFreshness() {
        if (bouquet.getFlowers().isEmpty())
            loadFlowers();
        bouquet.sortByFreshness();
    }

    @Override
    public Collection<Flower> getFlowers() {
        if (bouquet.getFlowers().isEmpty())
            loadFlowers();
        return bouquet.getFlowers();
    }

    @Override
    public String getName() {
        return bouquet.getName();
    }

    @Override
    public void setAssemblePrice(BigDecimal assemblePrice) {
        bouquet.setAssemblePrice(assemblePrice);
    }

    @Override
    public Integer getId() {
        return bouquet.getId();
    }

    @Override
    public void setId(Integer id) {
        bouquet.setId(id);
    }

    @Override
    public BigDecimal getAssemblePrice() {
        return bouquet.getAssemblePrice();
    }
}
