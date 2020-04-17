package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.DTO.CustomerRegisterDto;
import com.example.Project.ECommerce.DTO.CustomerViewProfileDto;
import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.InputException;
import com.example.Project.ECommerce.Exceptions.PasswordAndConfirmPasswordMismatchException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.AddressRepository;
import com.example.Project.ECommerce.Repository.CustomerRepository;
import com.example.Project.ECommerce.Repository.TokenRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class CustomerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;
    @Autowired
    ModelMapper modelMapper;

    public String registerCustomer(CustomerRegisterDto customer) {

        if (customer.getPassword().equals(customer.getConfirmPassword())) {
            Customer customer1 = modelMapper.map(customer, Customer.class);
            String password = customer.getPassword();
            customer1.setPassword(passwordEncoder.encode(password));
            customer1.addRoles(new Role("ROLE_CUSTOMER"));
            customer1.setCreatedBy(customer1.getUsername());
            userRepository.save(customer1);
            if (userRepository.existsById(customer1.getId())) {
                mailSenderService.sendLoginTokenMail(customer1);
            }
            return "You have been Registered successfully!";
        } else {
            throw new PasswordAndConfirmPasswordMismatchException("Password is not same as confirm password");
        }
    }

    public String editProfile(CustomerViewProfileDto customer) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Customer customer1 = customerRepository.findByUsername(username);
        if (customer.getContact() != null) {
            if(customer.getContact().matches("(^$|[0-9]{10})")){
                customer1.setContact(customer.getContact());
            }
            else
                throw new InputException("Phone number not valid..");
        }
        if (customer.getFirstName() != null) {
            customer1.setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null) {
            customer1.setLastName(customer.getLastName());
        }
        customerRepository.save(customer1);
        return "Profile updated!";
    }

    public CustomerViewProfileDto viewProfile() {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        CustomerViewProfileDto customerViewProfileDto = new CustomerViewProfileDto();
        customerViewProfileDto.setId(customer.getId());
        customerViewProfileDto.setContact(customer.getContact());
        customerViewProfileDto.setFirstName(customer.getFirstName());
        customerViewProfileDto.setLastName(customer.getLastName());
        customerViewProfileDto.setActive(customer.isActive());
        return customerViewProfileDto;
    }

    public String addNewAddress(Address address) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        User user = userRepository.findByUsername(username);
        System.out.println(user.getRoles());
        Iterator<Role> roleIterator = user.getRoles().iterator();

        while (roleIterator.hasNext()) {
            Role role = roleIterator.next();

            if (role.getAuthority().equals("ROLE_SELLER")) {
                if (!user.getAddress().isEmpty()) {
                    throw new InputException("You cannot add any new address!!");
                } else {

                    address.setUser(user);
                    user.addAddress(address);
                    user.setModifiedBy(username);
                    userRepository.save(user);
                    return "Address Added!!";
                }
            } else {
                address.setUser(user);
                user.addAddress(address);
                user.setModifiedBy(username);
                userRepository.save(user);
            }
        }
        return "success";
    }

    public String reSendActivationLink(String username)
    {
        User user= userRepository.findByUsername(username);
        if(user==null)
        {
            throw new UserNotFoundException("User not found!!");
        }else {
            for (Token token: tokenRepository.findAll())
            {
                if(user.getUsername().equals(token.getTokenName()) && !user.isActive())
                {
                    tokenRepository.deleteById(token.getTokenId());
                }
            }
            mailSenderService.sendLoginTokenMail(user);
        }
        return "Mail sent with re-send activation link..";
    }


}
