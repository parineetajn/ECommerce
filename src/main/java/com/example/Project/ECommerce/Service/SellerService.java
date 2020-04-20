package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.DTO.SellerRegisterDto;
import com.example.Project.ECommerce.DTO.SellerViewProfileDto;
import com.example.Project.ECommerce.Entity.Address;
import com.example.Project.ECommerce.Entity.Role;
import com.example.Project.ECommerce.Entity.Seller;
import com.example.Project.ECommerce.Exceptions.InputException;
import com.example.Project.ECommerce.Exceptions.PasswordAndConfirmPasswordMismatchException;
import com.example.Project.ECommerce.Repository.SellerRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SellerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    public String registerSeller(SellerRegisterDto seller){
        if (seller.getPassword().equals(seller.getConfirmPassword())) {
            Seller seller1 = modelMapper.map(seller, Seller.class);
            String password = seller.getPassword();
            seller1.setPassword(passwordEncoder.encode(password));
            seller1.addRoles(new Role("ROLE_SELLER"));
            seller1.setCreatedBy(seller.getUsername());
            userRepository.save(seller1);

            if (userRepository.existsById(seller1.getId())) {
                mailSenderService.sendLoginTokenMail(seller1);

            }
            return "You have been Registered successfully!";
        } else {
            throw new PasswordAndConfirmPasswordMismatchException("Password is not same as confirm password");
        }
    }

    public String editProfile(SellerViewProfileDto seller){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Seller seller1= sellerRepository.findByUsername(username);

        if (seller.getGST() != null) {
            seller1.setGST(seller.getGST());
        }
        if (seller.getCompanyContact() != null) {
            if(seller.getCompanyContact().matches("(^$|[0-9]{10})")){
                seller1.setCompanyContact(seller.getCompanyContact());
            }
            else
                throw new InputException("Phone contact number not valid..");
        }
        if (seller.getCompanyName() != null) {
            seller1.setCompanyName(seller.getCompanyName());
        }
        if (seller.getFirstName() != null) {
            seller1.setFirstName(seller.getFirstName());
        }
        if (seller.getLastName() != null) {
            seller1.setLastName(seller.getLastName());
        }
        sellerRepository.save(seller1);
        return "Profile updated!";
    }

    public SellerViewProfileDto viewProfile() {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);

        SellerViewProfileDto sellerViewProfileDto=new SellerViewProfileDto();
        sellerViewProfileDto.setId(seller.getId());
        sellerViewProfileDto.setCompanyContact(seller.getCompanyContact());
        sellerViewProfileDto.setCompanyName(seller.getCompanyName());
        sellerViewProfileDto.setGST(seller.getGST());
        sellerViewProfileDto.setFirstName(seller.getFirstName());
        sellerViewProfileDto.setLastName(seller.getLastName());
        sellerViewProfileDto.setActive(seller.isEnable());

        Set<Address> addressSet=seller.getAddress();
        for(Address address:addressSet){
            sellerViewProfileDto.setCity(address.getCity());
            sellerViewProfileDto.setAddressLine(address.getAddressLine());
            sellerViewProfileDto.setCountry(address.getCountry());
            sellerViewProfileDto.setLabel(address.getLabel());
            sellerViewProfileDto.setZipCode(address.getZipCode());
            sellerViewProfileDto.setState(address.getState());
        }
        return sellerViewProfileDto;
    }


}
