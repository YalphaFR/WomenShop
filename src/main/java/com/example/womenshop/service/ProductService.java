package com.example.womenshop.service;

import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.repository.IProductRepository;

import java.util.List;

public class ProductService {
    private final IProductRepository repo;

    public ProductService(IProductRepository repo) {
        this.repo = repo;
    }

    public void registerProduct(Product p) {
        repo.addProduct(p);
    }

    public List<Product> listAllProducts() {
        return repo.getAllProducts();
    }

    public Product findProductById(int id) {
        return repo.getProductById(id);
    }

    public Product findProductByName(String name) {
        return repo.getProductByName(name);
    }

    public void updateProductDetails(Product p) {
        repo.updateProduct(p);
    }

    public void removeProduct(int id) {
        repo.deleteProduct(id);
    }

    public List<Product> filterByCategory(Category category) {
        return repo.getProductsFilterByCategory(category);
    }
}

