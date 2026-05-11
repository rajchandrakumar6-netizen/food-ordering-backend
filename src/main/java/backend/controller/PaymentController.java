package backend.controller;

import backend.dto.PaymentRequest;
import backend.entity.Payment;
import backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));
    }
}