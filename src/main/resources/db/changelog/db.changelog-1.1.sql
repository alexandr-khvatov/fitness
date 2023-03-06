--liquibase formatted sql

--changeset kh:1
ALTER TABLE users
    ADD gym_id BIGINT NOT NULL CONSTRAINT users_gym_fk_c REFERENCES gym(id);
--rollback ALTER TABLE users DROP gym_id;