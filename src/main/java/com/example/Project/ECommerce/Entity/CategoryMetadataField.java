package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetadataField {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy ="categoryMetadataField",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValue> categoryMetadataFieldValuesSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<CategoryMetadataFieldValue> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValue> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryMetadataField{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                '}';
    }
}