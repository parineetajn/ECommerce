package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Customer;
import com.example.Project.ECommerce.Entity.Role;
import com.example.Project.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;

    public Customer registerCustomer(Customer customer){
        String password = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(password));
        customer.addRoles(new Role("ROLE_CUSTOMER"));

        userRepository.save(customer);
        if(userRepository.existsById(customer.getId()))
        {
            mailSenderService.sendLoginTokenMail(customer);
        }
        return customer;

    }

}
