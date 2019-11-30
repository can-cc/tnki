CREATE TABLE user_learn_item (
     item_id INT NOT NULL,
     user_id INT NOT NULL,
     ef DOUBLE NOT NULL,
     n INT NOT NULL DEFAULT 0,
     is_learning INT NOT NULL DEFAULT 1,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY(item_id,user_id),
     CONSTRAINT fk_learn_item FOREIGN KEY (item_id) REFERENCES learn_item(id),
     CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id)
);