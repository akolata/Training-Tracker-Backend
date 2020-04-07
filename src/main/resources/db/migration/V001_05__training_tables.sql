-----------------------------------
--- exercise
-----------------------------------
CREATE SEQUENCE training_seq INCREMENT BY 1;

CREATE TABLE training
(
    id              int8        NOT NULL,
    additional_info varchar(255),
    date            date        NOT NULL,
    name            varchar(255),
    gym_id          int8,
    user_id         int8,
    uuid            varchar(36) NOT NULL,
    version         timestamp   NOT NULL,
    created_at      timestamp   NOT NULL,
    updated_at      timestamp   NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX training_uid_uk ON training (uuid);
CREATE INDEX training_gym_id_idx ON training(gym_id);
CREATE INDEX training_user_id_idx ON training(user_id);

ALTER TABLE training
    ADD CONSTRAINT training_gym_fk
        FOREIGN KEY (gym_id)
            REFERENCES gym;

ALTER TABLE training
    ADD CONSTRAINT training_user_fk
        FOREIGN KEY (user_id)
            REFERENCES tt_user;

-----------------------------------
--- training_set
-----------------------------------
CREATE SEQUENCE training_set_seq INCREMENT BY 1;

CREATE TABLE training_set
(
    id                  int8        NOT NULL,
    additional_info     varchar(255),
    calories            int4,
    distance_in_km      float8,
    duration_in_minutes int4,
    reps                int4,
    weight              int4,
    exercise_id         int8,
    training_id         int8,
    uuid                varchar(36) NOT NULL,
    version             timestamp   NOT NULL,
    created_at          timestamp   NOT NULL,
    updated_at          timestamp   NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX training_set_uuid_uk ON training_set (uuid);
CREATE INDEX training_set_training_id_idx ON training_set(training_id);
CREATE INDEX training_set_exercise_id_idx ON training_set(exercise_id);

ALTER TABLE training_set
    ADD CONSTRAINT training_set_exercise_fk
        FOREIGN KEY (exercise_id)
            REFERENCES exercise;


ALTER TABLE training_set
    ADD CONSTRAINT training_set_training_fk
        FOREIGN KEY (training_id)
            REFERENCES training;