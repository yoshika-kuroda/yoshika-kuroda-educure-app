DROP TABLE IF EXISTS words CASCADE;
DROP TABLE IF EXISTS word_categories;
DROP TABLE IF EXISTS categories_wordbookapp CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(50) NOT  NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS words (
    word_id BIGSERIAL PRIMARY KEY,
    word VARCHAR(100) NOT  NULL,
    meaning TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS categories_wordbookapp (
    category_id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT  NULL
);

CREATE TABLE word_categories (
    word_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
     PRIMARY KEY (word_id, category_id),
    FOREIGN KEY (word_id) REFERENCES words(word_id) ON DELETE CASCADE, 
    FOREIGN KEY (category_id) REFERENCES categories_wordbookapp(category_id) ON DELETE CASCADE
);