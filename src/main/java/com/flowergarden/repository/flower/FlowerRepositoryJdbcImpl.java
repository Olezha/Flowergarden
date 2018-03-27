package com.flowergarden.repository.flower;

import com.flowergarden.model.flower.*;
import com.flowergarden.sql.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.List;

@Repository
public class FlowerRepositoryJdbcImpl implements FlowerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SqlStatements sql;

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

        return saveOrUpdate(flower, null);
    }

    @Override
    @CachePut(value = "flower", key = "#flower.id")
    public Flower saveOrUpdate(Flower flower, Integer bouquetId) {
        if (flower == null)
            throw new IllegalArgumentException("Null entity");

        if (flower.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql.get("FLOWER_SAVE"),
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, flower.getClass().getSimpleName().toLowerCase());
                preparedStatement.setInt(2, flower.getLength());
                if (flower instanceof GeneralFlower && ((GeneralFlower) flower).getFreshness() != null)
                    preparedStatement.setInt(3, ((GeneralFlower) flower).getFreshness().getFreshness());
                else
                    preparedStatement.setNull(3, Types.INTEGER);
                preparedStatement.setBigDecimal(4, flower.getPrice());
                if (flower instanceof Chamomile)
                    preparedStatement.setInt(5, ((Chamomile) flower).getPetals());
                else
                    preparedStatement.setNull(5, Types.INTEGER);
                if (flower instanceof Rose)
                    preparedStatement.setBoolean(6, ((Rose) flower).getSpike());
                else
                    preparedStatement.setNull(6, Types.BOOLEAN);
                if (bouquetId != null)
                    preparedStatement.setInt(7, bouquetId);
                else
                    preparedStatement.setNull(7, Types.INTEGER);
                return preparedStatement;
            }, keyHolder);
            flower.setId((Integer) keyHolder.getKey());
        } else
            jdbcTemplate.update(sql.get("FLOWER_UPDATE"),
                    flower.getLength(),
                    flower instanceof GeneralFlower && ((GeneralFlower) flower).getFreshness() != null ?
                            ((GeneralFlower) flower).getFreshness().getFreshness() : null,
                    flower.getPrice(),
                    flower instanceof Chamomile ? ((Chamomile) flower).getPetals() : null,
                    bouquetId,
                    flower.getId());
        return flower;
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId) {
        for (Flower flower : flowers)
            saveOrUpdate(flower, bouquetId);
    }

    @Override
    @Cacheable("flower")
    public Flower findOne(Integer id) {
        return jdbcTemplate.queryForObject(sql.get("FLOWER_FIND_ONE"), new FlowerMapper(), id);
    }

    @Override
    public Iterable<Flower> findAll() {
        List<Flower> flowers = jdbcTemplate.query(sql.get("FLOWER_FIND_ALL"), new FlowerMapper());
        for (Flower flower : flowers)
            cachePut(flower);
        return flowers;
    }

    @Override
    public Iterable<Flower> findBouquetFlowers(int bouquetId) {
        List<Flower> flowers = jdbcTemplate.query(sql.get("FLOWER_FIND_BOUQUET_FLOWERS"), new FlowerMapper(), bouquetId);
        for (Flower flower : flowers)
            cachePut(flower);
        return flowers;
    }

    @Override
    @CacheEvict(value = "flower")
    public void delete(Integer id) {
        jdbcTemplate.update(sql.get("FLOWER_DELETE"), id);
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
        jdbcTemplate.update(sql.get("FLOWER_DELETE_ALL"));
    }

    @Override
    public boolean exists(Integer id) {
        int count = jdbcTemplate.queryForObject(sql.get("FLOWER_EXISTS"), Integer.class, id);
        return count > 0;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(sql.get("FLOWER_COUNT"), Integer.class);
    }

    @Override
    @CacheEvict(value = "flower", allEntries = true)
    public void deleteBouquetFlowers(int bouquetId) {
        jdbcTemplate.update(sql.get("FLOWER_DELETE_BOUQUET_FLOWERS"));
    }

    @CachePut(value = "flower", key = "#flower.id")
    public Flower cachePut(Flower flower) {
        return flower;
    }
}
