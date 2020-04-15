package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Object[]> getCategory() {
        List<Object[]> objects = categoryRepository.getCategory();
        return objects;
    }

    public List<Object[]> getParentCategory() {
        List<Object[]> objects = categoryRepository.getParentCategory();
        return objects;
    }

    public List<Object[]> getSubCategory(int parentCategory_id) {
        List<Object[]> objects = categoryRepository.getSubCategory(parentCategory_id);
        return objects;
    }

}
