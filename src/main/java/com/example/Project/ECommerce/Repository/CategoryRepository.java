package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

    @Query(value = "select * from Category where parent_id=:id",nativeQuery = true)
    List<Optional<Category>> findByParentId(@Param("id") Long id);

    List<Category> findAll(Pageable pageable);

    @Query(value = "select categoryName from Category" +
            " where parent_id not in(select id " +
            "from Category " +
            "where parent_id is null)",nativeQuery = true)
    List<Category> getAllLeafSubCategory();

    @Query(value = "select id,categoryName from Category " +
            "where parent_id is null\n",nativeQuery = true)
    List<Object[]> getParentCategory();

    @Query(value = "select id,categoryName from Category " +
            "where parent_id=:id",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("id") long id);

    @Query(value = "select categoryName,id from Category where parent_id in(select id from Category where categoryName=:name)",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("name") String name);

    @Query(value = "select id from Category " +
            "where categoryName=:name",nativeQuery = true)
    Long getIdOfParentCategory(@Param(value = "name") String name);

    @Query(value = "select categoryName,id from Category where parent_id not in (select id from Category where parent_id is null)", nativeQuery = true)
    List<Object[]> getSubcategory();

    @Query(value = "select exists(select * from Category where parent_id=:parent_id)",nativeQuery = true)
    int checkIfLeafCategory(@Param("parent_id") long parent_id);

}
