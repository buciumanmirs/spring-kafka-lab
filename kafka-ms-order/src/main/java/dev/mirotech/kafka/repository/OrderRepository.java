package dev.mirotech.kafka.repository;

import dev.mirotech.kafka.entiy.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
