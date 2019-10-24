-- write sql to create comments table
CREATE TABLE if not exists comments (
     comment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     comment_author VARCHAR(25) NOT NULL,
     comment_text VARCHAR(50) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     review_id INT NOT NULL,
     CONSTRAINT fk_reviewid
     FOREIGN KEY (review_id) REFERENCES reviews(review_id)
     ON UPDATE CASCADE
     ON DELETE CASCADE
);