package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Repository.AddressRepository;
import com.example.Project.ECommerce.Repository.CustomerRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Security.GrantAuthorityImpl;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    String from = "parittn2020@gmail.com";

    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        Set<Role> roleSet = user.getRoles();
        Iterator<Role> roleIterator = roleSet.iterator();

        List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
        while (roleIterator.hasNext()) {
            Role role = roleIterator.next();
            grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority()));
        }
        System.out.println(user);

        if (username != null) {
            System.out.println("---------->" + user.isEnable());
            return new AppUser(user.getUsername(), user.getPassword(), user.getConfirmPassword(), grantAuthorityList, user.isEnable());
        } else {
            throw new UserNotFoundException(username + "not found");
        }
    }

    public void activateStatus(User user1) throws MailException {
        user1.setEnable(true);
        System.out.println("Account activating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user1.getUsername());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Account activated");
        mailMessage.setText("Your account has been activated by the admin..You can now login..");
        javaMailSender.send(mailMessage);
        userRepository.save(user1);
        System.out.println("Mail sent..");

    }

    public void deActivateStatus(User user1) throws MailException {
        user1.setEnable(false);
        System.out.println("Account DeActivating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user1.getUsername());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Account DeActivated");
        mailMessage.setText("Your account has been DeActivated by the admin..");
        javaMailSender.send(mailMessage);
        userRepository.save(user1);
        System.out.println("Mail sent..");
    }

    public Boolean updatePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public String updateAddress(Address address, long address_id) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        Optional<Address> addressOptional = addressRepository.findById(address_id);
        if (addressOptional.isPresent()) {
            Address address1 = addressOptional.get();

            if (address.getAddressLine() != null) {
                address1.setAddressLine(address.getAddressLine());
            }
            if (address.getCity() != null) {
                address1.setCity(address.getCity());
            }
            if (address.getCountry() != null) {
                address1.setCountry(address.getCountry());
            }
            if (address.getState() != null) {
                address1.setState(address.getState());
            }
            if (address.getLabel() != null) {
                address1.setLabel(address.getLabel());
            }
            customer.addAddress(address1);
            customerRepository.save(customer);

        } else {
            return "Address with id: " + address_id + " does not exist!";
        }
        return "Address updated!";
    }

    public List<Map<String, Object>> findAllRegisteredCustomers() {
        List<Object[]> list = userRepository.findAllRegisteredCustomers();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object[] o : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("Id:", o[0]);
            map.put("Username:", o[1]);
            map.put("Role:", o[2]);
            mapList.add(map);
        }
        return mapList;
    }

    public List<Map<String,Object>> findAllRegisteredSellers() {
        List<Object[]> list = userRepository.findAllRegisteredSellers();

        List<Map<String,Object>> mapList=new ArrayList<>();
        for(Object[] o:list){
            Map<String,Object> map=new HashMap<>();
            map.put("Id:",o[0]);
            map.put("Username:",o[1]);
            map.put("Role:",o[2]);
            mapList.add(map);
        }
        return mapList;
    }
}
