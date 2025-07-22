CREATE TABLE IF NOT EXISTS vehicles (
    plate VARCHAR(6) PRIMARY KEY,
    color VARCHAR(50),
    brand VARCHAR(50),
    model VARCHAR(50),
    owner VARCHAR(100),
    arrival TIMESTAMP
);