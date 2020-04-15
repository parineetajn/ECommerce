package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Token;
import com.example.Project.ECommerce.Entity.User;
import com.example.Project.ECommerce.Repository.TokenRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String getToken(User user){
        Token token = new Token();

        String uu= UUID.randomUUID().toString();
        token.setRandomToken(uu);
        token.setTimeInMilli(System.currentTimeMillis());
        token.setTokenName(user.getUsername());
        tokenRepository.save(token);
        return uu;

    }

    public void verifyToken(String token){
        Token token1= null;
        for (Token token2: tokenRepository.findAll()){
            if(token2.getRandomToken().equals(token)){
                token1=token2;
            }
        }
        User user = userRepository.findByUsername(token1.getTokenName());
        userRepository.save(user);
    }
}
