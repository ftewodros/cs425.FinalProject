package edu.miu.cs.cs425.carrentalswe.service;

import edu.miu.cs.cs425.carrentalswe.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category createCategory(Category category);
    void deleteCategoryById(Long id);
}
