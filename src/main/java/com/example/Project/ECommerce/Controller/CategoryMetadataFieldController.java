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

    @GetMapping("/admin/getAllCategoryMetadataFields")
    public List<CategoryMetadataField> viewAllCategoryMetadataFields(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                        @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<CategoryMetadataField> categoryMetadataFieldList= categoryMetadataFieldService.viewAllCategoryMetadataFields(paging);
        return categoryMetadataFieldList;
    }

}
