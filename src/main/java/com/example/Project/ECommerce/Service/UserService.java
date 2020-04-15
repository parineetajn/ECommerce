package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.AppUser;
import com.example.Project.ECommerce.Entity.Role;
import com.example.Project.ECommerce.Entity.User;
import com.example.Project.ECommerce.PasswordValidation.passwordValidatorConstraint;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Security.GrantAuthorityImpl;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    String from = "parittn2020@gmail.com";

    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        Set<Role> roleSet = user.getRoles();
        Iterator<Role> roleIterator = roleSet.iterator();

        List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
        while (roleIterator.hasNext()) {
            Role role = roleIterator.next();
            grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority()));
        }
        System.out.println(user);

        if (username != null) {
            return new AppUser(user.getUsername(), user.getPassword(), user.getConfirmPassword(), grantAuthorityList, user.isEnable());
        } else {
            throw new UserNotFoundException(username + "not found");
        }
    }

    public void activateStatus(User user1, Integer id) throws MailException {
        user1.setEnable(true);
        System.out.println("Account activating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user1.getEmail());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Account activated");
        mailMessage.setText("Your account has been activated by the admin..You can now login..");
        javaMailSender.send(mailMessage);
        userRepository.save(user1);
        System.out.println("Mail sent..");

    }

    public void deActivateStatus(User user1, Integer id) throws MailException {
        user1.setEnable(false);
        System.out.println("Account DeActivating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user1.getEmail());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Account DeActivated");
        mailMessage.setText("Your account has been DeActivated by the admin..");
        javaMailSender.send(mailMessage);
        userRepository.save(user1);
        System.out.println("Mail sent..");
    }

    public Boolean updatePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
