package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long> {
}
