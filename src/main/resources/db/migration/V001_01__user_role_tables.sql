-----------------------------------
--- tt_role
-----------------------------------
CREATE SEQUENCE tt_role_seq INCREMENT BY 1;

CREATE TABLE tt_role (
    id int8 NOT NULL,
    name varchar(32) NOT NULL,
    uuid varchar(36) NOT NULL,
    version timestamp NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX tt_role_name_uk ON tt_role(name);
CREATE UNIQUE INDEX tt_role_uuid_uk ON tt_role(uuid);

-----------------------------------
--- tt_user
-----------------------------------
CREATE SEQUENCE tt_user_seq INCREMENT BY 1;

CREATE TABLE tt_user (
    id int8 NOT NULL,
    username varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    account_expired boolean NOT NULL,
    account_locked boolean NOT NULL,
    credentials_expired boolean NOT NULL,
    enabled boolean NOT NULL,
    uuid varchar(36) NOT NULL,
    version timestamp NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX tt_user_uuid_uk ON tt_user(uuid);
CREATE UNIQUE INDEX tt_user_username_uk ON tt_user(username);
CREATE UNIQUE INDEX tt_user_email_uk ON tt_user(email);

-----------------------------------
--- user_role
-----------------------------------
CREATE TABLE user_role (
    user_id int8 NOT NULL,
    role_id int8 NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE user_role
ADD CONSTRAINT fk_role_user
FOREIGN KEY (role_id)
REFERENCES tt_role;

ALTER TABLE user_role
ADD CONSTRAINT fk_user_role
FOREIGN KEY(user_id)
REFERENCES tt_user;