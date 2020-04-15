package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.PasswordValidation.passwordValidatorConstraint;
import com.example.Project.ECommerce.Security.GrantAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {

    @NotEmpty
    @Column(unique = true)
    private String username;
    @passwordValidatorConstraint
    @NotEmpty
    private String password;
    @Transient
    @NotEmpty
    @passwordValidatorConstraint
    private String confirmPassword;
    List<GrantAuthorityImpl> grantAuthorities;
    private boolean isEnabled;

    public AppUser() {
    }

    public AppUser(@NotEmpty String username, @NotEmpty String password, @NotEmpty String confirmPassword, List<GrantAuthorityImpl> grantAuthorities, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.grantAuthorities = grantAuthorities;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setGrantAuthorities(List<GrantAuthorityImpl> grantAuthorities) {
        this.grantAuthorities = grantAuthorities;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grantAuthorities=" + grantAuthorities +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
