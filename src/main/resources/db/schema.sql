DROP DATABASE IF EXISTS womenshop;
CREATE DATABASE IF NOT EXISTS womenshop;
USE womenshop;

CREATE TABLE categories (
                            categories_id INT AUTO_INCREMENT PRIMARY KEY,
                            categories_name VARCHAR(50) UNIQUE NOT NULL,
                            categories_discount_rate DOUBLE NOT NULL
);

INSERT INTO categories (categories_name, categories_discount_rate) VALUES
                                                 ('Clothes', 0.30),
                                                 ('Shoes', 0.20),
                                                 ('Accessory', 0.50);


CREATE TABLE products (
                          products_id INT AUTO_INCREMENT PRIMARY KEY,
                          categories_id INT NOT NULL,
                          products_name VARCHAR(100) NOT NULL,
                          products_purchase_price DOUBLE NOT NULL,   -- prix d'achat (constant)
                          products_sale_price DOUBLE NOT NULL,       -- prix de vente normal
                          products_discounted TINYINT(1) DEFAULT 0,  -- discount actif ?
                          products_stock INT DEFAULT 0,
                          FOREIGN KEY (categories_id) REFERENCES categories(categories_id)
);



CREATE TABLE transactions (
                              transactions_id INT AUTO_INCREMENT PRIMARY KEY,
                              products_id INT NOT NULL,
                              transactions_type ENUM('PURCHASE', 'SALE') NOT NULL,
                              transactions_quantity INT NOT NULL,
                              transactions_amount DOUBLE NOT NULL,       -- montant total payé ou reçu
                              transactions_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (products_id) REFERENCES products(products_id)
);
