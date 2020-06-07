package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import com.example.Project.ECommerce.Service.CategoryMetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryMetadataFieldController {

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldService categoryMetadataFieldService;

    @PostMapping("/admin/addCategoryMetadataField")
    public String addCategoryMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField) {
        categoryMetadataFieldRepository.save(categoryMetadataField);
        return "CategoryMetadataField is successfully created";
    }

    @GetMapping("/admin/AllCategoryMetadataFields")
    public List<Object[]> viewAllCategoryMetadataFields(){
        List<Object[]> categoryMetadataFieldList= categoryMetadataFieldService.viewAllCategoryMetadataFields();
        return categoryMetadataFieldList;
    }

}
