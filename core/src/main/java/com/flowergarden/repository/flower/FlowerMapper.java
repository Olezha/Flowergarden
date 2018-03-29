package com.flowergarden.repository.flower;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.flower.Tulip;
import com.flowergarden.model.property.FreshnessInteger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlowerMapper implements RowMapper<Flower> {

    @Override
    public Flower mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
        return flower;
    }
}
