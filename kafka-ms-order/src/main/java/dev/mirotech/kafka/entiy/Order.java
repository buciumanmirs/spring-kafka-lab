package dev.mirotech.kafka.entiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column
    private String orderNumber;
    @Column
    private String orderLocation;
    @Column
    private OffsetDateTime orderDateTime;
    @Column
    private String creditCardNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}
