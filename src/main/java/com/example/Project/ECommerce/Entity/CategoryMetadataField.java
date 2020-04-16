package com.example.Project.ECommerce.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CategoryMetadataField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String Name;

    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }

    public void addFieldValues( CategoryMetadataFieldValues values){
        if(values!= null){
            if(categoryMetadataFieldValuesSet==null)
                categoryMetadataFieldValuesSet = new HashSet<>();

            categoryMetadataFieldValuesSet.add(values);
            values.setCategoryMetadataField(this);
        }
    }
    @Override
    public String toString() {
        return "CategoryMetadataField{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", categoryMetadataFieldValuesSet=" + categoryMetadataFieldValuesSet +
                '}';
    }
}