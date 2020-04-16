package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Utility.HashMapConverter;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Order_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;
    private double price;


    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> product_variation_metadata;

    @OneToOne
    @JoinColumn(name = "Order_id")
    private Orders order;

    @OneToOne
    private Order_Status order_status;

    @ManyToOne
    @JoinColumn(name = "Product_Variation_Id")
    private ProductVariation productVariation;

    public Order_Product() {
    }

    public Order_Product(long quantity, double price, Map<String, Object> product_variation_metadata) {
        this.quantity = quantity;
        this.price = price;
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

    public Map<String, Object> getProduct_variation_metadata() {
        return product_variation_metadata;
    }

    public void setProduct_variation_metadata(Map<String, Object> product_variation_metadata) {
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
