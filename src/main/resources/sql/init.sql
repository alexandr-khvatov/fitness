DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(64)  NOT NULL,
    patronymic VARCHAR(64)  NOT NULL,
    lastname   VARCHAR(64)  NOT NULL,
    email      VARCHAR(128) NOT NULL UNIQUE,
    password   VARCHAR(128) NOT NULL,
    phone      VARCHAR(12)  NOT NULL UNIQUE,
    birth_date DATE
);


ALTER TABLE users
    ADD COLUMN image VARCHAR(255);

DROP TABLE IF EXISTS role;
CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS users_role;
CREATE TABLE IF NOT EXISTS users_role
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES role (id),
    UNIQUE (user_id, role_id)
);

SELECT *
FROM users