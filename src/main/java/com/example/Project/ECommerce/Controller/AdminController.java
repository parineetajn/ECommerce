package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Service.TokenService;
import com.example.Project.ECommerce.Service.UserService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Admin home";
    }

    @PostMapping("/admin/verify")
    public String verifyToken(@RequestParam(name = "token") String token) {
        tokenService.verifyToken(token);
        return "token verified..";
    }

    @GetMapping("/admin/getAllRegisteredCustomers")
    public List<Map<String,Object>> getRegisteredCustomer() {
        List<Map<String,Object>> registeredCustomers = userService.findAllRegisteredCustomers();
       /* for (Object[] objects : registeredCustomers) {
            System.out.println("UserName: " + objects[0]);
        }*/
      /*  for (Object[] ob : registeredCustomers) {
            String key = (String) ob[0];
            String value = (String) ob[1];
        }*/

        return registeredCustomers;
    }

    @GetMapping("/admin/getAllRegisteredSellers")
    public List<Map<String,Object>> getRegisteredSeller() {
        List<Map<String,Object>> registeredSellers = userService.findAllRegisteredSellers();
        return registeredSellers;
    }

    @PutMapping("/admin/changeRole")
    public void changeRole(@RequestParam(name = "role") Role role) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        User user1 = userRepository.findByUsername(username);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user1.setRoles(roleSet);
        userRepository.save(user1);
    }

    @PutMapping("/admin/ActivateUserStatus/{id}")
    public String activateStatus(@PathVariable(name = "id") Long id) {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user1 = user.get();
            if (user1.isEnable()) {
                return "User account is already activated!";
            } else {
                userService.activateStatus(user1);
                return "Status of User activated with id: " + id;
            }
        } else throw new UserNotFoundException(" User Not Found... with id : " + id);
    }

    @PutMapping("/admin/deActivateUserStatus/{id}")
    public String deActivateStatus(@PathVariable(name = "id") Long id) {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user1 = user.get();
            if (!user1.isEnable()) {
                return "User account is already DeActivated!";
            } else {
                userService.deActivateStatus(user1);
                return "Status of User Deactivated with id: " + id;
            }
        } else throw new UserNotFoundException(" User Not Found...with id : " + id);
    }
}
