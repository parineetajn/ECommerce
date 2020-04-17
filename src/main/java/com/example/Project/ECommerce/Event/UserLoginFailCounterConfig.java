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
import org.springframework.stereotype.Component;

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
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event){
        int counter=0;
        String username=getCurrentLoggedInUser.getCurrentUser();
        Optional<UserLoginFailCounter> userLoginFailCounter=userLoginFailCounterRepository.findByUsername(username);

        if (!userLoginFailCounter.isPresent()){
             {
                UserLoginFailCounter userLoginFailCounter1 = new UserLoginFailCounter();
                userLoginFailCounter1.setAttempts(counter);
                userLoginFailCounter1.setUsername(username);
                userLoginFailCounterRepository.save(userLoginFailCounter1);
            }
        }else {
            counter = userLoginFailCounter.get().getAttempts();
            System.out.println(counter);
            UserLoginFailCounter userLoginFailCounter1 = userLoginFailCounter.get();
            userLoginFailCounter1.setAttempts(counter++);
            userLoginFailCounter1.setUsername(username);
            userLoginFailCounterRepository.save(userLoginFailCounter1);
        }
            if (counter>=3){
                User user=userRepository.findByUsername(username);
                user.setEnable(false);
                mailSenderService.sendAccountLockingMail(user);
                userRepository.save(user);
            }


        }


  /*  //if registered then clear the previous attempts
    @EventListener
    public void authenticationPass(AuthenticationSuccessEvent event){
        LinkedHashMap<String,String> userMap=new LinkedHashMap<>();
        try {
            userMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();//return whole object as a map...
        }
        catch(ClassCastException ex){
        }
        String username=new String();
        try{
            username=userMap.get("username");////username is a field..
        }
        catch (NullPointerException ex){

        }
        Optional<UserLoginFailCounter>userLoginFailCounter=userLoginFailCounterRepository.findByUsername(username);
        if (userLoginFailCounter.isPresent()){
            userLoginFailCounterRepository.deleteById(userLoginFailCounter.get().getId());
        }
    }*/
}
