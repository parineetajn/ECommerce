package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.Category;
import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


     @GetMapping("/admin/getParentCategories")
    public List<Object[]> getCategories() {
        return categoryService.getParentCategory();
    }

    @GetMapping("/getSubCategories/{Category_parent_id}")
    public List<Object[]> getSubcategories(@PathVariable(name = "Category_parent_id") long category_parent_id) {
       return  categoryService.getSubCategory(category_parent_id);
    }

    @GetMapping("/admin/getAllLeafSubCategories")
    public List<Category> getAllLeafSubcategories(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        if (categoryRepository.findAll(paging).isEmpty()) {
            throw new UserNotFoundException("This list is empty because no metadata is present");
        } else {
            Page<Category> pageResult = categoryRepository.findAll(paging);
            if (pageResult.hasContent()) {
                return pageResult.getContent();
            } else {
                throw new UserNotFoundException("This page has no content");
            }
        }
     }

      @PostMapping("/admin/addCategory")
        public String addCategory(@RequestParam(value = "parent_id", required = false) Long parent_id,
                              @RequestParam(value = "CategoryName") String CategoryName) {
        Category category = new Category();
        Category category1 =null;
        category.setCategoryName(CategoryName);
        if(parent_id!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(parent_id);
            category1 = optionalCategory.get();
            category.setCategory(category1);
        } else {
            category.setCategory(null);
        }
        categoryRepository.save(category);
        return "Category added! with id: " + category.getId();
    }

    @PutMapping("/admin/updateCategory/{category_id}")
    public String updateCategory(@PathVariable(name = "category_id")long category_id,
                                 @Valid @RequestBody Category category){
         return categoryService.updateCategory(category_id,category);
    }

}
