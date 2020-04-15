package com.example.Project.ECommerce.Utility;

import com.example.Project.ECommerce.Entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetCurrentLoggedInUser{

    public String getCurrentUser(){
       Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String username;
       User user=null;
       if(principal instanceof UserDetails){
           username=((UserDetails)principal).getUsername();
            System.out.println("currently logged in user: "+username);

        }
       else {
           username=principal.toString();
           System.out.println("user: "+username);

       }
       return username;
    }

}
