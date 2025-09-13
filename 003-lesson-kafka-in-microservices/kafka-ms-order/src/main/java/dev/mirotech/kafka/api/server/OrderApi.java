package dev.mirotech.kafka.api.server;

import dev.mirotech.kafka.api.request.OrderRequest;
import dev.mirotech.kafka.api.response.OrderResponse;
import dev.mirotech.kafka.command.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        var orderNumber = orderService.saveOrder(request);

        var orderResponse = new OrderResponse(orderNumber);

        return ResponseEntity.ok(orderResponse);
    }
}
