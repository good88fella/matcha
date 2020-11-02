#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER admin WITH PASSWORD 'pass';
    CREATE DATABASE matcha;
    GRANT ALL PRIVILEGES ON DATABASE matcha TO admin;
EOSQL
