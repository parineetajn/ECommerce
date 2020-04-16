package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.ProductVariation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {

    @Transactional
    @Modifying
    @Query(value = "delete ProductVariation from ProductVariation  " +
            "inner join Product on Product.id=ProductVariation.product_id " +
            "where product_id=:id",nativeQuery = true)
    void deleteProductVariation(@Param(value = "id") long id);

    @Query(value = "select primaryImageName,price,quantity_available,metadata " +
            "from ProductVariation pv " +
            "inner join Product p on pv.product_id=p.id " +
            "where p.id=:id",nativeQuery = true)
    List<Object[]> getProductVariation(@Param(value ="id") long id);

}
