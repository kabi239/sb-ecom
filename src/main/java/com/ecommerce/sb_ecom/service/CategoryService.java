package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNo, Integer pageSize,String sortBy,String sortOrder );

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);
    // whatever category is deleted we are returning it as a result
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
