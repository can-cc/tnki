CREATE TABLE user_daily_statistics (
       user_id INT NOT NULL,
       date DATE NOT NULL,
       all_number INT NOT NULL,
       learned_number INT NOT NULL,
       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
       PRIMARY KEY(user_id, date),
       FOREIGN KEY (user_id) REFERENCES user(id)
);