package backend.service;

import backend.dto.PaymentRequest;
import backend.entity.Order;
import backend.entity.Payment;
import backend.repository.OrderRepository;
import backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Payment processPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(request.getAmount());
        payment.setStatus(Payment.Status.COMPLETED);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}