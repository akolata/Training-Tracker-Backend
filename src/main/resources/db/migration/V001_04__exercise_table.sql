-----------------------------------
--- exercise
-----------------------------------
CREATE SEQUENCE exercise_seq INCREMENT BY 1;

CREATE TABLE exercise (
    id int8 NOT NULL,
    name varchar(255) NOT NULL,
    type varchar(64) NOT NULL,
    uuid varchar(36) NOT NULL,
    version timestamp NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX exercise_uuid_uk ON exercise(uuid);
CREATE UNIQUE INDEX exercise_name_type_uk ON exercise(name, type);
