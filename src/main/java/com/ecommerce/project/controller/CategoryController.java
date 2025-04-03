package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;

import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(allCategories);
    }

    @PostMapping("/category")
    public ResponseEntity<String> addCategory( @Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category Added Successfully");
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
            String status =  categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<String> updateCategory( @Valid @RequestBody Category category ,
                                                  @PathVariable Long categoryId ){
            Category categoryUpdated = categoryService.updateCategory(categoryId , category);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Category Updated successfully :- "+categoryId);
    }

}
