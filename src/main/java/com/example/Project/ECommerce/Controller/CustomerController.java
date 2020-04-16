package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.DTO.CustomerRegisterDto;
import com.example.Project.ECommerce.DTO.CustomerViewProfileDto;
import com.example.Project.ECommerce.Repository.AddressRepository;
import com.example.Project.ECommerce.Service.CustomerService;
import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Repository.CustomerRepository;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Repository.ProductReviewRepository;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Lazy
    @PostMapping("/customerRegister")
    public String customerRegistration(@Valid @RequestBody CustomerRegisterDto customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping("/customer/myProfile")
    public CustomerViewProfileDto viewProfile(){
        return customerService.viewProfile();
    }

    @PutMapping("/customer/updateProfile")
    public String editProfile(@RequestBody CustomerViewProfileDto customer){
       return customerService.editProfile(customer);
    }

    @PostMapping("/customer/addAddress")
    public String addAddress(@Valid @RequestBody Address address){
        return customerService.addNewAddress(address);
    }

    @GetMapping("/customer/viewAddress")
    public List<Object[]> viewAddress(){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        return addressRepository.viewAddress(username);
    }

    @DeleteMapping("/customer/deleteAddress/{address_id}")
    public String deleteAddress(@PathVariable(name = "address_id")long address_id){
        Optional<Address> optionalAddress =addressRepository.findById(address_id);
        Address address =optionalAddress.get();
        if(address.getClass()==null){
            return "No Address with id : "+ address_id +" found!";
        }
        else{
            addressRepository.deleteAddress(address_id);
            return "Address deleted with id: "+address_id;
        }
    }

    @GetMapping("/customer/listOfProducts/{categoryId}")
    public List<Object[]> getListOfProducts(@PathVariable(name = "categoryId")long categoryId) {
        return productRepository.findProductList(categoryId);
    }

    @GetMapping("/customer/ProductDetails/{product_id}")
    public List<Object[]> getDetailedProducts(@PathVariable(name = "product_id") long product_id) {
        return productRepository.findProductDetails(product_id);
    }

    @PostMapping("/customer/addProductReview/{product_id}")
    public String addProductReview(@PathVariable(name = "product_id") long product_id,
                                   @RequestBody ProductReview productReview){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        Optional<Product> optionalProduct =productRepository.findById(product_id);
        Product product=optionalProduct.get();
        ProductReview productReview1 =new ProductReview();
        productReview1.setCustomer(customer);
        productReview1.setRating(productReview.getRating());
        productReview1.setReview(productReview.getReview());
        productReview1.setProduct(product);
        productReviewRepository.save(productReview1);
        return "Review added!";
    }


}
