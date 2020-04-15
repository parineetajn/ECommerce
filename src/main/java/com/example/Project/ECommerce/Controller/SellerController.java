package com.example.Project.ECommerce.Controller;

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
    @PostMapping("/register/seller")
    public void registerSeller(@Valid @RequestBody Seller seller) {
        sellerService.registerSeller(seller);
    }

    @GetMapping("/myProfile/seller")
    public List<Object[]> viewProfile(){
        String username = getCurrentLoggedInUser.getCurrentUser();
        return sellerRepository.viewProfile(username);
    }

    @PutMapping("/updateProfile/seller")
    public String editProfile(@RequestBody Seller seller){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Seller seller1= sellerRepository.findByUsername(username);
            if (seller.getEmail() != null) {
                seller1.setEmail(seller.getEmail());
            }
            if (seller.getGST() != null) {
                seller1.setGST(seller.getGST());
            }
            if (seller.getCompanyContact() != null) {
                seller1.setCompanyContact(seller.getCompanyContact());
            }
            if (seller.getCompanyName() != null) {
                seller1.setCompanyName(seller.getCompanyName());
            }
            if (seller.getFirstName() != null) {
                seller1.setFirstName(seller.getFirstName());
            }
            if (seller.getLastName() != null) {
                seller1.setLastName(seller.getLastName());
            }
            if (seller.getUsername() != null) {
                seller1.setUsername(seller.getUsername());
            }
            sellerRepository.save(seller1);
            return "Profile updated!";
    }

    @PutMapping("/editCompanyDetails")
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
    @GetMapping("/listOfSellerProducts")
    public List<Object[]> getListOfProducts() {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);
        List<Object[]> products = productRepository.findSellerProductList(username);
        return products;
    }

   /* @PostMapping("/addProduct/{category}")
    public void addProducts(@PathVariable(name = "category") String category, @RequestBody Product product) {
        List<Object[]> objects = categoryService.getCategory();
        for (Object[] objects1 : objects) {
            if (objects1[0].toString().equals(category)) {
                productService.addProduct(product,category);
                break;
            }
        }
    }*/

    /*@PutMapping("/updateProduct/{productName}")
    public void updateProduct(@PathVariable(name = "productName") String productName, @RequestBody Product product){
        Product product1 = productRepository.findProductName(productName);
        product1.setName(product.getName());
        product1.setIs_Returnable(product.isIs_Returnable());
        product1.setIs_Active(product.isIs_Active());
        product1.setIs_Cancellable(product.isIs_Cancellable());
        product1.setBrand(product.getBrand());
        product1.setDescription(product.getDescription());
        productRepository.save(product1);
    }
*/
    @DeleteMapping("/deleteProduct")
    public String deleteProduct(@RequestParam(name = "productId") int productId) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);

        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            if (seller1.getUsername().equals(product.getSeller().getUsername())) {
                productRepository.deleteProduct(productId);
                productVariationRepository.deleteProductVariation(productId);
            } else
                return "You are not authorized seller to delete the product";
        }else
            return "Product with id: "+productId+" is not Valid";
    return "Product with id: "+productId+ " deleted..";
    }

    /*ProductVariation API's
        ->get productVariations
        ->add productVariation
     */
    @GetMapping("/getProductVariations/{productId}")
    public Object getProductVariation(@PathVariable(name = "productId")int productId){
        Optional<Product> product= productRepository.findById(productId);
        Product product1=product.get();
        if(product1.isIs_Active()) {
            return productVariationService.getProductVariation(productId);
        }
        else
            return "No Product Variations available!";

    }

    /*@PostMapping("/addProductVariation/{productName}")
    public void addProductVariation(@PathVariable(name = "productName") String productName, @RequestBody ProductVariation productVariation){
        Product product1 = productRepository.findProductName(productName);
        if(product1!=null){
            productVariationService.addProductVariation(productVariation,productName);
        }
    }*/

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

