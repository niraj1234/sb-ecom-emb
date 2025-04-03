package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.NoCategoryFoundException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> savedCategories =  categoryRepository.findAll();
        if(savedCategories.isEmpty()){
            throw new NoCategoryFoundException("No Category available at this time");
        }
        return savedCategories;
    }


    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with name "+ category.getCategoryName() + " already exist. Try something different");
        }
        categoryRepository.save(category);
    }


    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOpt =  categoryRepository.findById(categoryId);
        Category savedCategory = categoryOpt.orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , categoryId) );
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }


    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
//        Category categoryInDb = categoryOpt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Category not found"));
        Category categoryInDb = categoryOpt.orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        categoryRepository.delete(categoryInDb);
        return "Category deleted successfully  category id :- " + categoryId;
    }

}
