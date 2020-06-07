package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.CategoryMetadataFieldValue;
import com.example.Project.ECommerce.Utility.CategoryMetadataFieldValuesId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValue, CategoryMetadataFieldValuesId> {

    @Query(value = "select * from CategoryMetadataFieldValue where " +
            "category_id=:category_id and category_metadata_field_id=:category_metadata_field_id", nativeQuery = true)
    CategoryMetadataFieldValue getFieldValues(@Param(value = "category_id") long category_id, @Param(value = "category_metadata_field_id") long category_metadata_field_id);

    @Query(value = "select category_id,category_metadata_field_id, value from CategoryMetadataFieldValue" +
            " where category_id=:category_id and category_metadata_field_id=:category_metadata_field_id", nativeQuery = true)
    List<Object[]> getParticularMetadataValue(@Param(value = "category_id") long category_id, @Param(value = "category_metadata_field_id") long category_metadata_field_id);

    @Query(value = "select * from CategoryMetadataFieldValue", nativeQuery = true)
    List<Object[]> getAllCategoryMetadataFieldValues();

    @Query(value = "select category_metadata_field_id from CategoryMetadataFieldValue where category_id=:id", nativeQuery = true)
    List<Long> getMetadataId(@Param(value = "id") long id);

    @Query(value = "select value from CategoryMetadataFieldValue where " +
            "category_id=:category_id and category_metadata_field_id=:category_metadata_field_id", nativeQuery = true)
    String getFieldValuesForCompositeKey(@Param(value = "category_id") long category_id,
                                         @Param(value = "category_metadata_field_id") long category_metadata_field_id);


    @Query(value = "select name, value from CategoryMetadataFieldValue as cm " +
            "inner join CategoryMetadataField as cf " +
            "on cm.category_metadata_field_id = cf.id and cm.category_id=:id", nativeQuery = true)
    List<Object[]> findCategoryMetadataFieldValuesById(@Param("id") Long id);

}