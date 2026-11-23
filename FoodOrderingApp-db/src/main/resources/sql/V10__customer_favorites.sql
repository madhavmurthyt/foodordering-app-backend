CREATE TABLE customer_favorite (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL REFERENCES customer(id) ON DELETE CASCADE,
    restaurant_id INTEGER NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT customer_favorite_unique UNIQUE (customer_id, restaurant_id)
);
