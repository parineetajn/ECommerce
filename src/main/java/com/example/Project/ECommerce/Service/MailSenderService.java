package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    TokenService tokenService;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender=javaMailSender;
    }

    @Lazy
    @Autowired
    MailSenderService mailSenderService;

    private JavaMailSender javaMailSender;

    @Async
    public void sendMailToAdmin(User user,String text) throws MailException{
        System.out.println("Sending mail..");
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getUsername());
        mail.setFrom("parittn2020@gmail.com");
        mail.setSubject("Changes Made by user");
        mail.setText(text);

        javaMailSender.send(mail);

        System.out.println("Mail sent..");
    }
    @Async
    public void sendLoginTokenMail(User user) throws MailException{
        System.out.println("Sending mail..");
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getUsername());
        mail.setFrom("parittn2020@gmail.com");
        mail.setSubject("Token for current login:");
        mail.setText(tokenService.getToken(user));

        javaMailSender.send(mail);

        System.out.println("Mail sent..");
    }

    @Async
    public void sendPasswordResetTokenMail(User user) throws MailException{
        System.out.println("Sending mail..");
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getUsername());
        mail.setFrom("parittn2020@gmail.com");
        mail.setSubject("Token to Reset your Password:");
        mail.setText(tokenService.getToken(user));

        javaMailSender.send(mail);

        System.out.println("Mail sent..");
    }
    @Async
    public void sendPasswordResetMail(User user) throws MailException{
        System.out.println("Sending mail..");
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getUsername());
        mail.setFrom("parittn2020@gmail.com");
        mail.setSubject("Password Updated..");
        mail.setText("Your Password has been updated recently..");

        javaMailSender.send(mail);

        System.out.println("Mail sent..");
    }
}
