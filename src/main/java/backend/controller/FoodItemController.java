package backend.controller;

import backend.dto.FoodItemRequest;
import backend.entity.FoodItem;
import backend.service.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodItem> createFoodItem(@Valid @RequestBody FoodItemRequest request) {
        return ResponseEntity.ok(foodItemService.createFoodItem(request));
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.getFoodItemById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(foodItemService.getFoodItemsByCategory(categoryId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id,
                                                    @Valid @RequestBody FoodItemRequest request) {
        return ResponseEntity.ok(foodItemService.updateFoodItem(id, request));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodItem> updateStatus(@PathVariable Long id,
                                                  @RequestParam FoodItem.Status status) {
        return ResponseEntity.ok(foodItemService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.ok("Food item deleted successfully");
    }
}