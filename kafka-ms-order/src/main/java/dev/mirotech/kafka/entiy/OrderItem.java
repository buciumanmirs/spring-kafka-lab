package dev.mirotech.kafka.entiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    private int orderItemId;
    @Column(nullable = false)
    private String itemName;
    @Column
    private int price;
    @Column
    private int quantity;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;
}
