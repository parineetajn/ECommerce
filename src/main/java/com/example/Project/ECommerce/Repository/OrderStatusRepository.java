package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatus,Long> {
}
