package com.example.Project.ECommerce.Entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double amountPaid;
    private LocalDateTime dateCreated;
    private String paymentMethod;
    private String customerAddressCity;
    private String customerAddressState;
    private String customerAddressCountry;
    private String customerAddressAddressLine;
    @Size(min = 6,max = 6,message = "ZipCode should have 6 digits")
    private int customerAddressZipCode;
    private String customerAddressLabel;

    @Column(name = "createdDate")
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "modifiedDate")
    @LastModifiedDate
    private LocalDateTime modifiedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Order_Product> order_products;

    @ManyToOne
    @JoinColumn(name = "Customer_User_id")
    private Customer customer;

    public Orders() {
    }

    public Orders(double amountPaid, LocalDateTime dateCreated, String paymentMethod, String customerAddressCity, String customerAddressState, String customerAddressCountry, String customerAddressAddressLine, int customerAddressZipCode, String customerAddressLabel) {
        this.amountPaid = amountPaid;
        this.dateCreated = dateCreated;
        this.paymentMethod = paymentMethod;
        this.customerAddressCity = customerAddressCity;
        this.customerAddressState = customerAddressState;
        this.customerAddressCountry = customerAddressCountry;
        this.customerAddressAddressLine = customerAddressAddressLine;
        this.customerAddressZipCode = customerAddressZipCode;
        this.customerAddressLabel = customerAddressLabel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerAddressCity() {
        return customerAddressCity;
    }

    public void setCustomerAddressCity(String customerAddressCity) {
        this.customerAddressCity = customerAddressCity;
    }

    public String getCustomerAddressState() {
        return customerAddressState;
    }

    public void setCustomerAddressState(String customerAddressState) {
        this.customerAddressState = customerAddressState;
    }

    public String getCustomerAddressCountry() {
        return customerAddressCountry;
    }

    public void setCustomerAddressCountry(String customerAddressCountry) {
        this.customerAddressCountry = customerAddressCountry;
    }

    public String getCustomerAddressAddressLine() {
        return customerAddressAddressLine;
    }

    public void setCustomerAddressAddressLine(String customerAddressAddressLine) {
        this.customerAddressAddressLine = customerAddressAddressLine;
    }

    public int getCustomerAddressZipCode() {
        return customerAddressZipCode;
    }

    public void setCustomerAddressZipCode(int customerAddressZipCode) {
        this.customerAddressZipCode = customerAddressZipCode;
    }

    public String getCustomerAddressLabel() {
        return customerAddressLabel;
    }

    public void setCustomerAddressLabel(String customerAddressLabel) {
        this.customerAddressLabel = customerAddressLabel;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Set<Order_Product> getOrder_products() {
        return order_products;
    }

    public void setOrder_products(Set<Order_Product> order_products) {
        this.order_products = order_products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", amountPaid=" + amountPaid +
                ", dateCreated=" + dateCreated +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", customerAddressCity='" + customerAddressCity + '\'' +
                ", customerAddressState='" + customerAddressState + '\'' +
                ", customerAddressCountry='" + customerAddressCountry + '\'' +
                ", customerAddressAddressLine='" + customerAddressAddressLine + '\'' +
                ", customerAddressZipCode=" + customerAddressZipCode +
                ", customerAddressLabel='" + customerAddressLabel + '\'' +
                '}';
    }
}
