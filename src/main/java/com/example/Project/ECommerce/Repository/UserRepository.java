package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select username,email,authority " +
            "from User inner join Role on User.id = Role.id " +
            "where authority='ROLE_CUSTOMER'",nativeQuery = true)
    List<Object[]> findAllRegisteredCustomers();


    @Query(value = "select username,email,authority " +
            "from User inner join Role on User.id = Role.id " +
            "where authority='ROLE_SELLER'",nativeQuery = true)
    List<Object[]> findAllRegisteredSellers();

}
