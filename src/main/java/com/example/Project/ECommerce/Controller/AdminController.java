package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.CategoryMetadataFieldRepository;
import com.example.Project.ECommerce.Repository.CategoryRepository;
import com.example.Project.ECommerce.Service.ProductService;
import com.example.Project.ECommerce.Service.TokenService;
import com.example.Project.ECommerce.Service.UserService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import com.example.Project.ECommerce.Repository.ProductRepository;
import com.example.Project.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Admin home";
    }

    @PostMapping("/admin/verify")
    public String verifyToken(@RequestParam(name = "token") String token) {
        tokenService.verifyToken(token);
        return "token verified..";
    }

    @GetMapping("/admin/getAllRegisteredCustomers")
    public List<Object[]> getRegisteredCustomer() {
        List<Object[]> registeredCustomers = userRepository.findAllRegisteredCustomers();
        for (Object[] objects : registeredCustomers) {
            System.out.println("UserName: " + objects[0]);
            System.out.println("Email: " + objects[1]);
        }
        return registeredCustomers;
    }

    @GetMapping("/admin/getAllRegisteredSellers")
    public List<Object[]> getRegisteredSeller() {
        List<Object[]> registeredSellers = userRepository.findAllRegisteredSellers();
        for (Object[] objects : registeredSellers) {
            System.out.println("UserName: " + objects[0]);
            System.out.println("Email: " + objects[1]);
        }
        return registeredSellers;
    }

    @PutMapping("/admin/changeRole")
    public void changeRole(@RequestParam(name = "role") Role role) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        User user1 = userRepository.findByUsername(username);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user1.setRoles(roleSet);
        userRepository.save(user1);
    }

    @PutMapping("/admin/ActivateUserStatus/{id}")
    public String activateStatus(@PathVariable(name = "id") Long id) {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user1 = user.get();
            if (user1.isEnable()) {
                return "User account is already activated!";
            } else {
                userService.activateStatus(user1);
                return "Status of User activated with id: " + id;
            }
        } else throw new UserNotFoundException(" User Not Found... with id : " + id);
    }

    @PutMapping("/admin/deActivateUserStatus/{id}")
    public String deActivateStatus(@PathVariable(name = "id") Long id) {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user1 = user.get();
            if (!user1.isEnable()) {
                return "User account is already DeActivated!";
            } else {
                userService.deActivateStatus(user1);
                return "Status of User Deactivated with id: " + id;
            }
        } else throw new UserNotFoundException(" User Not Found...with id : " + id);
    }

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
    public List<Object[]> getListOfProducts() {
        List<Object[]> products = productRepository.findAdminProductList();
        return products;
    }

   /* @PostMapping("/addCategory")
    public String addCategory(@RequestParam(value = "parent_id", required = false) Integer parent_id,
                              @RequestParam(value = "CategoryName") String CategoryName) {
        Category category = new Category();
        Category category1 = new Category();
        category.setCategoryName(CategoryName);
        if (parent_id != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(parent_id);
            category1 = optionalCategory.get();
            category.setCategory(category1);
        } else {
            category.setCategory(null);
        }
        categoryRepository.save(category);
        return "Category added! with id: " + category.getId();
    }
*/
   @PostMapping("/admin/addCategoryMetadataField")
   public String addCategoryMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField) {
       categoryMetadataFieldRepository.save(categoryMetadataField);
       return "CategoryMetadataField is successfully created";
   }
}
