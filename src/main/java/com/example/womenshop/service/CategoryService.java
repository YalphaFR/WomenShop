package com.example.womenshop.service;

import com.example.womenshop.model.Category;
import com.example.womenshop.repository.CategoryRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public void registerCategory(Category c) {
        repo.addCategory(c);
    }

    public List<Category> listAllCategories() {
        return repo.getAllCategories();
    }

    public Category findCategoryById(int id) {
        return repo.getCategoryById(id);
    }

    public void updateCategoryDetails(Category c) {
        repo.updateCategory(c);
    }

    public void removeCategory(int id) {
        repo.deleteCategory(id);
    }

    public List<Category> filterByCategory(Category category) {
        return repo.getAllCategories().stream()
                .filter(c -> c.getName().equals(category.getName()))
                .toList();
    }
}
