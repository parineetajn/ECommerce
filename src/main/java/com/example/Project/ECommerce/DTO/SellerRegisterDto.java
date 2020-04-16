package com.example.Project.ECommerce.DTO;

import javax.validation.constraints.Pattern;

public class SellerRegisterDto extends UserRegisterDto{
    private String GST;
    private String companyName;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String companyContact;

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
}
