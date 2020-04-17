package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<Orders,Long> {

    @Query(value = "select amountPaid,paymentMethod " +
            "from Orders join User on Orders.customer_user_id=User.id " +
            "where username=:username",nativeQuery = true)
    List<Object[]> viewOrders(@Param(value = "username")String username);
}
