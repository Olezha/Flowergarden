-- Table: bouquet
CREATE TABLE bouquet (id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), assemble_price DECIMAL);
INSERT INTO bouquet (id, name, assemble_price) VALUES (1, 'married', 12.7);

-- Table: flower
CREATE TABLE flower (id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), length INTEGER, freshness INTEGER, price DECIMAL, petals INTEGER, spike BOOLEAN, bouquet_id INTEGER);
INSERT INTO flower (id, name, length, freshness, price, petals, spike, bouquet_id) VALUES (1, 'chamomile', 10, 1, 12.5, 6, NULL, 1);
INSERT INTO flower (id, name, length, freshness, price, petals, spike, bouquet_id) VALUES (2, 'rose', 12, 1, 15.6, NULL, 1, 1);
INSERT INTO flower (id, name, length, freshness, price, petals, spike, bouquet_id) VALUES (3, 'rose', 11, 2, 15.2, NULL, 1, 1);
INSERT INTO flower (id, name, length, freshness, price, petals, spike, bouquet_id) VALUES (4, 'rose', 15, 1, 13, NULL, 1, 1);
INSERT INTO flower (id, name, length, freshness, price, petals, spike, bouquet_id) VALUES (5, 'rose', 15, 1, 13, NULL, 1, 1);
INSERT INTO flower (id, name, length, freshness, price, petals, spike, bouquet_id) VALUES (6, 'rose', 15, 1, 13, NULL, 1, 1);
