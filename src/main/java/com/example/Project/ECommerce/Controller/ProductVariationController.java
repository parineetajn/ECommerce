package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.Product;
import com.example.Project.ECommerce.Entity.ProductVariation;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Repository.ProductVariationRepository;
import com.example.Project.ECommerce.Service.ProductVariationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;


    @GetMapping("/seller/getProductVariation/{productVariation_id}")
    public List<Object[]> getProductVariation(@PathVariable(name = "productVariation_id")long productVariation_id){
        return productVariationService.getProductVariations(productVariation_id);
    }

    @GetMapping("/seller/getAllProductVariations/{product_id}")
    public List<ProductVariation> getAllProductVariation(@PathVariable(name = "product_id")long product_id,
                                                         @RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                         @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                         @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
       List<ProductVariation> productVariationList= productVariationService.getAllProductVariations(product_id,paging);
       return productVariationList;
    }


    @PostMapping("/seller/addProductVariation/{product_id}")
    public String addProductVariation(@PathVariable(name = "product_id") long product_id, @Valid @RequestBody ProductVariation productVariation) throws JsonProcessingException {
        Optional<Product> product1 = productRepository.findById(product_id);
        if(product1.isPresent()){
            productVariationService.addProductVariation(productVariation,product_id);
            return "Product variation of Product id :"+product_id+" added!";
        }else {
            throw new UserNotFoundException("No Product id :" + product_id + " found!");
        }
    }

    @PutMapping("/seller/updateProductVariation/{productVariation_id}")
    public void updateProductVariation(@PathVariable(name = "productVariation_id")long productVariation_id,
                                       @Valid @RequestBody ProductVariation productVariation) throws JsonProcessingException {
        productVariationService.updateProductVariation(productVariation,productVariation_id);

    }

}
