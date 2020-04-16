package com.example.Project.ECommerce.Entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Order_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Positive
    private long quantity;
    @Positive
    private double price;

    @Column(name = "createdDate")
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "modifiedDate")
    @LastModifiedDate
    private LocalDateTime modifiedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    private String product_variation_metadata;

    @ManyToOne
    @JoinColumn(name = "Order_id")
    private Orders order;

    @OneToOne
    private Order_Status order_status;

    @ManyToOne
    @JoinColumn(name = "Product_Variation_Id")
    private ProductVariation productVariation;

    public Order_Product() {
    }


    public Order_Product(@Positive long quantity, @Positive double price, LocalDateTime createdOn, LocalDateTime modifiedOn, String createdBy, String modifiedBy, String product_variation_metadata) {
        this.quantity = quantity;
        this.price = price;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.product_variation_metadata = product_variation_metadata;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getProduct_variation_metadata() {
        return product_variation_metadata;
    }

    public void setProduct_variation_metadata(String product_variation_metadata) {
        this.product_variation_metadata = product_variation_metadata;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Order_Status getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Order_Status order_status) {
        this.order_status = order_status;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    @Override
    public String toString() {
        return "Order_Product{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product_variation_metadata='" + product_variation_metadata + '\'' +
                '}';
    }
}
