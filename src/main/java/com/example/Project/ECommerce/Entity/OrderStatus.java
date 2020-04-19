package com.example.Project.ECommerce.Entity;

import com.example.Project.ECommerce.Enums.Order_Status;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    @JoinColumn(name = "Order_Product_id")
    private OrderProduct order_product;
    @Enumerated(EnumType.STRING)
    private Order_Status fromStatus;
    @ElementCollection
    @CollectionTable(name="Product_ToStatus")
    @Column(name="To_Status")
    @Enumerated(EnumType.STRING)
    private List<Order_Status> toStatus;
    private String transitionNotesComments;

    @Column(name = "createdDate")
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "modifiedDate")
    @LastModifiedDate
    private LocalDateTime modifiedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;


    public OrderStatus() {
    }

    public OrderStatus(OrderProduct order_product, Order_Status fromStatus, List<Order_Status> toStatus, String transitionNotesComments) {
        this.order_product = order_product;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.transitionNotesComments = transitionNotesComments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderProduct getOrder_product() {
        return order_product;
    }

    public void setOrder_product(OrderProduct order_product) {
        this.order_product = order_product;
    }

    public Order_Status getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Order_Status fromStatus) {
        this.fromStatus = fromStatus;
    }


    public List<Order_Status> getToStatus() {
        return toStatus;
    }

    public void setToStatus(List<Order_Status> toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "Order_Status{" +
                "id=" + id +
                ", From_Status=" + fromStatus +
                ", To_Status=" + toStatus +
                ", transition_notes_comments='" + transitionNotesComments + '\'' +
                '}';
    }
}
