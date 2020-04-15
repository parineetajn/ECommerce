package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Order_Product order_product;

    @ManyToOne
    @JoinColumn(name = "Customer_User_id")
    private Customer customer;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
