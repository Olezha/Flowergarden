# Flowergarden [:information_source:](https://github.com/Olezha/Flowergarden/wiki) [![Build Status](https://travis-ci.org/Olezha/Flowergarden.svg?branch=master)](https://travis-ci.org/Olezha/Flowergarden) [![codecov](https://codecov.io/gh/Olezha/Flowergarden/branch/master/graph/badge.svg)](https://codecov.io/gh/Olezha/Flowergarden)

for launching: `mvn clean install && java -jar ./web/target/flowergarden-web-0.0.1-exec.jar`  
web application will be available at the following url: http://localhost:8080  
for example `curl localhost:8080/bouquet?id=1`

#### Changelog:
- lesson 6 HW
    - Remove custom DB Connection Pull & refactor repository impl for work through JdbcTemplate.  
    Old stage is placed in a separate branch [jdbcp](https://github.com/Olezha/Flowergarden/tree/jdbcp).
