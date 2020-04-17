package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Category;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllLeafSubCategory(Integer pageNo, Integer pageSize, String sortBy) {
        return  categoryRepository.getAllLeafSubCategory();
    }

    public List<Object[]> getParentCategory() {
        List<Object[]> objects = categoryRepository.getParentCategory();
        return objects;
    }

    public List<Object[]> getSubCategory(long parent_category_id) {
        List<Object[]> objects = categoryRepository.getSubCategory(parent_category_id);
        return objects;
    }

    public String updateCategory(long category_id, Category category) {
        if (categoryRepository.findById(category_id).isPresent()) {
            Category category1 = categoryRepository.findById(category_id).get();
            category1.setCategoryName(category.getCategoryName());
            categoryRepository.save(category1);
            return "category Updated!";
        } else
            throw new UserNotFoundException("Category with id: " + category_id + " not found!");
    }
}
