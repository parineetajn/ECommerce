package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.DTO.SellerRegisterDto;
import com.example.Project.ECommerce.DTO.SellerViewProfileDto;
import com.example.Project.ECommerce.Repository.*;
import com.example.Project.ECommerce.Service.SellerService;
import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Lazy
    @PostMapping("/sellerRegister")
    public String sellerRegistration(@Valid @RequestBody SellerRegisterDto seller) {
        return sellerService.registerSeller(seller);
    }

    @GetMapping("/seller/myProfile")
    public SellerViewProfileDto viewProfile(){
        return sellerService.viewProfile();
    }

    @PutMapping("/seller/updateProfile")
    public String editProfile(@RequestBody SellerViewProfileDto seller){
        return sellerService.editProfile(seller);
    }

    @PutMapping("/seller/editCompanyDetails")
    public void editCompanyDetails(@RequestBody Seller seller) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);
        seller1.setGST(seller.getGST());
        seller1.setCompanyName(seller.getCompanyName());
        seller1.setCompanyContact(seller.getCompanyContact());
        sellerRepository.save(seller1);
    }

}

