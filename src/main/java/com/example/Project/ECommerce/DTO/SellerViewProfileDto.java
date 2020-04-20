package com.example.Project.ECommerce.DTO;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SellerViewProfileDto extends UserViewProfileDto{

    private String GST;
    private String companyName;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String companyContact;

    private String addressLine;
    private String city;
    private String state;
    private int zipCode;
    private String country;
    private String label;

    public SellerViewProfileDto() {
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    @Override
    public String toString() {
        return "SellerViewProfileDto{" +
                "GST='" + GST + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyContact='" + companyContact + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode=" + zipCode +
                ", country='" + country + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
