package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);
    CategoryDTO createCategory(Category category);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(Long categoryId, Category category);
}
