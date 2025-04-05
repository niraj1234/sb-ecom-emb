package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.model.Category;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
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
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name="pageSize" ,   defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name="sortBy" ,     defaultValue = AppConstant.SORT_CATEGORY_BY , required = false) String sortBy ,
            @RequestParam(name="sortOrder" ,  defaultValue = AppConstant.SORT_CATEGORY_DIR , required = false) String sortOrder
    ){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> addCategory( @Valid @RequestBody Category category){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(category);
        return new ResponseEntity<>(savedCategoryDTO , HttpStatus.CREATED);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO deletedCategory =  categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory( @Valid @RequestBody Category category ,
                                                              @PathVariable Long categoryId ){
            CategoryDTO categoryDTOUpdated = categoryService.updateCategory(categoryId , category);
            return new ResponseEntity<>(categoryDTOUpdated , HttpStatus.OK);
    }

}
