package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview ,Long> {
}
