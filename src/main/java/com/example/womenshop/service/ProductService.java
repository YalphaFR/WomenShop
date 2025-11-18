package com.example.womenshop.service;

import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
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

    public void updateProductDetails(Product p) {
        repo.updateProduct(p);
    }

    public void removeProduct(int id) {
        repo.deleteProduct(id);
    }

    public List<Product> filterByCategory(Category category) {
        return repo.getAllProducts().stream()
                .filter(c -> c.getCategory().getId()== category.getId())
                .toList();
    }
}

