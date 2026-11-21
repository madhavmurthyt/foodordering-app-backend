CREATE TABLE invite_tokens (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(200) NOT NULL,
    token VARCHAR(200) NOT NULL UNIQUE,
    restaurant_id INTEGER NOT NULL REFERENCES restaurant(id),
    expires_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    used_by INTEGER REFERENCES customer(id),
    CONSTRAINT invite_tokens_uuid_unique UNIQUE (uuid)
);
