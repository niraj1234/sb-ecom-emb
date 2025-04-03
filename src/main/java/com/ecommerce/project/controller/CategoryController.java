package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;

import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(allCategories);
    }

    @PostMapping("/category")
    public ResponseEntity<String> addCategory( @Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category Added Successfully");
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try {
            String status =  categoryService.deleteCategory(categoryId);
//            return new ResponseEntity<>(status , HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getMessage() , e.getStatusCode());
        }
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<String> updateCategory( @PathVariable Long categoryId,
                                                  @RequestBody Category category){
        try {
            Category categoryUpdated = categoryService.updateCategory(categoryId , category);
            return ResponseEntity.status(HttpStatus.OK).body("Category Updated successfully :- "+categoryId);
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }



}
