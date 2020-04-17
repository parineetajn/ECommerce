package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Utility.CategoryMetadataFieldValuesId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CategoryMetadataFieldValue implements Serializable {
    @EmbeddedId
    private CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @MapsId("category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_metadata_field_id")
    @MapsId("category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    private String value;

    public CategoryMetadataFieldValue() {
    }

    public CategoryMetadataFieldValue(Category category, CategoryMetadataField categoryMetadataField, String value) {
        this.category = category;
        this.categoryMetadataField = categoryMetadataField;
        this.value = value;
    }

    public CategoryMetadataFieldValuesId getId() {
        return id;
    }

    public void setId(CategoryMetadataFieldValuesId id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CategoryMetadataFieldValues{" +
                "id=" + id +
                ", category=" + category +
                ", categoryMetadataField=" + categoryMetadataField +
                ", value='" + value + '\'' +
                '}';
    }
}
