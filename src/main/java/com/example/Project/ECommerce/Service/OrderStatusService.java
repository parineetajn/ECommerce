package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.OrderStatus;
import com.example.Project.ECommerce.Enums.Order_Status;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OrderStatusService {
    OrderStatus order_status = new OrderStatus();

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void checkOrderStatus(OrderStatus order_status) {
        if (order_status.getFromStatus().equals(Order_Status.ORDER_PLACED)){
            order_status.setToStatus(Arrays.asList(Order_Status.ORDER_CONFIRMED, Order_Status.CANCELLED, Order_Status.ORDER_REJECTED));
        } else if (order_status.getFromStatus().equals(Order_Status.CANCELLED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.REFUND_INITIATED, Order_Status.CLOSED));
        } else if (order_status.getFromStatus().equals(Order_Status.ORDER_REJECTED)){
            order_status.setToStatus(Arrays.asList(Order_Status.REFUND_INITIATED, Order_Status.CLOSED));
        } else if (order_status.getFromStatus().equals(Order_Status.ORDER_CONFIRMED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.CANCELLED , Order_Status.ORDER_SHIPPED));
        } else if (order_status.getFromStatus().equals(Order_Status.DELIVERED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.RETURN_REQUESTED, Order_Status.CLOSED));
        } else if (order_status.getFromStatus().equals(Order_Status.ORDER_SHIPPED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.DELIVERED));
        } else if (order_status.getFromStatus().equals(Order_Status.RETURN_REQUESTED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.RETURN_REJECTED , Order_Status.RETURN_APPROVED));
        } else if (order_status.getFromStatus().equals(Order_Status.RETURN_REJECTED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.CLOSED));
        } else if (order_status.getFromStatus().equals(Order_Status.RETURN_APPROVED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.PICK_UP_INITIATED));
        } else if (order_status.getFromStatus().equals(Order_Status.PICK_UP_INITIATED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.PICK_UP_COMPLETED));
        } else if (order_status.getFromStatus().equals(Order_Status.PICK_UP_COMPLETED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.REFUND_INITIATED));
        } else if (order_status.getFromStatus().equals(Order_Status.REFUND_INITIATED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.REFUND_COMPLETED));
        } else if (order_status.getFromStatus().equals(Order_Status.REFUND_COMPLETED)) {
            order_status.setToStatus(Arrays.asList(Order_Status.CLOSED));
        }

    }
}
