--liquibase formatted sql

--changeset dmaltsev:1
ALTER TABLE users ADD COLUMN image VARCHAR(64);
--rollback ALTER TABLE users DROP COLUMN image;

--changeset dmaltsev:2
ALTER TABLE users_aud ADD COLUMN image VARCHAR(64);
--rollback ALTER TABLE users_aud DROP COLUMN image;

