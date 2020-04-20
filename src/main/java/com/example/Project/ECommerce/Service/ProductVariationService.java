package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Product;
import com.example.Project.ECommerce.Entity.ProductVariation;
import com.example.Project.ECommerce.Entity.Seller;
import com.example.Project.ECommerce.Exceptions.InputException;
import com.example.Project.ECommerce.Exceptions.UserNotAuthorizedException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.*;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductVariationService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;
    @Autowired
    ObjectMapper objectMapper;


    public void addProductVariation(ProductVariation productVariation, long product_id)throws JsonProcessingException {

        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);
        Optional<Product> productOptional = productRepository.findById(product_id);
        if(productOptional.isPresent()){
                Product product = productOptional.get();
                if (product.isActive()) {
                    if ((product.getSeller().getUsername()).equals(seller.getUsername())) {
                        productVariation.setProduct(product);
                        Map<String, String> stringMap = new HashMap<String, String>();
                        Map<String, Object> newMap = productVariation.getMetadataAttributes();
                        long category_id = productRepository.getCategoryId(product.getId());
                        List<Long> metadata_id = categoryMetadataFieldValuesRepository.getMetadataId(category_id);

                        for (long metadataField_id : metadata_id) {
                            String metadata = categoryMetadataFieldRepository.getMetadataName(metadataField_id);
                            String metadataValues = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category_id, metadataField_id);
                            stringMap.put(metadata, metadataValues);
                        }
                        int count = 0;
                        for (String key : newMap.keySet()) {
                            if (stringMap.containsKey(key)) {
                                String str = stringMap.get(key);
                                Object obj = newMap.get(key);
                                String[] strings = str.split(",");
                                List<String> list = Arrays.asList(strings);
                                if (list.contains(obj)) {
                                    count++;
                                }
                            }
                        }
                        if (count == newMap.size()) {
                            String metadataInfo = objectMapper.writeValueAsString(productVariation.getMetadataAttributes());
                            productVariation.setMetadata(metadataInfo);
                            productVariation.setActive(true);
                            productVariation.setQuantityAvailable(productVariation.getQuantityAvailable());
                            productVariation.setPrimaryImageName(productVariation.getPrimaryImageName());
                            productVariation.setPrice(productVariation.getPrice());
                            productVariationRepository.save(productVariation);
                        } else
                            throw new InputException("Field values are not correct");
                    } else
                        throw new UserNotAuthorizedException("You are not authorized to add product variations of this Product!");
                }
                }else
            throw new UserNotFoundException("Product Not Found");
        }

    public List<Object[]> getProductVariation(long productVariation_id) {
        String seller = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(seller);
        Long product_id = productVariationRepository.getProductId(productVariation_id);
        Optional<Product> product = productRepository.findById(product_id);
        if (product.isPresent()) {
            Product product1 = product.get();
            if (product1.isActive()) {
                if ((product1.getSeller().getUsername()).equals(seller1.getUsername())) {
                    List<Object[]> objects = productVariationRepository.getProductVariation(productVariation_id);
                    return objects;
                } else
                    throw new UserNotAuthorizedException("You are not authorized to view the Product variations");
            } else
                throw new UserNotFoundException("No Product Variation Found!");
        } else throw new UserNotFoundException("Product not Found!");
    }

    public List<Object[]> getAllProductVariations(long product_id) {
        String seller = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(seller);
        Optional<Product> product = productRepository.findById(product_id);

        if (product.isPresent()) {
            Product product1 = product.get();
            if (product1.isActive()) {
                if ((product1.getSeller().getUsername()).equals(seller1.getUsername())) {
                    return productVariationRepository.getAllProductVariation(product_id);
                } else
                    throw new UserNotAuthorizedException("You are not authorized to view the Product variations");
            } else
                throw new UserNotFoundException("No Product Variation Found!");
        } else throw new UserNotFoundException("Product not Found!");
    }

    public void updateProductVariation(ProductVariation productVariation, long productVariation_id)throws JsonProcessingException {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);
        long product_id = productVariationRepository.getProductId(productVariation_id);
        Optional<Product> productOptional = productRepository.findById(product_id);
        if(productOptional.isPresent()){
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariation_id);
        if (productVariationOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.isActive()) {
                if ((product.getSeller().getUsername()).equals(seller.getUsername())) {
                    Map<String, String> stringMap = new HashMap<String, String>();
                    Map<String, Object> newMap = productVariation.getMetadataAttributes();
                    long category_id = productRepository.getCategoryId(product.getId());
                    List<Long> metadata_id = categoryMetadataFieldValuesRepository.getMetadataId(category_id);

                    for (long metadataField_id : metadata_id) {
                        String metadata = categoryMetadataFieldRepository.getMetadataName(metadataField_id);
                        String metadataValues = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category_id, metadataField_id);
                        stringMap.put(metadata, metadataValues);
                    }
                    int count = 0;
                    for (String key : newMap.keySet()) {
                        if (stringMap.containsKey(key)) {
                            String str = stringMap.get(key);
                            Object obj = newMap.get(key);
                            String[] strings = str.split(",");
                            List<String> list = Arrays.asList(strings);
                            if (list.contains(obj)) {
                                count++;
                            }
                        }
                    }
                    if (count == newMap.size()) {
                        Optional<ProductVariation> productVariation1 = productVariationRepository.findById(productVariation_id);
                        ProductVariation productVariation2 = productVariation1.get();
                        productVariation2.setActive(productVariation.isActive());
                        productVariation2.setQuantityAvailable(productVariation.getQuantityAvailable());
                        productVariation2.setPrice(productVariation.getPrice());
                        String metadataInfo = objectMapper.writeValueAsString(productVariation.getMetadataAttributes());
                        productVariation2.setMetadata(metadataInfo);
                        productVariationRepository.save(productVariation2);
                    } else
                        throw new InputException("Field values are not correct");
                } else
                    throw new UserNotAuthorizedException("You are not authorized to update product variations of this Product!");
            }
        } else
            throw new UserNotFoundException("Product Variation Not Found");
    } else
            throw new UserNotFoundException("Product Not Found");
}

    public void checkAndUpdateQuantity(long productVariation_id, int requiredQuantity) {
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariation_id);
        ProductVariation productVariation = productVariationOptional.get();
        int presentQuantity = productVariation.getQuantityAvailable();
        int newQuantityAvailable = presentQuantity - requiredQuantity;
        if (newQuantityAvailable < 0) {
            throw new InputException("Ordered Quantity is not available!");
        }
        productVariation.setQuantityAvailable(newQuantityAvailable);
        productVariationRepository.save(productVariation);

    }
}
