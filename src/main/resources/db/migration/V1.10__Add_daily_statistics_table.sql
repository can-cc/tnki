DROP TABLE user_daily_statistics;

CREATE TABLE daily_learn_statistics (
     user_id INT NOT NULL,
     date DATETIME NOT NULL,
     total_should_learn INTEGER,
     learned INTEGER,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
     PRIMARY KEY(user_id, date),
     FOREIGN KEY (user_id) REFERENCES user(id)
);