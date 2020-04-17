package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.*;
import com.example.Project.ECommerce.Enums.Order_Status;
import com.example.Project.ECommerce.Exceptions.InputException;
import com.example.Project.ECommerce.Exceptions.UserNotAuthorizedException;
import com.example.Project.ECommerce.Exceptions.UserNotFoundException;
import com.example.Project.ECommerce.Repository.*;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationService productVariationService;

    public void placeOrder(long productVariation_id, int quantity,long address_id) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        productVariationService.checkAndUpdateQuantity(productVariation_id, quantity);

        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariation_id);
        ProductVariation productVariation = productVariationOptional.get();
        if (!productVariation.isActive()) {
            throw new UserNotFoundException("Item is not available");
        }
        Optional<Address> addressOptional = addressRepository.findById(address_id);
        Address address = null;
        if (addressOptional.isPresent()) {
            address = addressOptional.get();
        } else {
            throw new InputException("No Address added! ");
        }

        Orders orders = new Orders();
        orders.setAmountPaid((productVariationRepository.getPrice(productVariation_id)) * quantity);
        orders.setCustomer(customer);
        orders.setPaymentMethod("COD");
        orders.setCustomerAddressCity(address.getCity());
        orders.setCustomerAddressCountry(address.getCountry());
        orders.setCustomerAddressLabel(address.getLabel());
        orders.setCustomerAddressState(address.getState());
        orders.setCustomerAddressZipCode(address.getZipCode());

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPrice((productVariationRepository.getPrice(productVariation_id)) * quantity);
        orderProduct.setQuantity(quantity);
        orderProduct.setOrder(orders);
        orderProduct.setProduct_variation_metadata(productVariation.getMetadata());
        orderProduct.setProductVariation(productVariation);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setFrom_Status(Order_Status.ORDER_PLACED);
        orderStatus.setOrder_product(orderProduct);

        orderRepository.save(orders);
        productVariationRepository.save(productVariation);
        orderProductRepository.save(orderProduct);
        addressRepository.save(address);
        orderStatusRepository.save(orderStatus);
    }

    public void changeOrderStatus(OrderStatus orderStatus, long productVariation_id, long orderStatus_id) {
        String username = getCurrentLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);
        Long productId = productVariationRepository.getProductId(productVariation_id);
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product1 = productOptional.get();
        if ((product1.getSeller().getUsername()).equals(seller.getUsername())) {
            Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatus_id);
            OrderStatus orderStatus1 = orderStatusOptional.get();
            orderStatus1.setFrom_Status(orderStatus.getFrom_Status());
            orderStatus1.setTo_Status(orderStatus.getTo_Status());
            orderStatus1.setTransition_notes_comments(orderStatus.getTransition_notes_comments());
            orderStatusRepository.save(orderStatus1);
        } else {
            throw new UserNotAuthorizedException("you can't change the to status of this product");
        }
    }

    public List<Object[]> viewOrders(){
        String username=getCurrentLoggedInUser.getCurrentUser();
        return orderRepository.viewOrders(username);
    }
}

