-- Script before creating table (if data base not exist) in data base when application is starting
CREATE TABLE IF NOT EXISTS wines
(
    id    BIGSERIAL PRIMARY KEY ,
    old_price FLOAT,
    new_price FLOAT,
    link VARCHAR(254),
    image VARCHAR(254),
    manufacturer VARCHAR(254),
    brand VARCHAR(64),
    country VARCHAR(32),
    capacity FLOAT,
    strength FLOAT,
    color VARCHAR(16),
    sugar VARCHAR(16),
    grape_sort VARCHAR(512),
    gastronomy VARCHAR(512),
    taste VARCHAR(512),
    flavour VARCHAR(512),
    rating FLOAT,
    sparkling BOOLEAN,
    wine_title VARCHAR(256)
);