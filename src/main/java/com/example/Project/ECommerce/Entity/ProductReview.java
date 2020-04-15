package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Utility.ProductReviewId;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class ProductReview {

    @EmbeddedId
    private ProductReviewId id =new ProductReviewId();

    private String review;
    @Size(max = 10)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "Customer_User_id")
    @MapsId("Customer_User_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "Product_id")
    @MapsId("Product_id")
    private Product product;

    public ProductReview() {
    }

    public ProductReview(String review, @Size(max = 10) int rating) {
        this.review = review;
        this.rating = rating;
    }

    public ProductReview(String review, @Size(max = 10) int rating, Customer customer, Product product) {
        this.review = review;
        this.rating = rating;
        this.customer = customer;
        this.product = product;
    }

    public ProductReviewId getId() {
        return id;
    }

    public void setId(ProductReviewId id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", Review='" + review + '\'' +
                ", Rating=" + rating +
                '}';
    }
}
