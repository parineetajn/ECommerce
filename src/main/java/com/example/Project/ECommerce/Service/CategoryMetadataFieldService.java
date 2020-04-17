package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMetadataFieldService {
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    public List<CategoryMetadataField> viewAllCategoryMetadataFields(Pageable pageable){
        return categoryMetadataFieldRepository.viewAllCategoryMetadataFields(pageable);
    }
}
