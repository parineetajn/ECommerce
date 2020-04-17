package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Long> {

    @Query(value = "select categoryName from Category" +
            " where parent_id not in(select id " +
            "from Category " +
            "where parent_id is null)",nativeQuery = true)
    List<Category> getAllLeafSubCategory();

    @Query(value = "select categoryName from Category " +
            "where parent_id is null\n",nativeQuery = true)
    List<Object[]> getParentCategory();

    @Query(value = "select categoryName from Category " +
            "where parent_id=:id",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("id") long id);

    @Query(value = "select id from Category " +
            "where categoryName=:name",nativeQuery = true)
    Integer getIdOfParentCategory(@Param(value = "name") String name);

    @Query(value = "select exists(select * from Category where parent_id=:parent_id)",nativeQuery = true)
    int checkIfLeafCategory(@Param("parent_id") long parent_id);

}
