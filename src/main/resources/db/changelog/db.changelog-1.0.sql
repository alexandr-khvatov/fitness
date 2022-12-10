--liquibase formatted sql

--changeset kh:1
CREATE TABLE call_request
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_at  TIMESTAMP,
    created_by  VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    firstname   VARCHAR(255) NOT NULL,
    is_active   BOOLEAN      NOT NULL,
    lastname    VARCHAR(255) NOT NULL,
    phone       VARCHAR(255) NOT NULL
);

CREATE TABLE gym
(
    id                        BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    address                   VARCHAR(255),
    email                     VARCHAR(255),
    phone                     VARCHAR(255),
    inst_link                 VARCHAR(255),
    tg_link                   VARCHAR(255),
    vk_link                   VARCHAR(255),
    name                      VARCHAR(255)
        CONSTRAINT uk_rsoaa6kkpd3bt0cubbqhm4uo9
            UNIQUE,
    working_hours_on_weekdays VARCHAR(255),
    working_hours_on_weekends VARCHAR(255)
);

CREATE TABLE coach
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    birth_date     DATE,
    description    TEXT,
    email          VARCHAR(255),
    firstname      VARCHAR(255),
    image          VARCHAR(255),
    lastname       VARCHAR(255),
    patronymic     VARCHAR(255),
    phone          VARCHAR(255),
    specialization VARCHAR(255),
    gym_id         BIGINT NOT NULL
        CONSTRAINT fkkc9k9ldilr7y1ekmte66gtxoj
            REFERENCES gym
);

CREATE TABLE free_pass
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_at  TIMESTAMP,
    created_by  VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    date        DATE,
    email       VARCHAR(255) NOT NULL,
    end_time    TIME,
    firstname   VARCHAR(255) NOT NULL,
    is_done     BOOLEAN      NOT NULL,
    lastname    VARCHAR(255),
    phone       VARCHAR(255) NOT NULL,
    start_time  TIME,
    gym_id      BIGINT       NOT NULL
        CONSTRAINT fk4xmexiqmn5y4gnycn50jsf7mj
            REFERENCES gym
);

CREATE TABLE role
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE subscription
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    name   VARCHAR(255),
    period VARCHAR(255),
    price  INTEGER,
    gym_id BIGINT
        CONSTRAINT fk48guxfkmi8pxonloimixlbmq3
            REFERENCES gym
);

CREATE TABLE room
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    name   VARCHAR(255),
    gym_id BIGINT NOT NULL
        CONSTRAINT fks7y8kidqsi5y1pu6jme0670ki
            REFERENCES gym
);

CREATE TABLE training_program
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    description TEXT,
    image       VARCHAR(255),
    name        VARCHAR(255),
    overview    VARCHAR(255),
    gym_id      BIGINT NOT NULL
        CONSTRAINT fkovkmble71ootowf2kymjjc98h
            REFERENCES gym
);

CREATE TABLE sub_training_program
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    description         TEXT,
    image               VARCHAR(255),
    name                VARCHAR(255),
    overview            VARCHAR(255),
    training_program_id BIGINT NOT NULL
        CONSTRAINT fkf5b0twtcg951rpdr3of947ymi
            REFERENCES training_program
);

CREATE TABLE training
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    date                    TIMESTAMP,
    day_of_week             INTEGER,
    end_time                TIME,
    name                    VARCHAR(255),
    start_time              TIME,
    coach_id                BIGINT NOT NULL
        CONSTRAINT fke00hj3snley9f46qjq09wibe4
            REFERENCES coach,
    gym_id                  BIGINT
        CONSTRAINT fkdmj9bce4hvlranugnv81cott0
            REFERENCES gym,
    taken_seats             INTEGER,
    total_seats             INTEGER,
    room_id                 BIGINT
        CONSTRAINT fksjnlx9xxnserd9426685eqbkf
            REFERENCES room,
    sub_training_program_id BIGINT
        CONSTRAINT fkgdd7nnkxqy6kdwhmy5r7ofwn4
            REFERENCES sub_training_program
);

CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    birth_date DATE,
    email      VARCHAR(255),
    firstname  VARCHAR(255),
    image      VARCHAR(255),
    lastname   VARCHAR(255),
    password   VARCHAR(255),
    patronymic VARCHAR(255),
    phone      VARCHAR(255)
);

CREATE TABLE users_role
(
    user_id BIGINT NOT NULL
        CONSTRAINT fkqpe36jsen4rslwfx5i6dj2fy8
            REFERENCES users,
    role_id BIGINT NOT NULL
        CONSTRAINT fk3qjq7qsiigxa82jgk0i0wuq3g
            REFERENCES role,
    PRIMARY KEY (user_id, role_id)
);
