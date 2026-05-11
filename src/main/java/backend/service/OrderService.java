package backend.service;

import backend.dto.OrderRequest;
import backend.entity.*;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Order placeOrder(String email, OrderRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PLACED);
        order.setTotalAmount(request.getTotalAmount());

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (var itemRequest : request.getItems()) {
            FoodItem foodItem = foodItemRepository.findById(itemRequest.getFoodItemId())
                    .orElseThrow(() -> new RuntimeException("Food item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setFoodItem(foodItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(itemRequest.getPrice());
            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);

        // Clear cart after order
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartService.clearCart(cart);

        // Fetch fresh order with items
        return orderRepository.findById(savedOrder.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getUserOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long id, Order.Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}