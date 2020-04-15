package com.example.Project.ECommerce.Service;

import com.example.Project.ECommerce.Entity.Order_Status;
import com.example.Project.ECommerce.Enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OrderStatusService {
    Order_Status order_status = new Order_Status();

    public Order_Status getOrder_status() {
        return order_status;
    }

    public void checkOrderStatus(Order_Status order_status) {
        if (order_status.getFrom_Status().equals(OrderStatus.ORDER_PLACED)){
            order_status.setTo_Status(Arrays.asList(OrderStatus.ORDER_CONFIRMED,OrderStatus.CANCELLED, OrderStatus.ORDER_REJECTED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.CANCELLED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.REFUND_INITIATED,OrderStatus.CLOSED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.ORDER_REJECTED)){
            order_status.setTo_Status(Arrays.asList(OrderStatus.REFUND_INITIATED,OrderStatus.CLOSED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.ORDER_CONFIRMED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.CANCELLED ,OrderStatus.ORDER_SHIPPED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.DELIVERED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.RETURN_REQUESTED,OrderStatus.CLOSED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.ORDER_SHIPPED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.DELIVERED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.RETURN_REQUESTED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.RETURN_REJECTED ,OrderStatus.RETURN_APPROVED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.RETURN_REJECTED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.CLOSED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.RETURN_APPROVED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.PICK_UP_INITIATED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.PICK_UP_INITIATED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.PICK_UP_COMPLETED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.PICK_UP_COMPLETED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.REFUND_INITIATED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.REFUND_INITIATED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.REFUND_COMPLETED));
        } else if (order_status.getFrom_Status().equals(OrderStatus.REFUND_COMPLETED)) {
            order_status.setTo_Status(Arrays.asList(OrderStatus.CLOSED));
        }

    }
}
