-- write sql to create products table
CREATE TABLE if not exists products(
     product_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     product_name VARCHAR(25) NOT NULL,
     price DOUBLE NOT NULL
);