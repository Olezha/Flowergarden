package com.flowergarden.service;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface BouquetService {

    BigDecimal getBouquetPrice(Integer id);
}
