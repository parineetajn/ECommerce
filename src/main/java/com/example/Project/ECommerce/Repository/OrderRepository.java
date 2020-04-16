package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders,Long> {
}
