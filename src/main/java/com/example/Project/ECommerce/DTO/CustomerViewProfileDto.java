package com.example.Project.ECommerce.DTO;

import javax.validation.constraints.Pattern;

public class CustomerViewProfileDto extends UserViewProfileDto{

    @Pattern(regexp="(^$|[0-9]{10})")
    private String contact;

    public CustomerViewProfileDto() {
    }

    public CustomerViewProfileDto(long id, String firstName, String lastName, Boolean isActive, @Pattern(regexp = "(^$|[0-9]{10})") String contact) {
        super(id, firstName, lastName, isActive);
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "CustomerViewProfileDto{" +
                "contact='" + contact + '\'' +
                '}';
    }
}
