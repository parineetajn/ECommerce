package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token,Long> {
}
