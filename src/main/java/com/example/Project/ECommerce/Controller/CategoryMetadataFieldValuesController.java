package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.CategoryMetadataFieldValue;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldValuesRepository;
import com.example.Project.ECommerce.Service.CategoryMetadataFieldValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryMetadataFieldValuesController {

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryMetadataFieldValuesService categoryMetadataFieldValuesService;

    @PostMapping("/admin/addMetadataValues/{category_id}/{metadata_id}")
    public String addCategoryMetadataFieldValues(@RequestBody CategoryMetadataFieldValue categoryMetadataFieldValue,
                                  @PathVariable(value = "category_id") long category_id,
                                  @PathVariable(value = "metadata_id") long metadata_id) {
        categoryMetadataFieldValuesService.addCategoryMetadataFieldValues(categoryMetadataFieldValue, category_id, metadata_id);
        return "Metadata value added for metadata field with id: "+metadata_id;

    }

    @PutMapping("/admin/updateMetadataValues/{category_id}/{metadata_id}")
    public String updateCategoryMetadataFieldValues(@RequestBody CategoryMetadataFieldValue categoryMetadataFieldValue,
                                     @PathVariable(value = "category_id") long category_id,
                                     @PathVariable(value = "metadata_id") long metadata_id) {

        categoryMetadataFieldValuesService.updateCategoryMetadataFieldValues(categoryMetadataFieldValue, category_id, metadata_id);
        return "Metadata value updated!";

    }

    @GetMapping("/admin/viewMetadataValues/{category_id}/{metadata_id}")
    public List<Object[]> viewAMetadataValue(@PathVariable(value = "category_id") long category_id,
                                             @PathVariable(value = "metadata_id") long metadata_id) {
        return categoryMetadataFieldValuesService.viewCategoryMetadataFieldValue(category_id, metadata_id);
    }
    @GetMapping("/admin/viewAllMetadataValues")
    public List<Object[]> viewAllMetadataValues() {
        return categoryMetadataFieldValuesRepository.getAllCategoryMetadataFieldValues();
    }
}
