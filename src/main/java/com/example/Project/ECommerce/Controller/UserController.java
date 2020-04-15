package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.Address;
import com.example.Project.ECommerce.Entity.Customer;
import com.example.Project.ECommerce.Entity.Product;
import com.example.Project.ECommerce.PasswordValidation.passwordValidatorConstraint;
import com.example.Project.ECommerce.Repository.AddressRepository;
import com.example.Project.ECommerce.Repository.CustomerRepository;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Service.TokenService;
import com.example.Project.ECommerce.Service.UserService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import com.example.Project.ECommerce.Entity.User;
import com.example.Project.ECommerce.Exceptions.NoLoggedInUserFoundException;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Service.MailSenderService;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class UserController {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        } else {
            throw new NoLoggedInUserFoundException("Cannot LogOut, No User found Logged-In");
        }
        return "Logged out successfully";
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to the home page";
    }

    @PostMapping("/verify")
    public void verifyToken(@RequestParam(name = "token") String token) {
        tokenService.verifyToken(token);
        System.out.println("token verified..");
    }

    @PutMapping("/editName")
    public void editName(@RequestParam(name = "username")String username) {
            String username1 = getCurrentLoggedInUser.getCurrentUser();
            User user1 = userRepository.findByUsername(username1);
            user1.setUsername(username);
            userRepository.save(user1);
        }

    @PutMapping("/updateAddress/{address_id}")
    public String editAddress(@PathVariable(name = "address_id")int address_id,@RequestBody Address address){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        Optional<Address> addressOptional=addressRepository.findById(address_id);
        if(addressOptional.isPresent()){
            Address address1=addressOptional.get();
            if(address.getAddressLine()!=null){
                address1.setAddressLine(address.getAddressLine());
            }
            if(address.getCity()!=null){
                address1.setCity(address.getCity());
            }
            if(address.getCountry()!=null){
                address1.setCountry(address.getCountry());
            }
            if(address.getState()!=null){
                address1.setState(address.getState());
            }
            if (address.getLabel()!=null){
                address1.setLabel(address.getLabel());
            }
            if(address.getZipCode()!=0){
                address1.setZipCode(address.getZipCode());
            }

            customer.addAddress(address1);
            customerRepository.save(customer);
        }
        else {
            return "Address with id: " + address_id + " does not exist!";
        }
        return "Address updated!";

    }

    @PostMapping("/forgetPassword")
    public void forgetPassword(@RequestParam(name = "email") String email) {
        User user1 = userRepository.findByEmail(email);
        if (user1 != null) {
            mailSenderService.sendPasswordResetTokenMail(user1);
        } else
            throw new UserNotFoundException("User with email : " + email + " not found");
    }

    @PutMapping("/resetPassword")
    public String resetPassword(@RequestParam(name = "token",required = false) String token,
                              @RequestParam(value = "password") @passwordValidatorConstraint String password,
                              @RequestParam(value = "confirmPassword") @passwordValidatorConstraint String confirmPassword){
        String username = getCurrentLoggedInUser.getCurrentUser();
        if(token!=null){
            tokenService.verifyToken(token);
        }
        User user1 = userRepository.findByUsername(username);
        Boolean flag=userService.updatePassword(password,confirmPassword);
        if(flag){
            user1.setPassword(passwordEncoder.encode(confirmPassword));
            mailSenderService.sendPasswordResetMail(user1);
            userRepository.save(user1);
            return "Password Updated!";
        }
        else
            return "Token invalid or Password Does not match!";
    }
    @GetMapping("/viewProduct/{productId}")
    public Object getProduct(@PathVariable(name = "productId")int productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            List<Object[]> prod=productRepository.findProduct(productId);
            return prod;
        }
        else
            return "Product not found!!";
    }


}
