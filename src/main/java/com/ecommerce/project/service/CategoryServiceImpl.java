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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

//    @Override   Old method without pagination may be direct use
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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
//        List<Category> savedCategories =  categoryRepository.findAll();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> savedCategories = categoryPage.getContent();

            if(savedCategories.isEmpty()){
                throw new NoCategoryFoundException("No Category available at this time");
            }
            List<CategoryDTO> categoryDtos = savedCategories.stream()
                    .map( category -> modelMapper.map(category , CategoryDTO.class))
                    .collect(Collectors.toList());
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setContent(categoryDtos);
            categoryResponse.setPageNumber(categoryPage.getNumber());
            categoryResponse.setPageSize(categoryPage.getSize());
            categoryResponse.setTotalElements(categoryPage.getTotalElements());
            categoryResponse.setTotalPages(categoryPage.getTotalPages());
            categoryResponse.setLastPage(categoryPage.isLast());

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
    public CategoryDTO updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOpt =  categoryRepository.findById(categoryId);
        Category savedCategory = categoryOpt.orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , categoryId) );
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory , CategoryDTO.class);
    }


    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
//        Category categoryInDb = categoryOpt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Category not found"));
        Category categoryInDb = categoryOpt.orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        categoryRepository.delete(categoryInDb);
        CategoryDTO categoryDTO = modelMapper.map(categoryInDb , CategoryDTO.class);
        System.out.println("Category deleted successfully  category id :- " + categoryId);
        return categoryDTO;
    }

}
