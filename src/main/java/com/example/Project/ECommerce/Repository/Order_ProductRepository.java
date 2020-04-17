package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface Order_ProductRepository extends CrudRepository<OrderProduct,Long> {
}
