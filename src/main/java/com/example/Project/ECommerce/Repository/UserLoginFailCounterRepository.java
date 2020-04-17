package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.UserLoginFailCounter;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserLoginFailCounterRepository extends CrudRepository<UserLoginFailCounter,Long> {
    Optional<UserLoginFailCounter> findByUsername(String username);
}
