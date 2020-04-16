package com.example.Project.ECommerce.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CustomerRegisterDto extends UserRegisterDto{
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})")
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
