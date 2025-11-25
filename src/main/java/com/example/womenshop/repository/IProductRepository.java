package com.example.womenshop.repository;

import com.example.womenshop.model.Category;
import com.example.womenshop.model.base.Product;

import java.util.List;

public interface IProductRepository {
    void addProduct(Product p);
    void updateProduct(Product p);
    void deleteProduct(int id);
    List<Product> getAllProducts();
    List<Product> getProductsFilterByCategory(Category category);
    Product getProductById(int id);
    Product getProductByName(String name);
}

