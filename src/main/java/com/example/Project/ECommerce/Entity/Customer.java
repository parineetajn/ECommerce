package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {

    @Pattern(regexp="(^$|[0-9]{10})")
    private String contact;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductReview> productReviews;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Orders> orders;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    public Customer() {
    }

    public Customer(String contact) {
        this.contact = contact;
    }

    public Customer(@Email @NotEmpty String username, String firstName, String middleName, String lastName, @NotEmpty @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}", message = "Password should contain 8-15 characters with at least 1 Lowercase, 1 Uppercase, 1 Special Character and 1 Number") String password, @NotEmpty @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}", message = "Password should contain 8-15 characters with at least 1 Lowercase, 1 Uppercase, 1 Special Character and 1 Number") String confirmPassword, boolean isDeleted, boolean isActive, boolean isEnable, @Pattern(regexp = "(^$|[0-9]{10})") String contact) {
        super(username, firstName, middleName, lastName, password, confirmPassword, isDeleted, isActive, isEnable);
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact=" + contact +
                '}';
    }

}
