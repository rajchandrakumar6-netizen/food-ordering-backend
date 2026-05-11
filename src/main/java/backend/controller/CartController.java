package backend.controller;

import backend.dto.CartItemRequest;
import backend.entity.Cart;
import backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<Cart> addToCart(Authentication authentication,
                                           @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(authentication.getName(), request));
    }

    @PutMapping("/update/{cartItemId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<Cart> updateCartItem(Authentication authentication,
                                                @PathVariable Long cartItemId,
                                                @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(authentication.getName(),
                cartItemId, quantity));
    }

    @DeleteMapping("/remove/{cartItemId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<Cart> removeFromCart(Authentication authentication,
                                                @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeFromCart(authentication.getName(), cartItemId));
    }
}