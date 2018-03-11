package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class SqlStatements {

    private Map<String, String> sqlStatements;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SqlStatements() {
        try {
            try (InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("statements.sql.yml")) {
                sqlStatements = new Yaml().load(inputStream);
            }
        } catch (IOException e) {
            log.error("{}", e);
            log.info("Unable to start program (code 256)");
            System.exit(256);
        }
    }

    public String get(String key) {
        return sqlStatements.get(key);
    }
}
