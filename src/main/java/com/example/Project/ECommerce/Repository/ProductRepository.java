package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query(value = "select id from Product " +
            "where name=:name",nativeQuery = true)
    Long findProductId(@Param( value="name")String name);

    @Query(value = "select * from Product " +
            "where name=:name",nativeQuery = true)
    Product findProductName(@Param( value="name")String name);

    @Query(value = "select  p.name,p.brand,p.is_Returnable,c.categoryName,pv.primaryImageName " +
            "from Product p inner join Category c on p.category_id=c.id " +
            "inner join ProductVariation pv on p.id=pv.product_id" +
            " where p.is_Active=true and p.id=:id",nativeQuery = true)
    List<Object[]>findProduct(@Param(value = "id")long id);


    @Query(value = "select  p.name,p.brand,p.is_Returnable,c.categoryName,pv.primaryImageName " +
            "from Product p inner join Category c on p.category_id=c.id" +
            " inner join ProductVariation pv on p.id=pv.product_id " +
            "where p.is_Active=true and c.parent_id=:id",nativeQuery = true)
    List<Object[]> findProductList(@Param(value = "id")long id);

    @Query(value = "select  p.name,p.brand,p.is_Returnable,c.categoryName,pv.primaryImageName " +
            "from Product p inner join Category c on p.category_id=c.id" +
            " inner join ProductVariation pv on p.id=pv.product_id" +
            " inner join User u on p.seller_user_id=u.id " +
            "where p.is_Active=true and u.username=:username",nativeQuery = true)
    List<Object[]> findSellerProductList(@Param(value = "username")String username);

    @Query(value = "select  p.name,p.brand,c.categoryName,pv.primaryImageName " +
            "from Product p inner join Category c on p.category_id=c.id " +
            "inner join ProductVariation pv on p.id=pv.product_id " +
            "where p.is_Active=true ",nativeQuery = true)
    List<Object[]> findAdminProductList();

    @Query(value = "select brand, name,description,is_Active,is_Returnable,is_cancellable primary_image_name," +
            "price,quantity_available ,rating,review,username ," +
            "companyName, companyContact from Product p " +
            "inner join ProductVariation pv on pv.product_id= p.id " +
            "inner join ProductReview pr on p.id=pr.product_id " +
            "inner join Seller s on s.id=p.seller_user_id " +
            "inner join User u on u.id =s.id "+
            "where p.category_id=:id",nativeQuery = true)
    List<Object[]> findProductDetails(@Param(value = "id")long id);

    @Query(value = "select category_id from Product where id=:id",nativeQuery = true)
    long getCategoryId(@Param(value = "id")long id);

    @Transactional
    @Modifying
    @Query(value = "delete from Product where id=:id",nativeQuery = true)
    void deleteProduct(@Param(value = "id") long id);

    @Query(value = "select brand,description,name,categoryName,metadata,primaryImageName " +
            "from Product p join Category c on p.category_id=c.id " +
            "join ProductVariation pv on pv.product_id=p.id " +
            "where category_id=:category_id",nativeQuery = true)
    List<Object[]> getSimilarProducts(@Param(value = "category_id")long category_id);

}
