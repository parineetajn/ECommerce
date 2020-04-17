package com.example.Project.ECommerce.Controller;

import com.example.Project.ECommerce.Entity.OrderStatus;
import com.example.Project.ECommerce.Repository.OrderRepository;
import com.example.Project.ECommerce.Service.OrderService;
import com.example.Project.ECommerce.Utility.GetCurrentLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    GetCurrentLoggedInUser getCurrentLoggedInUser;

    @PostMapping("/customer/placeOrder")
    public String placeOrder(@RequestParam(name = "productVariation_id") long productVariation_id,
                           @RequestParam(name = "quantity") int quantity,
                           @RequestParam(name = "address_id") long address_id){
        orderService.placeOrder(productVariation_id, quantity, address_id);
        return "Order Placed!!";
    }

    @GetMapping("/customer/viewAllOrders")
    public List<Object[]> viewOrders(){
        return orderService.viewOrders();
    }

    @PutMapping("/seller/changeOrderStatus")
    public String changeOrderStatus(@RequestBody OrderStatus orderStatus,
                                  @RequestParam(name = "productVariation_id")long productVariation_id,
                                  @RequestParam(name = "orderStatus_id") long orderStatus_id){
        orderService.changeOrderStatus(orderStatus, productVariation_id, orderStatus_id);
        return "Order status changed!";

    }
}
