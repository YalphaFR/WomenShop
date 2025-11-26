DROP DATABASE IF EXISTS womenshop;
CREATE DATABASE womenshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;;
USE womenshop;

CREATE TABLE shop (
                      shop_id INT PRIMARY KEY,
                      shop_initial_capital DECIMAL(14,2) NOT NULL CHECK (shop_initial_capital >= 0),
                      shop_current_capital DECIMAL(14,2) NOT NULL CHECK (shop_current_capital >= 0),
                      shop_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO shop (shop_id, shop_initial_capital, shop_current_capital) VALUES (1, 30000, 30000);

CREATE TABLE category (
                            category_id INT AUTO_INCREMENT PRIMARY KEY,
                            category_name VARCHAR(50) UNIQUE NOT NULL,
                            category_discount_rate DOUBLE NOT NULL,
                            category_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO category (category_name, category_discount_rate) VALUES
                                                 ('Clothing', 0.30),
                                                 ('Shoes', 0.20),
                                                 ('Accessory', 0.50);


CREATE TABLE product (
                          product_id INT AUTO_INCREMENT PRIMARY KEY,
                          category_id INT NOT NULL,
                          product_name VARCHAR(100) NOT NULL,
                          product_purchase_price DOUBLE NOT NULL CHECK (product_purchase_price >= 0),   -- prix d'achat (constant)
                          product_sale_price DOUBLE NOT NULL CHECK (product_sale_price >= 0),     -- prix de vente normal
                          product_discounted TINYINT(1) DEFAULT 0,  -- discount actif ?
                          product_stock INT DEFAULT 0,
                          product_size INT NULL,
                          product_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_id) REFERENCES category(category_id)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
);



CREATE TABLE transaction (
                              transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                              product_id INT NOT NULL,
                              transaction_type ENUM('PURCHASE', 'SALE') NOT NULL,
                              transaction_quantity INT NOT NULL,
                              transaction_amount DOUBLE NOT NULL,       -- montant total payé ou reçu
                              transaction_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (product_id) REFERENCES product(product_id)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE
);