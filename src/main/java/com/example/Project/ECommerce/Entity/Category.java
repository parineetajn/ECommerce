package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String categoryName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Category category;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Set<Category> categories;

    @OneToMany(mappedBy = "categoryInProduct",cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;

    public Category() {
    }

    public Category(@NotEmpty String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void addSubCategory(Category category){
        if(category != null){
            if(categories == null)
                categories = new HashSet<>();
            categories.add(category);
            category.setCategory(this);
        }
    }
    public void addProduct(Product product){
        if(product != null){
            if(products == null)
                products = new HashSet<Product>();
            products.add(product);
            product.setCategoryInProduct(this);
        }
    }
    public void addFieldValues(CategoryMetadataFieldValues categoryMetadataFieldValues){
        if(categoryMetadataFieldValues != null){
            if(categoryMetadataFieldValuesSet==null)
                categoryMetadataFieldValuesSet = new HashSet<>();
            categoryMetadataFieldValuesSet.add(categoryMetadataFieldValues);
            categoryMetadataFieldValues.setCategory(this);
        }
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
