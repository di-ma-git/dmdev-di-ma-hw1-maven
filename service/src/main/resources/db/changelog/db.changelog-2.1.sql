--liquibase formatted sql

--changeset dmaltsev:1
CREATE TABLE IF NOT EXISTS revision
(
    id SERIAL   PRIMARY KEY,
    timestamp   BIGINT NOT NULL
);
--rollback DROP TABLE revision

--changeset dmaltsev:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id               BIGINT,
    rev             INT REFERENCES revision (id),
    revtype         SMALLINT,
    name             VARCHAR(255) NOT NULL UNIQUE,
    email            VARCHAR(255) NOT NULL UNIQUE,
    phone_number     VARCHAR(255) NOT NULL UNIQUE,
    password         VARCHAR,
    role             VARCHAR(32),
    delivery_address VARCHAR
);
--rollback DROP TABLE user_aud