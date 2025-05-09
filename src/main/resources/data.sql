CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Example insertions
INSERT INTO users (user_id, username, password, fullName, email, role)
VALUES ('1', 'admin', 'hashed_password', 'Admin User', 'admin@example.com', 'ADMIN'),
       ('2', 'user', 'hashed_password', 'Regular User', 'user@example.com', 'USER');
