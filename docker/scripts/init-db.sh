#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER tt_user WITH ENCRYPTED PASSWORD 'tt_password';
    CREATE DATABASE training_tracker;
    GRANT ALL PRIVILEGES ON DATABASE training_tracker TO tt_user;
EOSQL