package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Seller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerRepository extends PagingAndSortingRepository<Seller,Integer> {
    Seller findByUsername(String username);

    @Query(value = "select s.id,firstName,lastName,companyContact,companyName,GST,city,state,country,addressLine,zipCode from Seller s " +
            "inner join User u on s.id=u.id " +
            "inner join Address a on s.id=a.User_id " +
            "where u.username=:username",nativeQuery = true)
    List<Object[]> viewProfile(@Param(value = "username")String username);


}
