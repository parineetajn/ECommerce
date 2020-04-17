package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.DTO.ProductDto;
import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.InputException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Repository.SellerRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    String from = "parittn2020@gmail.com";

    public String addProduct(Product product, long category_id){
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);
        Set<Product> productSet = new HashSet<>();
        Product product1= new Product();
        product1.setBrand(product.getBrand());
        product1.setIs_Active(false);
        product1.setIs_Cancellable(product.isIs_Cancellable());
        product1.setDescription(product.getDescription());
        product1.setName(product.getName());
        product1.setSeller(seller);
        product1.setCategoryInProduct(categoryRepository.findById(category_id).get());
        productSet.add(product1);
        productRepository.save(product1);
        return "Product added!";
    }
    public String addNewProduct(ProductDto product, Long category_id) {
        int result = categoryRepository.checkIfLeafCategory(category_id);
        if (result == 0) {
            if (product.getName().equals(categoryRepository.findById(category_id).get().getCategoryName()) || product.getName().equals(product.getBrand())) {
                throw new InputException("Product ,Category,Brand Name cannot be same");
            }

            Product product1 = modelMapper.map(product, Product.class);
            addProduct(product1, category_id);
        } else {
            throw new InputException("This category is not a leaf category");
        }

        long product_id= productRepository.findProductId(product.getName());
        long id = userRepository.findAdmin();
        User user = userRepository.findById(id).get();
        String text = "Product Brand: " + product.getBrand() + "\n" + "Product Description: " +
                product.getDescription() + "\n" + "Product ID: " + product_id;
        mailSenderService.sendMailToAdmin(user, text);
        return "Mail sent!";
    }

    public String updateProduct(ProductDto product,long product_id) {
        Optional<Product> optionalProduct = productRepository.findById(product_id);
        if (optionalProduct.isPresent()) {
            Product product1=optionalProduct.get();
            if (product.getName() != null) {
                product1.setName(product.getName());
            }
            if (product.getBrand() != null) {
                product1.setBrand(product.getBrand());
            }
            if (product.getDescription() != null) {
                product1.setDescription(product.getDescription());
            }
            product1.setId(product_id);
            product1.setIs_Returnable(product.isIs_Returnable());
            product1.setIs_Active(product.isIs_Active());
            product1.setIs_Cancellable(product.isIs_Cancellable());
            productRepository.save(product1);
            return "Product updated with id: " + product_id;
        } else {
            throw new UserNotFoundException("Product Not found!!");
        }
    }


    public void activateProductStatus(Product product) throws MailException {
        product.setIs_Active(true);
        System.out.println("Account activating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(product.getSeller().getUsername());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Product activated");
        mailMessage.setText("Your Product "+product.getName()+" has been activated by the admin..");
        javaMailSender.send(mailMessage);
        productRepository.save(product);
        System.out.println("Mail sent..");

    }

    public void deActivateProductStatus(Product product) throws MailException {
        product.setIs_Active(false);
        System.out.println("Account activating... mail sending..");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(product.getSeller().getUsername());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Product Deactivated");
        mailMessage.setText("Your Product "+product.getName()+" has been Deactivated by the admin..");
        javaMailSender.send(mailMessage);
        productRepository.save(product);
        System.out.println("Mail sent..");
    }

    public List<Object[]> getSimilarProducts(long product_id){

        long category_id=productRepository.getCategoryId(product_id);
        return productRepository.getSimilarProducts(category_id);

    }

}
