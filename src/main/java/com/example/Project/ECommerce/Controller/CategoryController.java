package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.DTO.FilteringDto;
import com.example.Project.ECommerce.DTO.ViewCategoryDto;
import com.example.Project.ECommerce.Entity.Category;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

     @GetMapping("/getParentCategories")
    public List<Object[]> getCategories() {
        return categoryService.getParentCategory();
    }

    @GetMapping("/getSubCategories/{Category_parent_id}")
    public List<Object[]> getSubcategories(@PathVariable(name = "Category_parent_id") long category_parent_id) {
       return  categoryService.getSubCategory(category_parent_id);
    }

    @GetMapping("/admin/category/{id}")
    public List<ViewCategoryDto> viewSingleCategory(@PathVariable("id") Long id){
        return categoryService.viewSingleCategory(id);
    }

    @GetMapping("/admin/allCategories")
    public ResponseEntity<List<ViewCategoryDto>> viewAllCategories(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                   @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        List<ViewCategoryDto> list = categoryService.viewAllCategoriesForAdmin(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ViewCategoryDto>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/seller/allCategories")
    public List<ViewCategoryDto> viewAllCategory(){
        return categoryService.viewAllCategoriesForSeller();
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

    @GetMapping("/customer/filtering/{category_id}")
    public FilteringDto filtering(@PathVariable(value = "category_id") Long category_id) {
        return categoryService.getFilteringDetails(category_id);
    }
    @GetMapping("/customer/viewAllCategoriesExceptLeaf")
    public ResponseEntity<List<ViewCategoryDto>> viewAllCategoriesExceptLeaf(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                               @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                               @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        List<ViewCategoryDto> list = categoryService.viewAllCategoriesExceptLeaf(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ViewCategoryDto>>(list, new HttpHeaders(), HttpStatus.OK);


    }

}
