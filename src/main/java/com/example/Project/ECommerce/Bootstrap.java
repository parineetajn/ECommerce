package com.example.Project.ECommerce;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldValuesRepository;
import com.example.Project.ECommerce.Repository.ProductReviewRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import com.example.Project.ECommerce.Utility.CategoryMetadataFieldValuesId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductReviewRepository productReviewRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

        @Autowired
        ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

            Set<User> users = new HashSet<>();

            //Demo Admin
            User admin = new User();
            admin.setUsername("parittn2020@gmail.com");
            admin.setFirstName("Parineeta");
            admin.setLastName("Jain");
            admin.setEnable(true);
            admin.setPassword(passwordEncoder.encode("Pass@1234"));
            admin.setCreatedBy(admin.getUsername());
            admin.addRoles(new Role("ROLE_ADMIN"));
            admin.addAddress(new Address("Noida sec-144","UP","India","74hhdksjv",110095,"Office",admin));
            admin.addAddress(new Address("Karol Bagh","Delhi","India","53/16 gdc",110005,"home",admin));
            users.add(admin);
            userRepository.save(admin);

            //Demo Customer
            Customer customer =new Customer();
            customer.setFirstName("customer1");
            customer.setUsername("parineeta1996@gmail.com");
            customer.setPassword(passwordEncoder.encode("Pass@1234"));
            customer.setEnable(true);
            customer.setContact("9873313200");
            customer.addRoles(new Role("ROLE_CUSTOMER"));
            customer.addAddress(new Address("Karol Bagh1","Delhi","India","53/16 gdc",110005,"home",customer));
            users.add(customer);
            userRepository.save(customer);

            //demo Category
            Category category = new Category("Electronics");
            Set<Category> categorySet = new HashSet<>();

            Category phoneCategory = new Category("Phone");
            Category TVCategory = new Category("TV");

            //phone sub category
            Category phoneCategory1=new Category("3G Phone");
            Category phoneCategory2=new Category("Keypad Phone");
            Category phoneCategory3=new Category("Smart Phone");

            //Tv sub category
            Category TVCategory1=new Category("Smart Tv");
            Category TVCategory2=new Category("LED Tv");
            Category TVCategory3=new Category("LCD tv");

            //adding to set
            categorySet.add(phoneCategory);
            categorySet.add(phoneCategory1);
            categorySet.add(phoneCategory2);
            categorySet.add(phoneCategory3);
            categorySet.add(TVCategory);
            categorySet.add(TVCategory1);
            categorySet.add(TVCategory2);
            categorySet.add(TVCategory3);

            //assigning parent
            phoneCategory.setCategory(category);
            TVCategory.setCategory(category);

            phoneCategory1.setCategory(phoneCategory);
            phoneCategory2.setCategory(phoneCategory);
            phoneCategory3.setCategory(phoneCategory);

            TVCategory1.setCategory(TVCategory);
            TVCategory2.setCategory(TVCategory);
            TVCategory3.setCategory(TVCategory);

            category.setCategories(categorySet);
            phoneCategory.setCategories(categorySet);
            TVCategory.setCategories(categorySet);

           //demo Product
            Set<Product> productSet =new HashSet<>();
            Product PhoneProduct1 =new Product("Iphone8","is a good product",true,true,"Apple",true);
            Product PhoneProduct2 =new Product("Samsung Galaxy S9","is a good product",false,true,"Samsung",true);
            Product PhoneProduct3 =new Product("IphoneX","is a good product",true,true,"Apple",false);
            Product PhoneProduct4 =new Product("Nokia 6.1 Plus ","is a good product",true,true,"Nokia",true);
            Product PhoneProduct5 =new Product("Iphone11","is a good product",false,true,"Apple",true);

            Product TvProduct1 = new Product("samsung1","it is a nice tv",true,true,"samsung",false);
            Product TvProduct2 = new Product("samsung2","it is a nice tv",true,true,"samsung",true);
            Product TvProduct3 = new Product("VideoCon","it is a nice tv",true,true,"VideoCon",true);
            Product TvProduct4 = new Product("Lg1","it is a nice tv",true,true,"LG",true);
            Product TvProduct5 = new Product("Lg ","it is a nice tv",true,true,"LG",true);

            PhoneProduct1.setCategoryInProduct(phoneCategory3);
            PhoneProduct3.setCategoryInProduct(phoneCategory3);
            PhoneProduct5.setCategoryInProduct(phoneCategory3);
            PhoneProduct2.setCategoryInProduct(phoneCategory1);
            PhoneProduct4.setCategoryInProduct(phoneCategory2);

            TvProduct1.setCategoryInProduct(TVCategory1);
            TvProduct2.setCategoryInProduct(TVCategory1);
            TvProduct3.setCategoryInProduct(TVCategory3);
            TvProduct4.setCategoryInProduct(TVCategory2);
            TvProduct5.setCategoryInProduct(TVCategory2);

            productSet.add(PhoneProduct1);
            productSet.add(PhoneProduct2);
            productSet.add(PhoneProduct3);
            productSet.add(PhoneProduct4);
            productSet.add(PhoneProduct5);
            productSet.add(TvProduct1);
            productSet.add(TvProduct2);
            productSet.add(TvProduct3);
            productSet.add(TvProduct4);
            productSet.add(TvProduct5);

            //product review
            ProductReview productReview1 = new ProductReview();
            productReview1.setCustomer(customer);
            productReview1.setRating(9);
            productReview1.setProduct(PhoneProduct1);
            productReview1.setReview("Good Product");

            ProductReview productReview2 = new ProductReview();
            productReview2.setCustomer(customer);
            productReview2.setRating(8);
            productReview2.setProduct(PhoneProduct2);
            productReview2.setReview("Nice Product");

            productReviewRepository.save(productReview1);
            productReviewRepository.save(productReview2);

            //Demo Seller
            Set<Seller> sellerSet =new HashSet<>();

            Seller seller1 = new Seller();
            seller1.setFirstName("seller1");
            seller1.setUsername("atuljn100@gmail.com");
            seller1.setEnable(true);
            seller1.setPassword(passwordEncoder.encode("Pass@1234"));
            seller1.setCreatedBy("atuljn100@gmail.com");
            seller1.setGST("100193841737");
            seller1.setCompanyContact("982738641");
            seller1.setCompanyName("seller1.co");
            seller1.addRoles(new Role("ROLE_SELLER"));
            seller1.addAddress(new Address("Karol Bagh","Delhi","India","53/16 gdc",110005,"home",seller1));
            seller1.setProducts(productSet);

            Seller seller2 = new Seller();
            seller2.setFirstName("seller2");
            seller2.setUsername("jainsarita72@gmail.com");
            seller2.setPassword(passwordEncoder.encode("Pass@1234"));
            seller2.setGST("100193841737");
            seller2.setEnable(true);
            seller2.setCompanyContact("982738641");
            seller2.setCompanyName("seller2.co");
            seller2.addRoles(new Role("ROLE_SELLER"));
            seller2.addAddress(new Address("Karol Bagh","Delhi","India","53/16 gdc",110005,"home",seller2));
            seller2.setProducts(productSet);

            Seller seller3 = new Seller();
            seller3.setFirstName("seller3");
            seller3.setUsername("anubhajn128@gmail.com");
            seller3.setEnable(true);
            seller3.setPassword(passwordEncoder.encode("Pass@1234"));
            seller3.setGST("100193841737");
            seller3.setCompanyContact("982738641");
            seller3.setCompanyName("seller.co");
            seller3.addRoles(new Role("ROLE_SELLER"));
            seller3.addAddress(new Address("Karol Bagh","Delhi","India","53/16 gdc",110005,"home",seller3));

            seller3.setProducts(productSet);

            sellerSet.add(seller1);
            sellerSet.add(seller2);
            sellerSet.add(seller3);

            PhoneProduct1.setSeller(seller1);
            PhoneProduct2.setSeller(seller2);
            PhoneProduct3.setSeller(seller3);
            PhoneProduct4.setSeller(seller1);
            PhoneProduct5.setSeller(seller2);
            TvProduct1.setSeller(seller3);
            TvProduct2.setSeller(seller1);
            TvProduct3.setSeller(seller2);
            TvProduct4.setSeller(seller3);
            TvProduct5.setSeller(seller1);

            Set<ProductVariation> productVariationSet = new HashSet<>();

            ProductVariation PhoneProductVariation1 = new ProductVariation();
            PhoneProductVariation1.setPrice(45000);
            PhoneProductVariation1.setQuantityAvailable(5);
            PhoneProductVariation1.setProduct(PhoneProduct1);
            PhoneProductVariation1.setActive(true);
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("CAMERA","32px");
            PhoneProductVariation1.setMetadataAttributes(attributes);
            String  info = objectMapper.writeValueAsString(attributes);
            PhoneProductVariation1.setMetadata(info);

            ProductVariation PhoneProductVariation2 = new ProductVariation();
            PhoneProductVariation2.setPrice(60000);
            PhoneProductVariation2.setQuantityAvailable(8);
            PhoneProductVariation2.setProduct(PhoneProduct1);
            PhoneProductVariation2.setActive(true);
            Map<String,Object> attributes1 = new HashMap<>();
            attributes1.put("CAMERA","16px");
            PhoneProductVariation2.setMetadataAttributes(attributes1);
            String  info1 = objectMapper.writeValueAsString(attributes1);
            PhoneProductVariation2.setMetadata(info1);

            ProductVariation PhoneProductVariation3 = new ProductVariation();
            PhoneProductVariation3.setPrice(90000);
            PhoneProductVariation3.setQuantityAvailable(3);
            PhoneProductVariation3.setProduct(PhoneProduct1);
            PhoneProductVariation3.setActive(true);
            Map<String,Object> attributes2 = new HashMap<>();
            attributes2.put("STORAGE","32GB");
            PhoneProductVariation3.setMetadataAttributes(attributes2);
            String  info2 = objectMapper.writeValueAsString(attributes2);
            PhoneProductVariation3.setMetadata(info2);
            PhoneProduct1.setProductVariations(productVariationSet);

            ProductVariation TVProductVariation1 = new ProductVariation();
            TVProductVariation1.setPrice(32000);
            TVProductVariation1.setQuantityAvailable(15);
            TVProductVariation1.setProduct(TvProduct3);
            TVProductVariation1.setActive(true);
            Map<String,Object> attributes3 = new HashMap<>();
            attributes3.put("INCHES","22inc");
            TVProductVariation1.setMetadataAttributes(attributes3);
            String  info3 = objectMapper.writeValueAsString(attributes3);
            TVProductVariation1.setMetadata(info3);
            TvProduct2.setProductVariations(productVariationSet);

            ProductVariation TVProductVariation2 = new ProductVariation();
            TVProductVariation2.setPrice(50000);
            TVProductVariation2.setQuantityAvailable(12);
            TVProductVariation2.setProduct(TvProduct3);
            TVProductVariation2.setActive(true);
            Map<String,Object> attributes4 = new HashMap<>();
            attributes4.put("INCHES","22inc");
            TVProductVariation2.setMetadataAttributes(attributes3);
            String  info4 = objectMapper.writeValueAsString(attributes4);
            TVProductVariation2.setMetadata(info4);
            TvProduct3.setProductVariations(productVariationSet);

            ProductVariation TVProductVariation3 = new ProductVariation();
            TVProductVariation3.setPrice(32500);
            TVProductVariation3.setQuantityAvailable(10);
            TVProductVariation3.setProduct(TvProduct1);
            TvProduct1.setProductVariations(productVariationSet);

            productVariationSet.add(PhoneProductVariation1);
            productVariationSet.add(PhoneProductVariation2);
            productVariationSet.add(PhoneProductVariation3);
            productVariationSet.add(TVProductVariation1);
            productVariationSet.add(TVProductVariation2);
            productVariationSet.add(TVProductVariation3);

            userRepository.save(seller1);
            userRepository.save(seller2);
            userRepository.save(seller3);

            CategoryMetadataField categoryMetadataField=new CategoryMetadataField();
            categoryMetadataField.setName("STORAGE");
            categoryMetadataFieldRepository.save(categoryMetadataField);

            CategoryMetadataField categoryMetadataField1=new CategoryMetadataField();
            categoryMetadataField1.setName("INCHES");
            categoryMetadataFieldRepository.save(categoryMetadataField1);

            CategoryMetadataField categoryMetadataField2=new CategoryMetadataField();
            categoryMetadataField2.setName("CAMERA");
            categoryMetadataFieldRepository.save(categoryMetadataField2);

            CategoryMetadataFieldValuesId categoryMetadataFieldValuesId= new CategoryMetadataFieldValuesId();
            categoryMetadataFieldValuesId.setCategory_id(phoneCategory1.getId());
            categoryMetadataFieldValuesId.setCategory_metadata_field_id(categoryMetadataField.getId());

            CategoryMetadataFieldValuesId categoryMetadataFieldValuesId2= new CategoryMetadataFieldValuesId();
            categoryMetadataFieldValuesId2.setCategory_id(phoneCategory1.getId());
            categoryMetadataFieldValuesId2.setCategory_metadata_field_id(categoryMetadataField2.getId());

            CategoryMetadataFieldValuesId categoryMetadataFieldValuesId1= new CategoryMetadataFieldValuesId();
            categoryMetadataFieldValuesId1.setCategory_id(TVCategory1.getId());
            categoryMetadataFieldValuesId1.setCategory_metadata_field_id(categoryMetadataField1.getId());

            CategoryMetadataFieldValue categoryMetadataFieldValues= new CategoryMetadataFieldValue();
            categoryMetadataFieldValues.setValue("16GB,32GB,64GB");
            categoryMetadataFieldValues.setId(categoryMetadataFieldValuesId);
            categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField);
            categoryMetadataFieldValues.setCategory(phoneCategory1);

            CategoryMetadataFieldValue categoryMetadataFieldValues3= new CategoryMetadataFieldValue();
            categoryMetadataFieldValues3.setValue("12px ,16px");
            categoryMetadataFieldValues3.setId(categoryMetadataFieldValuesId2);
            categoryMetadataFieldValues3.setCategoryMetadataField(categoryMetadataField2);
            categoryMetadataFieldValues3.setCategory(phoneCategory1);

            CategoryMetadataFieldValue categoryMetadataFieldValues2= new CategoryMetadataFieldValue();
            categoryMetadataFieldValues2.setValue("22INC,32INC,42INC");
            categoryMetadataFieldValues2.setId(categoryMetadataFieldValuesId1);
            categoryMetadataFieldValues2.setCategoryMetadataField(categoryMetadataField1);
            categoryMetadataFieldValues2.setCategory(TVCategory2);

            Set<CategoryMetadataFieldValue> categoryMetadataFieldValues1 = new HashSet<>();
            categoryMetadataFieldValues1.add(categoryMetadataFieldValues);
            categoryMetadataFieldValues1.add(categoryMetadataFieldValues3);
            phoneCategory1.setCategoryMetadataFieldValueSet(categoryMetadataFieldValues1);

            Set<CategoryMetadataFieldValue> categoryMetadataFieldValues4 = new HashSet<>();
            categoryMetadataFieldValues4.add(categoryMetadataFieldValues2);
            TVCategory2.setCategoryMetadataFieldValueSet(categoryMetadataFieldValues4);

            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues2);
            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues3);

    }
}
