package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Seller extends User{

    private String GST;
    @Pattern(regexp="(^$|[0-9]{10})")
    private String companyContact;
    @NotEmpty
    private String companyName;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Product> products;

    public Seller() {
    }

    public Seller(@NotEmpty @Email String email, @NotEmpty String username, String firstName, String middleName, String lastName, @NotEmpty @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}", message = "Password should contain 8-15 characters with at least 1 Lowercase, 1 Uppercase, 1 Special Character and 1 Number") String password, @NotEmpty @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}", message = "Password should contain 8-15 characters with at least 1 Lowercase, 1 Uppercase, 1 Special Character and 1 Number") String confirmPassword, boolean isDeleted, boolean isActive, boolean isEnable, String GST, @Pattern(regexp = "(^$|[0-9]{10})") String companyContact, @NotEmpty String companyName) {
        super(email, username, firstName, middleName, lastName, password, confirmPassword, isDeleted, isActive, isEnable);
        this.GST = GST;
        this.companyContact = companyContact;
        this.companyName = companyName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Seller{" +
                ", GST=" + GST +
                ", companyContact=" + companyContact +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
