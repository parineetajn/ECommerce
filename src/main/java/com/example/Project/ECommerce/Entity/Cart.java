package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Utility.CartId;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Cart {

    @EmbeddedId
    private CartId id=new CartId();

    @Size(min = 1)
    private long quantity;
    private boolean is_WishList_Item;

    @OneToOne
    @JoinColumn(name = "Customer_User_id")
    @MapsId("Customer_User_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "Product_Variation_id")
    @MapsId("Product_Variation_id")
    private ProductVariation productVariation;

    public Cart() {
    }

    public Cart(@Size(min = 1) long quantity, boolean is_WishList_Item) {
        this.quantity = quantity;
        this.is_WishList_Item = is_WishList_Item;
    }

    public CartId getId() {
        return id;
    }

    public void setId(CartId id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public boolean isIs_WishList_Item() {
        return is_WishList_Item;
    }

    public void setIs_WishList_Item(boolean is_WishList_Item) {
        this.is_WishList_Item = is_WishList_Item;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", is_WishList_Item=" + is_WishList_Item +
                '}';
    }
}
