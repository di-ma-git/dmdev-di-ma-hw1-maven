--liquibase formatted sql

--changeset dmaltsev:1
ALTER TABLE users ALTER COLUMN email DROP NOT NULL;
--rollback ALTER TABLE users ALTER COLUMN email SET NOT NULL;
