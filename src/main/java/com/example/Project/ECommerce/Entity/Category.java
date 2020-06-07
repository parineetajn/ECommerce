package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String categoryName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Category category;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Set<Category> categories;

    @OneToMany(mappedBy = "productCategory",cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(targetEntity = CategoryMetadataFieldValue.class,mappedBy = "category", cascade = CascadeType.ALL)
    private Set<CategoryMetadataFieldValue> categoryMetadataFieldValueSet;

    public Category() {
    }

    public Category(@NotEmpty String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CategoryMetadataFieldValue> getCategoryMetadataFieldValueSet() {
        return categoryMetadataFieldValueSet;
    }

    public void setCategoryMetadataFieldValueSet(Set<CategoryMetadataFieldValue> categoryMetadataFieldValueSet) {
        this.categoryMetadataFieldValueSet = categoryMetadataFieldValueSet;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + categoryName + '\'' +
                ", category=" + category +
                ", categories=" + categories +
                ", products=" + products +
                '}';
    }

}
