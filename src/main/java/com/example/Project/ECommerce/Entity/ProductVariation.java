package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Utility.HashMapConverter;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long quantity_available;
    private double price;
    private String primaryImageName;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> metadata;

    private boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Cart> carts;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Order_Product> order_products;

    public ProductVariation() {
    }

    public ProductVariation(long quantity_available, double price, String primaryImageName, boolean isActive) {
        this.quantity_available = quantity_available;
        this.price = price;
        this.primaryImageName = primaryImageName;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(long quantity_available) {
        this.quantity_available = quantity_available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public Set<Order_Product> getOrder_products() {
        return order_products;
    }

    public void setOrder_products(Set<Order_Product> order_products) {
        this.order_products = order_products;
    }

    @Override
    public String toString() {
        return "ProductVariation{" +
                "id=" + id +
                ", quantity_available=" + quantity_available +
                ", price=" + price +
                ", primaryImageName='" + primaryImageName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
