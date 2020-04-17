package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    private String brand;
    private String description;
    private boolean is_Cancellable;
    private boolean is_Returnable;
    private boolean is_Active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category productCategory;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductReview> productReviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductVariation> productVariations;

    public Product() {
    }

    public Product(@NotEmpty String name, String description, boolean is_Cancellable, boolean is_Returnable, String brand, boolean is_Active) {
        this.name = name;
        this.description = description;
        this.is_Cancellable = is_Cancellable;
        this.is_Returnable = is_Returnable;
        this.brand = brand;
        this.is_Active = is_Active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isIs_Active() {
        return is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        this.is_Active = is_Active;
    }

    public Category getCategoryInProduct() {
        return productCategory;
    }

    public void setCategoryInProduct(Category categoryInProduct) {
        this.productCategory = categoryInProduct;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public Set<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(Set<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", is_Cancellable=" + is_Cancellable +
                ", is_Returnable=" + is_Returnable +
                ", brand='" + brand + '\'' +
                ", is_Active=" + is_Active +
                '}';
    }

}
