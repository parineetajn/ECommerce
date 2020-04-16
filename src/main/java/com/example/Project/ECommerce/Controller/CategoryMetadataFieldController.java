package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import com.example.Project.ECommerce.Service.CategoryMetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/admin/getAllCategoryMetadataFields")
    public List<Object[]> viewAllCategoryMetadataFields(){
        return categoryMetadataFieldService.viewAllCategoryMetadataFields();
    }

}
