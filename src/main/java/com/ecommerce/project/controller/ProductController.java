package com.ecommerce.project.controller;


import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product ,
                                                 @PathVariable Long categoryId){
        System.out.println("Product in Request"  + product);
        ProductDTO productDTO = productService.addProduct(categoryId,product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize"  , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" , defaultValue = AppConstant.SORT_PRODUCTS_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder" , defaultValue = AppConstant.SORT_PRODUCT_DIR , required = false) String sortOrder
            ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize ,sortBy , sortOrder);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }


    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId ,
         @RequestParam(name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
         @RequestParam(name = "pageSize"  , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
         @RequestParam(name = "sortBy" , defaultValue = AppConstant.SORT_PRODUCTS_BY , required = false) String sortBy,
         @RequestParam(name = "sortOrder" , defaultValue = AppConstant.SORT_PRODUCT_DIR , required = false) String sortOrder
        ){
        ProductResponse productResponse = productService.getProductsByCategory(categoryId , pageNumber, pageSize ,sortBy , sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword ,
        @RequestParam(name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
        @RequestParam(name = "pageSize"  , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
        @RequestParam(name = "sortBy" , defaultValue = AppConstant.SORT_PRODUCTS_BY , required = false) String sortBy,
        @RequestParam(name = "sortOrder" , defaultValue = AppConstant.SORT_PRODUCT_DIR , required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getProductsByKeyword( keyword , pageNumber, pageSize ,sortBy , sortOrder);
        return new ResponseEntity<>(productResponse , HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product ,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId,product);
        return new ResponseEntity<>(updatedProductDTO , HttpStatus.OK);
    }


    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProductDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProductDTO , HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId ,
                                                         @RequestParam("Image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId , image);
        return new ResponseEntity<>(updatedProduct , HttpStatus.OK);
    }
}
