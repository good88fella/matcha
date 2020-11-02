#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 --username admin --dbname matcha <<-EOSQL
	CREATE TABLE IF NOT EXISTS users
	(
    		user_id     serial,
    		login       VARCHAR(50) NOT NULL,
    		password    VARCHAR(256) NOT NULL,
    		email       VARCHAR(100) NOT NULL,
    		first_name  VARCHAR(50),
    		last_name   VARCHAR(50),
    		PRIMARY KEY (user_id)
	);
EOSQL
