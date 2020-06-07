package com.example.Project.ECommerce.DTO;

import com.example.Project.ECommerce.Entity.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ViewCategoryDto
{
    String categoryId;
    String name;
    List<String> fieldName;
    List<String> values;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;

    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}