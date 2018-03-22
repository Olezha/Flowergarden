package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Flower;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BouquetMapper implements RowMapper<Bouquet> {

    @Override
    public Bouquet<Flower> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Bouquet<Flower> bouquet = new MarriedBouquet();
        bouquet.setId(resultSet.getInt("id"));
        bouquet.setAssemblePrice(resultSet.getBigDecimal("assemble_price"));
        return bouquet;
    }
}
