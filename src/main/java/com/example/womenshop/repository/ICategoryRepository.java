package com.example.womenshop.repository;

import com.example.womenshop.model.Category;

import java.util.List;

public interface ICategoryRepository {
    void addCategory(Category c);
    void updateCategory(Category c);
    void deleteCategory(int id);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
}
