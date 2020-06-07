package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "select username from User where id=:id",nativeQuery = true)
    String findUsernameById(@Param(value = "id")Long id);

    @Query(value = "select User.id,username,authority " +
            "from User inner join Role on User.id = Role.id " +
            "where authority='ROLE_CUSTOMER'",nativeQuery = true)
    List<Object[]> findAllRegisteredCustomers();

    @Query(value = "select User.id,username,authority " +
            "from User inner join Role on User.id = Role.id " +
            "where authority='ROLE_SELLER'",nativeQuery = true)
    List<Object[]> findAllRegisteredSellers();

    @Query(value = "select u.id from User u " +
            "inner join Role on Role.id=u.id " +
            "where authority=\"ROLE_ADMIN\"\n",nativeQuery = true)
    long findAdmin();

}
