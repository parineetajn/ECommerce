package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Long> {
}
