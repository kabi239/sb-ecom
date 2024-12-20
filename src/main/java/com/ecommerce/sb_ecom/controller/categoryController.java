package com.ecommerce.sb_ecom.controller;


import com.ecommerce.sb_ecom.config.AppConstants;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;
import com.ecommerce.sb_ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class categoryController {

    @Autowired
    private CategoryService categoryService;


    //@GetMapping("/public/categories")
    @RequestMapping(value = "/public/categories",method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                             @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                             @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false)String sortBy,
                                                             @RequestParam(name ="sortOrder",defaultValue = AppConstants.SORT_DIR,required = false)String sortOrder ) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid  @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO deletedCategory=categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(deletedCategory,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public  ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
            CategoryDTO statusResponse = categoryService.deleteCategory(categoryId);
            // return new ResponseEntity<>(status, HttpStatus.OK);
            // return ResponseEntity.ok(status);
            return ResponseEntity.status(HttpStatus.OK).body(statusResponse);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK);

    }
}
