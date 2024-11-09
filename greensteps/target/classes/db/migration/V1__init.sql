CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    registration_date DATE NOT NULL
    );

CREATE TABLE IF NOT EXISTS activity (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    carbon_emission FLOAT NOT NULL,
    date DATE NOT NULL,
    users_id INT REFERENCES users (id)
    );
