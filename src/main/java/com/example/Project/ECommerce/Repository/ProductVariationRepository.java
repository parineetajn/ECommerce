package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Product;
import com.example.Project.ECommerce.Entity.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductVariationRepository extends PagingAndSortingRepository<ProductVariation,Long> {

    @Query(value = "select product_id from ProductVariation where id =:id",nativeQuery = true)
    long getProductId(@Param(value = "id") long id);


    @Query(value = "select * from ProductVariation where id=:id",nativeQuery = true)
    List<Object[]> getProductVariation(@Param("id") long id);

    @Query(value = "select pv.id,primaryImageName,price,quantityAvailable,metadata " +
            "from ProductVariation pv " +
            "inner join Product p on pv.product_id=p.id " +
            "where p.id=:id",nativeQuery = true)
    List<Object[]> getAllProductVariation(@Param(value ="id") long id);

    @Transactional
    @Modifying
    @Query(value = "delete ProductVariation from ProductVariation  " +
            "inner join Product on Product.id=ProductVariation.product_id " +
            "where product_id=:id",nativeQuery = true)
    void deleteProductVariation(@Param(value = "id") long id);

    @Query(value = "select price from ProductVariation where product_id=:id",nativeQuery = true)
    Double getPrice(@Param(value = "id")long id);



}
