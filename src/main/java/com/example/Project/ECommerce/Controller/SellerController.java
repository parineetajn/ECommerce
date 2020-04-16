package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.DTO.ProductDto;
import com.example.Project.ECommerce.DTO.SellerRegisterDto;
import com.example.Project.ECommerce.DTO.SellerViewProfileDto;
import com.example.Project.ECommerce.Exceptions.UserNotAuthorizedException;
import com.example.Project.ECommerce.Repository.*;
import com.example.Project.ECommerce.Service.CategoryService;
import com.example.Project.ECommerce.Service.ProductService;
import com.example.Project.ECommerce.Service.ProductVariationService;
import com.example.Project.ECommerce.Service.SellerService;
import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductVariationService productVariationService;

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Lazy
    @PostMapping("/sellerRegister")
    public String sellerRegistration(@Valid @RequestBody SellerRegisterDto seller) {
        return sellerService.registerSeller(seller);
    }

    @GetMapping("/seller/myProfile")
    public SellerViewProfileDto viewProfile(){
        return sellerService.viewProfile();
    }

    @PutMapping("/seller/updateProfile")
    public String editProfile(@RequestBody SellerViewProfileDto seller){
        return sellerService.editProfile(seller);
    }

    @PutMapping("/seller/editCompanyDetails")
    public void editCompanyDetails(@RequestBody Seller seller) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);
        seller1.setGST(seller.getGST());
        seller1.setCompanyName(seller.getCompanyName());
        seller1.setCompanyContact(seller.getCompanyContact());
        sellerRepository.save(seller1);
    }

    /*Product API's
        ->get products
        ->add products
        ->update product
        ->delete product
      */
    @GetMapping("/seller/listOfProducts")
    public List<Object[]> getListOfProducts() {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);
        List<Object[]> products = productRepository.findSellerProductList(username);
        return products;
    }

    @PostMapping("seller/addProduct/{category_id}")
    public String addProducts(@PathVariable(name = "category_id") long category_id, @RequestBody ProductDto product) {
    return productService.addNewProduct(product,category_id);
    }

    @PutMapping("/seller/updateProduct/{product_id}")
    public String updateProduct(@PathVariable(name = "product_id") long product_id, @RequestBody ProductDto product) {
        return productService.updateProduct(product, product_id);
    }

    @DeleteMapping("/seller/deleteProduct")
    public String deleteProduct(@RequestParam(name = "productId") long productId) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);

        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            if (seller1.getUsername().equals(product.getSeller().getUsername())) {
                productRepository.deleteProduct(productId);
                productVariationRepository.deleteProductVariation(productId);
            } else
                throw new UserNotAuthorizedException( "You are not authorized seller to delete the product");
        }else
            return "Product with id: "+productId+" is not Valid";
    return "Product with id: "+productId+ " deleted..";
    }

    /*ProductVariation API's
        ->get productVariations
        ->add productVariation
     */
    @GetMapping("/seller/getProductVariations/{product_id}")
    public Object getProductVariation(@PathVariable(name = "product_id")long product_id){
        Optional<Product> product= productRepository.findById(product_id);
        Product product1=product.get();
        if(product1.isIs_Active()) {
            return productVariationService.getProductVariation(product_id);
        }
        else
            return "No Product Variations available!";

    }

    @PostMapping("/seller/addProductVariation/{product_id}")
    public String addProductVariation(@PathVariable(name = "product_id") long product_id, @RequestBody ProductVariation productVariation){
        Optional<Product> product1 = productRepository.findById(product_id);
        if(product1.isPresent()){
            productVariationService.addProductVariation(productVariation,product_id);
            return "Product variation of Product id :"+product_id+" added!";
        }else {
            throw new UserNotFoundException("No Product id :" + product_id + " found!");
        }
    }

    /*Category API's
        ->get categories
        ->get subcategories
        ->add category
        ->add subcategory
     */

   /* @GetMapping("/getCategories")
    public List<Object[]> getCategories() {
        return categoryDao.getParentCategory();
    }

    @GetMapping("/getSubCategories/{CategoryName}")
    public List<Object[]> getSubcategories(@PathVariable(name = "CategoryName") String CategoryName) {
       return  categoryDao.getSubCategory(parent_id);
    }*/



}

