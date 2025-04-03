package com.ecommerce.project.service;

import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }


    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOpt =  categoryRepository.findById(categoryId);
        Category savedCategory = categoryOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Category not found") );
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }


    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        Category categoryInDb = categoryOpt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Category not found"));
        categoryRepository.delete(categoryInDb);
        return "Category deleted successfully  category id :- " + categoryId;
    }

}
