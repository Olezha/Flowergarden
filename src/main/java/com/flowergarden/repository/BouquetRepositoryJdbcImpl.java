package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.storage.JdbcConnectionPool;
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

    private static final String FIND_ONE_SQL = "SELECT * FROM bouquet WHERE id=?";

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

        return new LazyBouquet(flowerRepository, bouquets.get(0));
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
}
