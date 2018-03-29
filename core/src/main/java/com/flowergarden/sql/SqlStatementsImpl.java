package com.flowergarden.sql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Component
public class SqlStatementsImpl implements SqlStatements {

    private Map<String, String> sqlStatements;

    public SqlStatementsImpl() {
        this("db/statements.sql.yml");
    }

    public SqlStatementsImpl(String sqlYmlResourceName) {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(sqlYmlResourceName)) {
            sqlStatements = new Yaml().load(inputStream);
        } catch (IOException | YAMLException e) {
            log.debug("{}", e);
            log.error("Unable to start program (code 512)");
            System.exit(512);
        }
    }

    @Override
    public String get(String key) {
        return sqlStatements.get(key);
    }
}
