package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.Address;
import com.example.Project.ECommerce.Entity.Role;
import com.example.Project.ECommerce.Entity.Token;
import com.example.Project.ECommerce.Exceptions.PasswordAndConfirmPasswordMismatchException;
import com.example.Project.ECommerce.PasswordValidation.PasswordValidatorConstraint;
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
import sun.nio.cs.US_ASCII;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@RestController
@Validated
public class UserController {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    TokenStore tokenStore;

    @Autowired
    UserRepository userRepository;

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

    @PutMapping("/updateName")
    public String editName(@RequestParam(name = "username")String username) {
            String username1 = getCurrentLoggedInUser.getCurrentUser();
            User user1 = userRepository.findByUsername(username1);
            user1.setUsername(username);
            userRepository.save(user1);
            return "Name Updated!";
        }

    @PutMapping("/updateAddress/{address_id}")
    public String editAddress(@PathVariable(name = "address_id")long address_id,
                              @Valid @RequestBody Address address){
          return userService.updateAddress(address,address_id);

    }

    @GetMapping("/getUser/{username}")
    public String getUser(@PathVariable(name = "username") String username){
        User user1= userRepository.findByUsername(username);
        Set<Role> role= user1.getRoles();
        String roleName = null;
        for(Role ele : role){
            roleName= ele.getAuthority();
        }
        return user1.getFirstName()+" "+ roleName + " "+ user1.getId() ;
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestBody User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1 != null) {
            mailSenderService.sendPasswordResetTokenMail(user1);
        } else
            throw new UserNotFoundException("User with username : " + user.getUsername() + " not found");
        return "Link to reset your password sent to your email..";
    }

    @PutMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable(value = "token")String token,
                                @RequestParam(value = "password") @PasswordValidatorConstraint String password,
                                @RequestParam(value = "confirmPassword") String confirmPassword){
        User user=new User();
        if(token!=null){
            user =tokenService.verifyToken(token);
        }
        User user1 = userRepository.findByUsername(user.getUsername());
        Boolean flag=userService.updatePassword(password,confirmPassword);
        if(flag){
            user1.setPassword(passwordEncoder.encode(confirmPassword));
            user1.setModifiedBy(user1.getUsername());
            mailSenderService.sendPasswordResetMail(user1);
            userRepository.save(user1);
            return "Password Updated!";
        }
        else
            throw new PasswordAndConfirmPasswordMismatchException("Password and confirm password are not same!");
    }

    @PatchMapping("/updatePassword")
    public String upPassword(@RequestBody User user){
        String username1 = getCurrentLoggedInUser.getCurrentUser();
        User user1 = userRepository.findByUsername(username1);
        String password=user.getPassword();
        String confirmPassword=user.getConfirmPassword();
        if(password!=null && confirmPassword!=null) {
            Boolean flag = userService.updatePassword(password, confirmPassword);
            if (flag) {
                user1.setPassword(passwordEncoder.encode(confirmPassword));
                user1.setModifiedBy(user1.getUsername());
                mailSenderService.sendPasswordResetMail(user1);
                userRepository.save(user1);
                return "Password Updated!";
            } else
                throw new PasswordAndConfirmPasswordMismatchException("Password and confirm password are not same!");
        }
        return  "Please enter values!!";

    }




}
