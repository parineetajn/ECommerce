package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.DTO.ProductDto;
import com.example.Project.ECommerce.Entity.Customer;
import com.example.Project.ECommerce.Entity.Product;
import com.example.Project.ECommerce.Entity.ProductReview;
import com.example.Project.ECommerce.Entity.Seller;
import com.example.Project.ECommerce.Exceptions.UserNotAuthorizedException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.*;
import com.example.Project.ECommerce.Service.ProductService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/customer/listOfProducts/{categoryId}")
    public List<Object[]> getListOfProducts(@PathVariable(name = "categoryId")long categoryId) {
        return productRepository.findProductList(categoryId);
    }

    @GetMapping("/customer/ProductDetails/{product_id}")
    public List<Object[]> getDetailedProducts(@PathVariable(name = "product_id") long product_id) {
        return productRepository.findProductDetails(product_id);
    }

    @PostMapping("/customer/addProductReview/{product_id}")
    public String addProductReview(@PathVariable(name = "product_id") long product_id,
                                   @RequestBody ProductReview productReview){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Customer customer= customerRepository.findByUsername(username);
        Optional<Product> optionalProduct =productRepository.findById(product_id);
        Product product=optionalProduct.get();
        ProductReview productReview1 =new ProductReview();
        productReview1.setCustomer(customer);
        productReview1.setRating(productReview.getRating());
        productReview1.setReview(productReview.getReview());
        productReview1.setProduct(product);
        productReviewRepository.save(productReview1);
        return "Review added!";
    }

    @GetMapping("/customer/getSimilarProducts/{product_id}")
    public List<Object[]> getSimilarProducts(@PathVariable(name = "product_id")long product_id){
        return productService.getSimilarProducts(product_id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping("/admin/ActivateProductStatus/{id}")
    public String activateProductStatus(@PathVariable(name = "id") Long id) {
        Product product1 = null;
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product1 = product.get();
            if (product1.isIs_Active()) {
                return "Product already activate!";
            } else {
                productService.activateProductStatus(product1);
                return "Product with id: " + id+" activated ";
            }
        } else throw new UserNotFoundException(" Product Not Found... with id : " + id);
    }

    @PutMapping("/admin/deActivateProductStatus/{id}")
    public String deActivateProductStatus(@PathVariable(name = "id") Long id) {
        Product product1 = null;
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product1 = product.get();
            if (!product1.isIs_Active()) {
                return "Product already Deactivate!";
            } else {
                productService.deActivateProductStatus(product1);
                return "Product with id: " + id+" Deactivated ";
            }
        } else throw new UserNotFoundException(" Product Not Found... with id : " + id);
    }


    @GetMapping("/admin/listOfProducts")
    public List<Object[]> getListOfAdminProducts(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                 @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        List<Object[]> products = productRepository.findAdminProductList();
        return products;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/viewProduct/{productId}")
    public Object getProduct(@PathVariable(name = "productId")long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            List<Object[]> prod=productRepository.findProduct(productId);
            return prod;
        }
        else
            throw new UserNotFoundException("Product not found!");
    }
}
