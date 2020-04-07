-----------------------------------
--- gym
-----------------------------------
CREATE SEQUENCE gym_seq INCREMENT BY 1;

CREATE TABLE gym (
    id int8 NOT NULL,
    name varchar(255) NOT NULL,
    uuid varchar(36) NOT NULL,
    version timestamp NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX gym_uuid_uk ON gym(uuid);
CREATE UNIQUE INDEX gym_name_uk ON gym(name);
