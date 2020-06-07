package com.example.Project.ECommerce.Utility;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoryMetadataFieldValuesId implements Serializable {

    private long category_id;
    private long category_metadata_field_id;

    public CategoryMetadataFieldValuesId() {
    }

    public CategoryMetadataFieldValuesId(long category_id, long category_metadata_field_id) {
        this.category_id = category_id;
        this.category_metadata_field_id = category_metadata_field_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getCategory_metadata_field_id() {
        return category_metadata_field_id;
    }

    public void setCategory_metadata_field_id(long category_metadata_field_id) {
        this.category_metadata_field_id = category_metadata_field_id;
    }

}
