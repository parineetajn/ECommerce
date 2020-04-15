package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Integer> {

    @Query(value = "select name from Category" +
            " where parent_id not in(select id " +
            "from Category " +
            "where parent_id is null)",nativeQuery = true)
    List<Object[]> getCategory();

    @Query(value = "select name from Category " +
            "where parent_id is null\n",nativeQuery = true)
    List<Object[]> getParentCategory();

    @Query(value = "select name from Category " +
            "where parent_id =:id)",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("id") int id);

    @Query(value = "select id from Category " +
            "where name=:name",nativeQuery = true)
    Integer getIdOfParentCategory(@Param(value = "name") String name);

}
