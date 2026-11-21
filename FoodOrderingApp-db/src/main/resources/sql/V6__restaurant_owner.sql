ALTER TABLE restaurant ADD COLUMN owner_id INTEGER REFERENCES customer(id);
