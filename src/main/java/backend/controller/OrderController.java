package backend.controller;

import backend.dto.OrderRequest;
import backend.entity.Order;
import backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<Order> placeOrder(Authentication authentication,
                                             @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.placeOrder(authentication.getName(), request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getUserOrders(authentication.getName()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                    @RequestParam Order.Status status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}