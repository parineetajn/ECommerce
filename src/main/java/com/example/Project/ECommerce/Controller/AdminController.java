package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Service.TokenService;
import com.example.Project.ECommerce.Service.UserService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import com.example.Project.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<Object[]> getRegisteredCustomer() {
        List<Object[]> registeredCustomers = userRepository.findAllRegisteredCustomers();
        for (Object[] objects : registeredCustomers) {
            System.out.println("UserName: " + objects[0]);
        }
        return registeredCustomers;
    }

    @GetMapping("/admin/getAllRegisteredSellers")
    public List<Object[]> getRegisteredSeller() {
        List<Object[]> registeredSellers = userRepository.findAllRegisteredSellers();
        for (Object[] objects : registeredSellers) {
            System.out.println("UserName: " + objects[0]);
        }
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
