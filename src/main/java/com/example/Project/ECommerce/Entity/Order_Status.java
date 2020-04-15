package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Enums.OrderStatus;

import javax.persistence.*;
import java.util.List;

@Entity
public class Order_Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "Order_Product_id")
    private Order_Product order_product;
    @Enumerated(EnumType.STRING)
    private OrderStatus From_Status;
    @ElementCollection
    @CollectionTable(name="Product_ToStatus")
    @Column(name="To_Status")
    @Enumerated(EnumType.STRING)
    private List<OrderStatus> To_Status;
    private String transition_notes_comments;

    public Order_Status() {
    }

    public Order_Status(Order_Product order_product, OrderStatus from_Status, List<OrderStatus> to_Status, String transition_notes_comments) {
        this.order_product = order_product;
        From_Status = from_Status;
        To_Status = to_Status;
        this.transition_notes_comments = transition_notes_comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order_Product getOrder_product() {
        return order_product;
    }

    public void setOrder_product(Order_Product order_product) {
        this.order_product = order_product;
    }

    public OrderStatus getFrom_Status() {
        return From_Status;
    }

    public void setFrom_Status(OrderStatus from_Status) {
        From_Status = from_Status;
    }


    public List<OrderStatus> getTo_Status() {
        return To_Status;
    }

    public void setTo_Status(List<OrderStatus> to_Status) {
        To_Status = to_Status;
    }

    public String getTransition_notes_comments() {
        return transition_notes_comments;
    }

    public void setTransition_notes_comments(String transition_notes_comments) {
        this.transition_notes_comments = transition_notes_comments;
    }

    @Override
    public String toString() {
        return "Order_Status{" +
                "id=" + id +
                ", From_Status=" + From_Status +
                ", To_Status=" + To_Status +
                ", transition_notes_comments='" + transition_notes_comments + '\'' +
                '}';
    }
}
