package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.Category;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/admin/getSubCategories/{Category_parent_id}")
    public List<Object[]> getSubcategories(@PathVariable(name = "Category_parent_id") long category_parent_id) {
       return  categoryService.getSubCategory(category_parent_id);
    }

    @GetMapping("/admin/getAllLeafSubCategories")
    public List<Object[]> getAllLeafSubcategories() {
        return  categoryService.getAllLeafSubCategory();
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
