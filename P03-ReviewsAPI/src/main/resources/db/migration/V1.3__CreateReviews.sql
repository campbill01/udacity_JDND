-- write sql to create reviews table
CREATE TABLE if not exists reviews (
     review_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     review_author VARCHAR(25) NOT NULL,
     review_text VARCHAR(50) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     product_id INT NOT NULL,
     CONSTRAINT fk_productid
     FOREIGN KEY(product_id) REFERENCES products(product_id)
     ON UPDATE CASCADE
     ON DELETE CASCADE
);