CREATE TABLE food_item (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    food_item_category VARCHAR(255) NOT NULL,
    quantidade INTEGER NOT NULL,
    validade DATE
);