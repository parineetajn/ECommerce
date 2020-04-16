package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Product;
import com.example.Project.ECommerce.Entity.ProductVariation;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariationService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    public void addProductVariation(ProductVariation productVariation, long product_id){

        Optional<Product> productOptional =productRepository.findById(product_id);
        Product product=productOptional.get();
        productVariation.setProduct(product);
        productVariation.setPrice(productVariation.getPrice());
        productVariation.setQuantity_available(productVariation.getQuantity_available());
        productVariation.setPrimaryImageName(productVariation.getPrimaryImageName());
        productVariationRepository.save(productVariation);
    }

    public List<Object[]> getProductVariation(long product_id){
        List<Object[]> objects = productVariationRepository.getProductVariation(product_id);
        return objects;
    }
}
