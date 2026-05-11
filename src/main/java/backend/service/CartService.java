package backend.service;

import backend.dto.CartItemRequest;
import backend.entity.*;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Cart getCart(String email) {
        User user = getUserByEmail(email);
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart addToCart(String email, CartItemRequest request) {
        User user = getUserByEmail(email);
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        FoodItem foodItem = foodItemRepository.findById(request.getFoodItemId())
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        // Check if item already exists in cart
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            if (item.getFoodItem().getId().equals(foodItem.getId())) {
                item.setQuantity(item.getQuantity() + request.getQuantity());
                cartItemRepository.save(item);
                return cart;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setFoodItem(foodItem);
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return cart;
    }

    public Cart updateCartItem(String email, Long cartItemId, Integer quantity) {
        Cart cart = getCart(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return cart;
    }

    public Cart removeFromCart(String email, Long cartItemId) {
        Cart cart = getCart(email);
        cartItemRepository.deleteById(cartItemId);
        return cart;
    }

    public void clearCart(Cart cart) {
        cartItemRepository.deleteAll(cart.getCartItems());
    }
}