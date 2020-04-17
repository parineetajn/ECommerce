package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Category;
import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import com.example.Project.ECommerce.Entity.CategoryMetadataFieldValue;
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
    public void addCategoryMetadataFieldValues(CategoryMetadataFieldValue categoryMetadataFieldValue, long category_id, long metadata_id) {

        if (categoryRepository.findById(category_id).isPresent()&& categoryRepository.checkIfLeafCategory(category_id)==0 ) {
            Optional<CategoryMetadataField> categoryMetadataFieldOptional=categoryMetadataFieldRepository.findById(metadata_id);
            if (categoryMetadataFieldOptional.isPresent()) {

                CategoryMetadataFieldValuesId categoryMetadataFieldValuesId = new CategoryMetadataFieldValuesId();
                categoryMetadataFieldValuesId.setCategory_id(categoryRepository.findById(category_id).get().getId());
                CategoryMetadataField categoryMetadataField=categoryMetadataFieldRepository.findById(metadata_id).get();
                categoryMetadataFieldValuesId.setCategory_metadata_field_id(categoryMetadataField.getId());

                categoryMetadataFieldValue.setId(categoryMetadataFieldValuesId);
                Optional<Category> categoryOptional=categoryRepository.findById(category_id);
                Category category =categoryOptional.get();
                categoryMetadataFieldValue.setCategory(category);

                String[] valuesArray = categoryMetadataFieldValue.getValue().split(",");
                Set<String> s = new HashSet<>(Arrays.asList(valuesArray));
                if (s.size()==valuesArray.length && s.size()>=1&& !valuesArray[0].equals(""))
                {
                    categoryMetadataFieldValue.setValue(categoryMetadataFieldValue.getValue());
                }
                else {
                    throw  new InputException( "Value Not provided!");
                }
                categoryMetadataFieldValue.setCategoryMetadataField(categoryMetadataField);
                categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValue);
            } else {
                throw new UserNotFoundException("Metadata for the id: "+metadata_id+ " not found!");
            }
        } else {
            throw new UserNotFoundException("No category for id: "+category_id+ " found!");
        }

    }

    public void updateCategoryMetadataFieldValues(CategoryMetadataFieldValue categoryMetadataFieldValue, long category_id, long metadata_id) {

        if (categoryRepository.findById(category_id).isPresent())
        {
            if (categoryMetadataFieldRepository.findById(metadata_id).isPresent())
            {
                if (categoryMetadataFieldValuesRepository.getFieldValues(category_id, metadata_id) != null) {
                    CategoryMetadataFieldValue categoryMetadataFieldValue1 = categoryMetadataFieldValuesRepository.getFieldValues(category_id, metadata_id);
                    categoryMetadataFieldValue1.setValue(categoryMetadataFieldValue.getValue());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValue1);
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
