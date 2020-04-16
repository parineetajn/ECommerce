package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Category;
import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import com.example.Project.ECommerce.Entity.CategoryMetadataFieldValues;
import com.example.Project.ECommerce.Exceptions.InputException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldValuesRepository;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Utility.CategoryMetadataFieldValuesId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryMetadataFieldValuesService {

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    public List<Object[]> viewCategoryMetadataFieldValue(Long category_id, Long metadata_id) {
        if (categoryRepository.findById(category_id).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadata_id).isPresent()) {
                return categoryMetadataFieldValuesRepository.getParticularMetadataValue(category_id, metadata_id);
            } else {
                throw new UserNotFoundException("Metadata for the id: "+metadata_id+ " not found!");
            }
        } else {
            throw new UserNotFoundException("No Category with id: "+category_id +" found! ");
        }

    }
    public void addCategoryMetadataFieldValues(CategoryMetadataFieldValues categoryMetadataFieldValues, long category_id, long metadata_id) {

        if (categoryRepository.findById(category_id).isPresent()&& categoryRepository.checkIfLeafCategory(category_id)==0 ) {
            Optional<CategoryMetadataField> categoryMetadataFieldOptional=categoryMetadataFieldRepository.findById(metadata_id);
            if (categoryMetadataFieldOptional.isPresent()) {

                CategoryMetadataFieldValuesId categoryMetadataFieldValuesId = new CategoryMetadataFieldValuesId();
                categoryMetadataFieldValuesId.setCategory_id(categoryRepository.findById(category_id).get().getId());
                CategoryMetadataField categoryMetadataField=categoryMetadataFieldRepository.findById(metadata_id).get();
                categoryMetadataFieldValuesId.setCategory_metadata_field_id(categoryMetadataField.getId());

                categoryMetadataFieldValues.setId(categoryMetadataFieldValuesId);
                Optional<Category> categoryOptional=categoryRepository.findById(category_id);
                Category category =categoryOptional.get();
                categoryMetadataFieldValues.setCategory(category);

                String value = categoryMetadataFieldValues.getValue();
                if (!value.equals(""))
                {
                    categoryMetadataFieldValues.setValue(value);
                }
                else {
                    throw  new InputException( "Value Not provided!");
                }
                categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField);
                categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
            } else {
                throw new UserNotFoundException("Metadata for the id: "+metadata_id+ " not found!");
            }
        } else {
            throw new UserNotFoundException("No category for id: "+category_id+ " found!");
        }

    }

    public void updateCategoryMetadataFieldValues(CategoryMetadataFieldValues categoryMetadataFieldValues, long category_id, long metadata_id) {

        if (categoryRepository.findById(category_id).isPresent())
        {
            if (categoryMetadataFieldRepository.findById(metadata_id).isPresent())
            {
                if (categoryMetadataFieldValuesRepository.getFieldValues(category_id, metadata_id) != null) {
                    CategoryMetadataFieldValues categoryMetadataFieldValues1 = categoryMetadataFieldValuesRepository.getFieldValues(category_id, metadata_id);
                    categoryMetadataFieldValues1.setValue(categoryMetadataFieldValues.getValue());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
                } else {
                    throw new UserNotFoundException("Category id/Metadata id is not valid!!");
                }
            }
            else {
                throw new UserNotFoundException("Metadata id is not valid!!");
            }
        }

        else {
            throw new UserNotFoundException("Category id is not valid!!");
        }



    }
}
