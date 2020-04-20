package com.example.Project.ECommerce.DTO;

import javax.persistence.Id;

public class UserViewProfileDto {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private Boolean isEnable;

    public UserViewProfileDto() {
    }

    public UserViewProfileDto(long id, String firstName, String lastName, Boolean isEnable) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnable = isEnable;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return isEnable;
    }

    public void setActive(Boolean active) {
        isEnable = active;
    }

    @Override
    public String toString() {
        return "UserViewProfileDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}
