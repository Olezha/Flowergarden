package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

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
                    .getResourceAsStream("db/statements.sql.yml")) {
                sqlStatements = new Yaml().load(inputStream);
            }
        } catch (IOException | YAMLException e) {
            log.debug("{}", e);
            log.error("Unable to start program (code 512)");
            System.exit(512);
        }
    }

    public String get(String key) {
        return sqlStatements.get(key);
    }
}
