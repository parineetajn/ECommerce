package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OrderStatusService {
    OrderStatus orderStatus = new OrderStatus();

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void checkOrderStatus(OrderStatus orderStatus) {
        if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.ORDER_PLACED)){
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.ORDER_CONFIRMED, com.example.Project.ECommerce.Enums.orderStatus.CANCELLED, com.example.Project.ECommerce.Enums.orderStatus.ORDER_REJECTED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.CANCELLED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.REFUND_INITIATED, com.example.Project.ECommerce.Enums.orderStatus.CLOSED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.ORDER_REJECTED)){
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.REFUND_INITIATED, com.example.Project.ECommerce.Enums.orderStatus.CLOSED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.ORDER_CONFIRMED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.CANCELLED , com.example.Project.ECommerce.Enums.orderStatus.ORDER_SHIPPED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.DELIVERED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.RETURN_REQUESTED, com.example.Project.ECommerce.Enums.orderStatus.CLOSED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.ORDER_SHIPPED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.DELIVERED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.RETURN_REQUESTED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.RETURN_REJECTED , com.example.Project.ECommerce.Enums.orderStatus.RETURN_APPROVED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.RETURN_REJECTED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.CLOSED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.RETURN_APPROVED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.PICK_UP_INITIATED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.PICK_UP_INITIATED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.PICK_UP_COMPLETED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.PICK_UP_COMPLETED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.REFUND_INITIATED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.REFUND_INITIATED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.REFUND_COMPLETED));
        } else if (orderStatus.getFromStatus().equals(com.example.Project.ECommerce.Enums.orderStatus.REFUND_COMPLETED)) {
            orderStatus.setToStatus(Arrays.asList(com.example.Project.ECommerce.Enums.orderStatus.CLOSED));
        }

    }
}
