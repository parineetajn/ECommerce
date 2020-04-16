package com.example.Project.ECommerce.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //anonymous API's
                .antMatchers("/").anonymous()
                .antMatchers("/customerRegister").anonymous()
                .antMatchers("/sellerRegister").anonymous()
                //common API's
                .antMatchers("/forgetPassword").hasAnyRole("ADMIN","CUSTOMER","SELLER")
                .antMatchers("/resetPassword").hasAnyRole("ADMIN","CUSTOMER","SELLER")
                .antMatchers("/updateName").hasAnyRole("ADMIN","CUSTOMER")
                //.antMatchers("/editImage").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/updateAddress/{address_id}").hasAnyRole("CUSTOMER","SELLER")
                .antMatchers("/viewProduct/{productId}").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/doLogout").hasAnyRole("ADMIN","CUSTOMER","SELLER")
                //admin API's
                .antMatchers("/admin/*").hasAnyRole("ADMIN")
                //seller API's
                .antMatchers("/seller/*").hasAnyRole("SELLER")
                //customer API's
                .antMatchers("/customer/*").hasAnyRole("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }
}