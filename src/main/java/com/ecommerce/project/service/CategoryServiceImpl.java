package com.ecommerce.project.service;

import com.ecommerce.project.com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

//    private List<Category> categories = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
//        Category c1 = new Category((long) 1l, "Mobile");
//        Category c2 = new Category((long) 2l, "Drone");
//        Category c3 = new Category((long) 3l, "Laptop");
//        categories.add(c1);
//        categories.add(c2);
//        categories.add(c3);

        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

//    @Override
//    public String deleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//        Category category = categories.stream()
//                .filter( c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource not found with given category id"));
//
////        if(category == null ){
////            return "Category not found with id "+ categoryId ;
////        }
////        categories.remove(category);
//        categoryRepository.delete(category);
//        return "Category deleted successfully  category id :- " + categoryId;
//    }

//    @Override
//    public Category updateCategory(Long categoryId, Category category) {
//         List<Category> categories = categoryRepository.findAll();
//         Optional<Category> categoryOpt =  categories.stream()
//                 .filter(c -> c.getCategoryId().equals(categoryId))
//                 .findFirst();
//         if(categoryOpt.isPresent()){
//             Category existingCategory = categoryOpt.get();
//             existingCategory.setCategoryName(category.getCategoryName());
//             Category savedCategory = categoryRepository.save(existingCategory);
//             return savedCategory;
//         }else{
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Category not found");
//         }
//    }
//


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
