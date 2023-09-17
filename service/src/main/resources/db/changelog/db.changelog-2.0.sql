--liquibase formatted sql

--changeset dmaltsev:1
ALTER TABLE users
ADD COLUMN created_at TIMESTAMP;
--rollback ALTER TABLE users DROP COLUMN created_at;

--changeset dmaltsev:2
ALTER TABLE users
ADD COLUMN modified_at TIMESTAMP;
--rollback ALTER TABLE users DROP COLUMN modified_at;

--changeset dmaltsev:3
ALTER TABLE users
ADD COLUMN created_by VARCHAR(32);
--rollback ALTER TABLE users DROP COLUMN created_by;

--changeset dmaltsev:4
ALTER TABLE users
ADD COLUMN last_modified_by VARCHAR(32);
--rollback ALTER TABLE users DROP COLUMN last_modified_by
