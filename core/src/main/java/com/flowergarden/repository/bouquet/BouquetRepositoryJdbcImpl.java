package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.repository.flower.FlowerRepository;
import com.flowergarden.sql.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class BouquetRepositoryJdbcImpl implements BouquetRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FlowerRepository flowerRepository;
    private final SqlStatements sql;

    @Autowired
    public BouquetRepositoryJdbcImpl(
            JdbcTemplate jdbcTemplate,
            FlowerRepository flowerRepository,
            SqlStatements sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.flowerRepository = flowerRepository;
        this.sql = sql;
    }

    @Override
    public Bouquet saveOrUpdate(Bouquet bouquet) {
        if (bouquet == null)
            throw new IllegalArgumentException("Not persisted entity");

        if (bouquet.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql.get("BOUQUET_SAVE"),
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, bouquet.getName());
                preparedStatement.setBigDecimal(2, bouquet.getAssemblePrice());
                return preparedStatement;
            }, keyHolder);
            bouquet.setId(keyHolder.getKey().intValue());
        } else {
            jdbcTemplate.update(sql.get("BOUQUET_UPDATE"),
                    bouquet.getAssemblePrice(),
                    bouquet.getId());
        }
        flowerRepository.saveOrUpdateBouquetFlowers(bouquet.getFlowers(), bouquet.getId());

        return bouquet;
    }

    @Override
    public Bouquet<Flower> findOne(Integer id) {
        Bouquet<Flower> bouquet;
        try {
            bouquet = jdbcTemplate.queryForObject(sql.get("BOUQUET_FIND_ONE"), new BouquetMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return new LazyBouquet(flowerRepository, bouquet);
    }

    @Override
    public Bouquet<Flower> findOneEager(Integer id) {
        LazyBouquet lazyBouquet = (LazyBouquet) findOne(id);

        if (lazyBouquet == null)
            return null;

        lazyBouquet.getFlowers();
        return lazyBouquet.bouquet;
    }

    @Override
    public Iterable<Bouquet> findAll() {
        List<Bouquet> bouquets = jdbcTemplate.query(sql.get("BOUQUET_FIND_ALL"), new BouquetMapper());
        List<Bouquet> lazyBouquets = new ArrayList<>();
        for (Bouquet<Flower> bouquet : bouquets)
            lazyBouquets.add(new LazyBouquet(flowerRepository, bouquet));
        return lazyBouquets;
    }

    @Override
    public void delete(Integer id) {
        flowerRepository.deleteBouquetFlowers(id);
        jdbcTemplate.update(sql.get("BOUQUET_DELETE"), id);
    }

    @Override
    public void delete(Bouquet bouquet) {
        if (bouquet == null || bouquet.getId() == null)
            throw new IllegalArgumentException("Not persisted entity");
        delete(bouquet.getId());
    }

    @Override
    public void deleteAll() {
        flowerRepository.deleteAll();
        jdbcTemplate.update(sql.get("BOUQUET_DELETE_ALL"));
    }

    @Override
    public boolean exists(Integer id) {
        int count = jdbcTemplate.queryForObject(sql.get("BOUQUET_EXISTS"), Integer.class, id);
        return count > 0;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(sql.get("BOUQUET_COUNT"), Integer.class);
    }

    @Override
    public BigDecimal getBouquetPrice(int id) {
        // TODO: change money columns to INTEGER
        return jdbcTemplate.queryForObject(sql.get("BOUQUET_PRICE"), BigDecimal.class, id);
    }
}

class LazyBouquet implements Bouquet<Flower> {

    private FlowerRepository flowerRepository;

    Bouquet<Flower> bouquet;

    LazyBouquet(FlowerRepository flowerRepository, Bouquet<Flower> bouquet) {
        this.flowerRepository = flowerRepository;
        this.bouquet = bouquet;
    }

    private void loadFlowers() {
        for (Flower flower : flowerRepository.findBouquetFlowers(bouquet.getId()))
            bouquet.addFlower(flower);
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
