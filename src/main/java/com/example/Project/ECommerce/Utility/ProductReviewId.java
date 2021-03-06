package com.example.Project.ECommerce.Utility;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductReviewId implements Serializable {
    private long Customer_User_id;
    private long Product_id;

    public ProductReviewId() {
    }

    public ProductReviewId(long customer_User_id, long product_id) {
        Customer_User_id = customer_User_id;
        Product_id = product_id;
    }

    public long getCustomer_User_id() {
        return Customer_User_id;
    }

    public void setCustomer_User_id(long customer_User_id) {
        Customer_User_id = customer_User_id;
    }

    public long getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(long product_id) {
        Product_id = product_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductReviewId)) return false;
        ProductReviewId that = (ProductReviewId) o;
        return Customer_User_id == that.Customer_User_id &&
                Product_id == that.Product_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Customer_User_id, Product_id);
    }
}
