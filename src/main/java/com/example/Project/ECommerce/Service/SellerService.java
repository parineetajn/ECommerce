package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Role;
import com.example.Project.ECommerce.Entity.Seller;
import com.example.Project.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;

    public Seller registerSeller(Seller seller){
        String password = seller.getPassword();
        seller.setPassword(passwordEncoder.encode(password));
        seller.addRoles(new Role("ROLE_SELLER"));

        userRepository.save(seller);
        if(userRepository.existsById(seller.getId()))
        {
            mailSenderService.sendLoginTokenMail(seller);
        }
        return seller;
    }



}
