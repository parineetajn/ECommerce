package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMetadataFieldRepository extends PagingAndSortingRepository<CategoryMetadataField,Long> {

    @Query(value = "select name from CategoryMetadataField",nativeQuery = true)
    List<Object[]> viewAllCategoryMetadataFields();

   @Query(value="select name from CategoryMetadataField where id=:id ",nativeQuery = true)
    String getMetadataName(@Param(value = "id")long id);
}
