package com.example.Project.ECommerce.DTO;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class ProductDto {
    @NotEmpty
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String brand;
    private String description;
    private boolean is_Cancellable;
    private boolean is_Returnable;
    private boolean is_Active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_Cancellable() {
        return is_Cancellable;
    }

    public void setIs_Cancellable(boolean is_Cancellable) {
        this.is_Cancellable = is_Cancellable;
    }

    public boolean isIs_Returnable() {
        return is_Returnable;
    }

    public void setIs_Returnable(boolean is_Returnable) {
        this.is_Returnable = is_Returnable;
    }

    public boolean isIs_Active() {
        return is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        this.is_Active = is_Active;
    }
}
