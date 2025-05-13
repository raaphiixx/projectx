-- First, clear all tables
DROP TABLE IF EXISTS post_likes, user_follow, posts, users CASCADE;

-- Create tables
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    owner_id INT NOT NULL REFERENCES users(id)
);

CREATE TABLE user_follow (
    id SERIAL PRIMARY KEY,
    following_id INT NOT NULL REFERENCES users(id),
    followed_id INT NOT NULL REFERENCES users(id),
    UNIQUE (following_id, followed_id)
);

CREATE TABLE post_likes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    post_id INT NOT NULL REFERENCES posts(id),
    UNIQUE (user_id, post_id)
);

-- Insert Users (10000 users)
INSERT INTO users (username, password, first_name, last_name, email)
SELECT 
    'user' || i,
    'password' || i,
    'Name' || i,
    'LName' || i,
    'user' || i || '@example.com'
FROM generate_series(1, 10000) i;

-- Insert Posts (25000 posts)
INSERT INTO posts (content, owner_id)
SELECT 
    'This is post number ' || i || ' by user ' || (1 + floor(random() * 5000)),
    1 + floor(random() * 5000)
FROM generate_series(1, 25000) i;

-- Insert User Follows (4 follows per user)
WITH RECURSIVE follows AS (
    SELECT 
        u1.id as following_id,
        u2.id as followed_id,
        ROW_NUMBER() OVER (PARTITION BY u1.id ORDER BY random()) as rn
    FROM users u1
    CROSS JOIN users u2
    WHERE u1.id != u2.id
)
INSERT INTO user_follow (following_id, followed_id)
SELECT following_id, followed_id
FROM follows
WHERE rn <= 4;

-- Insert Post Likes (5 likes per post)
WITH RECURSIVE likes AS (
    SELECT 
        p.id as post_id,
        u.id as user_id,
        ROW_NUMBER() OVER (PARTITION BY p.id ORDER BY random()) as rn
    FROM posts p
    CROSS JOIN users u
)
INSERT INTO post_likes (post_id, user_id)
SELECT post_id, user_id
FROM likes
WHERE rn <= 5;
