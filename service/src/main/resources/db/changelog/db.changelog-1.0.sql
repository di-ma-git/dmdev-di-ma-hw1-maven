--liquibase formatted sql

--changeset dmaltsev:1
CREATE TABLE IF NOT EXISTS active_substance
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR
    );
--rollback DROP TABLE active_substance;

--changeset dmaltsev:2
CREATE TABLE IF NOT EXISTS manufacturer
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    country     VARCHAR(255),
    image       VARCHAR(512),
    description VARCHAR
    );
--rollback DROP TABLE manufacturer;

--changeset dmaltsev:3
CREATE TABLE IF NOT EXISTS product_category
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR
    );
--rollback DROP TABLE product_category;

--changeset dmaltsev:4
CREATE TABLE IF NOT EXISTS product
(
    id                                     BIGSERIAL PRIMARY KEY,
    name                                   VARCHAR(255),
    image                                  VARCHAR(512),
    price                                  REAL,
    quantity_per_packaging                 INTEGER,
    quantity_active_substance_per_one_dose DOUBLE PRECISION,
    description                            VARCHAR,
    medicine_type                          VARCHAR(64),
    manufacturer_id                        INTEGER REFERENCES manufacturer (id),
    product_category_id                    INTEGER REFERENCES product_category (id)
    );
--rollback DROP TABLE product;

--changeset dmaltsev:5
CREATE TABLE IF NOT EXISTS users
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL UNIQUE,
    email            VARCHAR(255) NOT NULL UNIQUE,
    phone_number     VARCHAR(255) NOT NULL UNIQUE,
    password         VARCHAR,
    role             VARCHAR(64),
    delivery_address VARCHAR
    );
--rollback DROP TABLE users;

--changeset dmaltsev:6
CREATE TABLE IF NOT EXISTS product_active_substance
(
    id                  BIGSERIAL PRIMARY KEY,
    active_substance_id INTEGER REFERENCES active_substance (id),
    product_id          BIGINT REFERENCES product (id)
    );
--rollback DROP TABLE product_active_substance;

--changeset dmaltsev:7
CREATE TABLE IF NOT EXISTS orders
(
    id           BIGSERIAL PRIMARY KEY,
    customer_id      BIGINT REFERENCES users (id),
    order_status VARCHAR(64),
    order_date   TIMESTAMP,
    pay_date     TIMESTAMP,
    total_sum    DECIMAL,
    payment      VARCHAR(64)
    );
--rollback DROP TABLE orders;

--changeset dmaltsev:8
CREATE TABLE IF NOT EXISTS product_in_order
(
    id         BIGSERIAL PRIMARY KEY,
    quantity   INTEGER,
    order_id   BIGINT REFERENCES orders (id),
    product_id BIGINT REFERENCES product (id)
    );
--rollback DROP TABLE product_in_order;

