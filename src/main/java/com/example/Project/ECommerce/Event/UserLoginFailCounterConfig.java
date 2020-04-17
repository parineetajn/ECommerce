package com.example.Project.ECommerce.Event;

import com.example.Project.ECommerce.Entity.User;
import com.example.Project.ECommerce.Entity.UserLoginFailCounter;
import com.example.Project.ECommerce.Repository.UserLoginFailCounterRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Service.MailSenderService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import java.util.LinkedHashMap;
import java.util.Optional;

@Configuration
public class UserLoginFailCounterConfig {

    @Autowired
    UserLoginFailCounterRepository userLoginFailCounterRepository;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Autowired
    private UserRepository userRepository;

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
        int counter;
        String username = (String) event.getAuthentication().getPrincipal();
        if ("access-token".contentEquals(username)) {
            System.out.println("invalid access token");
            return;
        }
        System.out.println(username+"----");
        Optional<UserLoginFailCounter> userLoginFailCounter = userLoginFailCounterRepository.findByUsername(username);

        if (!userLoginFailCounter.isPresent()) {
            UserLoginFailCounter userLoginFailCounter1 = new UserLoginFailCounter();
            userLoginFailCounter1.setAttempts(1);
            userLoginFailCounter1.setUsername(username);
            userLoginFailCounterRepository.save(userLoginFailCounter1);
        }
        if (userLoginFailCounter.isPresent()) {
            counter = userLoginFailCounter.get().getAttempts();
            System.out.println(counter);
            if (counter>=2) {
                User user = userRepository.findByUsername(username);
                user.setEnable(false);
                mailSenderService.sendAccountLockingMail(user);
                userRepository.save(user);
            }
            UserLoginFailCounter userLoginFailCounter1 = userLoginFailCounter.get();
            userLoginFailCounter1.setAttempts(++counter);
            userLoginFailCounter1.setUsername(username);
            System.out.println(userLoginFailCounter1+"-----------------");
            userLoginFailCounterRepository.save(userLoginFailCounter1);
        }

    }

    @EventListener
    public void authenticationPass(AuthenticationSuccessEvent event) {
        LinkedHashMap<String,String> userMap = new LinkedHashMap<>();
        try {
            userMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
        } catch (ClassCastException ex) {

        }
        String userEmail = "";
        try {
            userEmail = userMap.get("username");
        } catch (NullPointerException e) {
        }
        Optional<UserLoginFailCounter> userLoginFailCounter = userLoginFailCounterRepository.findByUsername(userEmail);
        userLoginFailCounter.ifPresent(loginFailCounter -> userLoginFailCounterRepository.deleteById(loginFailCounter.getId()));
    }
}
