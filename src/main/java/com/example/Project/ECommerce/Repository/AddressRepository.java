package com.example.Project.ECommerce.Repository;

import com.example.Project.ECommerce.Entity.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address,Long> {

    @Transactional
    @Modifying
    @Query(value = "delete Address from Address" +
            " inner join User  on User.id=Address.User_id " +
            "inner join Customer on Customer.id=User.id " +
            "where Address.id=:address_id",nativeQuery = true)
    void deleteAddress(@Param(value = "address_id") long address_id);

    @Query(value = "select Address.id,addressLine,state,city,country,label,zipCode from Address inner join User on Address.User_id=User.id where username=:username",nativeQuery = true)
    List<Object[]> viewAddress(@Param(value = "username")String username);
}
