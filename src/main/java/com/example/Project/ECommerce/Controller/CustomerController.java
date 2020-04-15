package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Exceptions.UserNotAuthorizedException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
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
    @PostMapping("/register/customer")
    public void registerCustomer(@Valid @RequestBody Customer customer){
        customerService.registerCustomer(customer);
    }

    @GetMapping("/myProfile/customer")
    public List<Object[]> viewProfile(){
        String username = getCurrentLoggedInUser.getCurrentUser();
        return customerRepository.viewProfile(username);
    }

    @PutMapping("/updateProfile/customer")
    public String editProfile(@RequestBody Customer customer){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer1= customerRepository.findByUsername(username);

            if (customer.getEmail() != null) {
                customer1.setEmail(customer.getEmail());
            }
            if (customer.getContact() != null) {
                customer1.setContact(customer.getContact());
            }
            if (customer.getFirstName() != null) {
                customer1.setFirstName(customer.getFirstName());
            }
            if (customer.getLastName() != null) {
                customer1.setLastName(customer.getLastName());
            }
            if (customer.getUsername() != null) {
                customer1.setUsername(customer.getUsername());
            }
            customerRepository.save(customer);

            return "Profile updated!";

    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody Address address){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        Address address1 = new Address();
        address1.setAddressLine(address.getAddressLine());
        address1.setCity(address.getCity());
        address1.setCountry(address.getCountry());
        address1.setState(address.getState());
        address1.setLabel(address.getLabel());
        address1.setZipCode(address.getZipCode());
        address1.setUser(customer);
        addressRepository.save(address1);
        return "Address added with id: "+address1.getId();
    }

    @GetMapping("viewAddress")
    public List<Object[]> viewAddress(){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        return addressRepository.viewAddress(username);
    }

    @DeleteMapping("/deleteAddress/{address_id}")
    public String deleteAddress(@PathVariable(name = "address_id")int address_id){
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

    @GetMapping("/listOfProducts/{categoryId}")
    public List<Object[]> getListOfProducts(@PathVariable(name = "categoryId")int categoryId) {
        List<Object[]> products = productRepository.findProductList(categoryId);
        return products;
    }

    @GetMapping("/ProductDetails/{productName}")
    public List<Object[]> getDetailedProducts(@PathVariable(name = "productName") String productName) {
        List<Object[]> products = productRepository.findProductDetails(productName);
        return products;
    }

    @PostMapping("/addProductReview/{productName}")
    public String addProductReview(@PathVariable(name = "productName") String productName,
                                   @RequestBody ProductReview productReview){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        Product product =productRepository.findProductName(productName);

        ProductReview productReview1 =new ProductReview();
        productReview1.setCustomer(customer);
        productReview1.setRating(productReview.getRating());
        productReview1.setReview(productReview.getReview());
        productReview1.setProduct(product);
        productReviewRepository.save(productReview1);
        return "Review added!";
    }


}
