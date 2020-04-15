package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Repository.SellerRepository;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    String from = "parittn2020@gmail.com";

    public void addProduct(Product product1, String category){
        String username= getCurrentLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(username);
        int id= categoryRepository.getIdOfParentCategory(category);
        Set<Product> productSet = new HashSet<>();

        Product product = new Product();
        product.setName(product1.getName());
        product.setDescription(product1.getDescription());
        product.setBrand(product1.getBrand());
        product.setIs_Cancellable(product1.isIs_Cancellable());
        product.setIs_Active(product1.isIs_Active());
        product.setIs_Returnable(product1.isIs_Returnable());
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category1 =optionalCategory.get();
        product.setCategoryInProduct(category1);
        product.setSeller(seller1);
        productSet.add(product);

        productRepository.save(product) ;
    }
    public void activateProductStatus(Product product, Integer id) throws MailException {
        product.setIs_Active(true);
        System.out.println("Account activating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(product.getSeller().getEmail());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Product activated");
        mailMessage.setText("Your Product "+product.getName()+" has been activated by the admin..");
        javaMailSender.send(mailMessage);
        productRepository.save(product);
        System.out.println("Mail sent..");

    }

    public void deActivateProductStatus(Product product, Integer id) throws MailException {
        product.setIs_Active(false);
        System.out.println("Account activating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(product.getSeller().getEmail());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Product Deactivated");
        mailMessage.setText("Your Product "+product.getName()+" has been Deactivated by the admin..");
        javaMailSender.send(mailMessage);
        productRepository.save(product);
        System.out.println("Mail sent..");
    }

}
