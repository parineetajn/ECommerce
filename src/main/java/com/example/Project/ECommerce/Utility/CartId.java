package com.example.Project.ECommerce.Utility;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartId implements Serializable {

    private long Customer_User_id;
    private long Product_Variation_id;

    public CartId() {
    }

    public CartId(long customer_User_id, long product_Variation_id) {
        Customer_User_id = customer_User_id;
        Product_Variation_id = product_Variation_id;
    }

    public long getCustomer_User_id() {
        return Customer_User_id;
    }

    public void setCustomer_User_id(long customer_User_id) {
        Customer_User_id = customer_User_id;
    }

    public long getProduct_Variation_id() {
        return Product_Variation_id;
    }

    public void setProduct_Variation_id(long product_Variation_id) {
        Product_Variation_id = product_Variation_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartId)) return false;
        CartId cartId = (CartId) o;
        return Customer_User_id == cartId.Customer_User_id &&
                Product_Variation_id == cartId.Product_Variation_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Customer_User_id, Product_Variation_id);
    }
}
