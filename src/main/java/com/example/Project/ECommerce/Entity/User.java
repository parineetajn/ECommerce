package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.PasswordValidation.passwordValidatorConstraint;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Column(unique = true)
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    @passwordValidatorConstraint
    @NotEmpty
    private String password;
    @Transient
    @NotEmpty
    @passwordValidatorConstraint
    private String confirmPassword;
    private boolean isDeleted;
    private boolean isActive;
    private boolean isEnable;

    public boolean isEnable() {
        return true;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public User(boolean isEnable) {
        this.isEnable = isEnable;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public User() {
    }

    public User(@NotEmpty @Email String email, @NotEmpty String username, String firstName, String middleName, String lastName, @NotEmpty String password, @NotEmpty String confirmPassword) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User(@NotEmpty @Email String email, @NotEmpty String username, String firstName, String middleName, String lastName, @NotEmpty String password, @NotEmpty String confirmPassword, boolean isDeleted, boolean isActive, boolean isEnable) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.isEnable = isEnable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                '}';
    }

    public void addRoles(Role role) {
        if (role != null) {
            if (roles == null) {
                roles = new HashSet<>();
            }
            roles.add(role);
        }
    }

    public void addAddress(Address addresss) {
        if (addresss != null) {
            if (address == null) {
                address = new HashSet<>();
            }
            address.add(addresss);
        }
    }


}
