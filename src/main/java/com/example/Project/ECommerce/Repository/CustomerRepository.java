package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Address;
import com.example.Project.ECommerce.Entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Integer> {
    Customer findByUsername(String username);

    @Query(value = "select c.id,firstName,lastName,contact,city,state,country,addressLine,zipCode from Customer c " +
            "inner join User u on c.id=u.id " +
            "inner join Address a on c.id=a.User_id " +
            "where u.username=:username",nativeQuery = true)
    List<Object[]> viewProfile(@Param(value = "username") String username);


}
