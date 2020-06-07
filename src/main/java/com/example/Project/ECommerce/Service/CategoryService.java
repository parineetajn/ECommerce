package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.DTO.FilteringDto;
import com.example.Project.ECommerce.DTO.ViewCategoryDto;
import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldValuesRepository;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

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

    public List<ViewCategoryDto> viewAllCategoriesExceptLeaf(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewCategoryDto> viewCategoriesDTOS= new ArrayList<>();

        for (Category category1: categoryRepository.findAll(paging))
        {
            if(categoryRepository.checkIfLeafCategory(category1.getId())==0)
            {
            }else{
                ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                viewCategoriesDTO.setName(category1.getCategoryName());
                viewCategoriesDTO.setCategoryId(category1.getId().toString());
                viewCategoriesDTOS.add(viewCategoriesDTO);
            }
        }
        return viewCategoriesDTOS;


    }


    public List<ViewCategoryDto> viewSingleCategory(Long categoryId) {
        List<ViewCategoryDto> viewCategoriesDTOList = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            ViewCategoryDto categoriesDTO = new ViewCategoryDto();
            categoriesDTO.setCategoryId(category.getId().toString());
            categoriesDTO.setName(category.getCategoryName());
            viewCategoriesDTOList.add(categoriesDTO);

            if (categoryRepository.checkIfLeafCategory(category.getId()) == 0) {
                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);
                for (Long l : longList) {
                    CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                    fields.add(categoryMetadataField.getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId, l));

                }

                categoriesDTO.setValues(values);
                categoriesDTO.setFieldName(fields);

            } else {
                ViewCategoryDto viewCategoriesDTO2= new ViewCategoryDto();
                viewCategoriesDTO2.setName(category.getCategoryName());
                viewCategoriesDTO2.setCategoryId(category.getId().toString());

                List<Object[]> list = categoryRepository.getSubCategory(category.getCategoryName());
                for (Object[] objects : list) {

                    ViewCategoryDto viewCategoriesDTO= new ViewCategoryDto();
                    viewCategoriesDTO.setName(objects[0].toString());
                    viewCategoriesDTO.setCategoryId(objects[1].toString());
                    viewCategoriesDTOList.add(viewCategoriesDTO);
                }
            }
        }
        else
        {
            throw new UserNotFoundException("Category not found!!");
        }
        return viewCategoriesDTOList;
    }


    public List<ViewCategoryDto> viewAllCategoriesForAdmin(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewCategoryDto> viewCategoriesDTOS= new ArrayList<>();
        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();

        for (Category category1: categoryRepository.findAll(paging))
        {
            ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
            viewCategoriesDTO.setName(category1.getCategoryName());
            viewCategoriesDTO.setCategoryId(category1.getId().toString());
            if(categoryRepository.checkIfLeafCategory(category1.getId())==0)
            {
                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category1.getId());
                for (Long l : longList) {
                    CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                    fields.add(categoryMetadataField.getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category1.getId(), l));
                    viewCategoriesDTO.setValues(values);
                    viewCategoriesDTO.setFieldName(fields);

                }

            }

            viewCategoriesDTOS.add(viewCategoriesDTO);
        }
        return viewCategoriesDTOS;


    }

    public List<ViewCategoryDto> viewAllCategoriesForSeller()
    {
        List<Object[]> list =  categoryRepository.getSubcategory();
        List<ViewCategoryDto> list1 = new ArrayList<>();
        for (Object[] objects:list)
        {
            ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
            viewCategoriesDTO.setName(objects[0].toString());
            viewCategoriesDTO.setCategoryId(objects[1].toString());
            Long categoryId = categoryRepository.getIdOfParentCategory(objects[0].toString());
            Optional<Category> category = categoryRepository.findById(categoryId);
            Set<CategoryMetadataFieldValue> set = category.get().getCategoryMetadataFieldValueSet(); //L,M,S
            List<String> fields = new ArrayList<>();
            for (CategoryMetadataFieldValue categoryMetadataFieldValues : set)
            {
                fields.add(categoryMetadataFieldValues.getValue());
                viewCategoriesDTO.setValues(fields);
            }
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            List<String > fields1 = new ArrayList<>();
            for (Long l : longList)
            {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                fields1.add(categoryMetadataField.get().getName());
                viewCategoriesDTO.setFieldName(fields1);
            }
            list1.add(viewCategoriesDTO);

        }
        return list1;
    }

    public String updateCategory(long category_id, Category category) {
        if (categoryRepository.findById(category_id).isPresent()) {
            Category category1 = categoryRepository.findById(category_id).get();
            category1.setCategoryName(category.getCategoryName());
            categoryRepository.save(category1);
            return "category Name Updated!";
        } else
            throw new UserNotFoundException("Category with id: " + category_id + " not found!");
    }

    public FilteringDto getFilteringDetails(Long categoryId) {
        Optional<Category> categoryOptional= categoryRepository.findById(categoryId);

        if(categoryOptional.isPresent()&& categoryRepository.checkIfLeafCategory(categoryId)==0)
        {
            Category category= categoryOptional.get();
            List<Long> listOfMetadataIdsForParticularCategory = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);
            FilteringDto filterDTO= new FilteringDto();
            filterDTO.setCategoryName(category.getCategoryName());
            List<String> stringListForField= new ArrayList<>();
            List<String> stringListForValues= new ArrayList<>();
            List<String> brands= new ArrayList<>();
            Double minPrice=0D;
            Double maxPrice=0D;
            Set<Double> doubleSet= new TreeSet<>();

            for (Long idOfMetadata : listOfMetadataIdsForParticularCategory) {

                CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findById(idOfMetadata).get();
                stringListForField.add(categoryMetadataField.getName());
                stringListForValues.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId,idOfMetadata));
                Set<Product> productSet= category.getProducts();
                for (Product product: productSet)
                {
                    brands.add(product.getBrand());
                    Set<ProductVariation> productVariationSet= product.getProductVariations();
                    for (ProductVariation productVariation: productVariationSet)
                    {
                        doubleSet.add(productVariation.getPrice());
                    }

                }
                Double[] doubles= doubleSet.toArray(new Double[doubleSet.size()]);
                filterDTO.setBrands(brands);
                filterDTO.setFields(stringListForField);
                filterDTO.setValues(stringListForValues);
                filterDTO.setMinPrice(doubles[0]);
                filterDTO.setMaxPrice(doubles[doubleSet.size()-1]);

            }
            return filterDTO;
        }
        else {
            throw new UserNotFoundException("Not Found!!");
        }


    }
}
