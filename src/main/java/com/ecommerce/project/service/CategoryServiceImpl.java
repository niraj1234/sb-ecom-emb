package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.NoCategoryFoundException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> savedCategories =  categoryRepository.findAll();
        if(savedCategories.isEmpty()){
            throw new NoCategoryFoundException("No Category available at this time");
        }
        List<CategoryDTO> categoryDtos = savedCategories.stream()
                .map( category -> modelMapper.map(category , CategoryDTO.class))
                .collect(Collectors.toList());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtos);
        return categoryResponse;
    }



    @Override
    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with name "+ category.getCategoryName() + " already exist. Try something different");
        }
        Category savedCategoryNew = categoryRepository.save(category);
        return modelMapper.map(savedCategoryNew , CategoryDTO.class);
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
