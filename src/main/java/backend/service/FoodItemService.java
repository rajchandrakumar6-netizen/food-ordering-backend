package backend.service;

import backend.dto.FoodItemRequest;
import backend.entity.Category;
import backend.entity.FoodItem;
import backend.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private CategoryService categoryService;

    public FoodItem createFoodItem(FoodItemRequest request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());

        FoodItem foodItem = new FoodItem();
        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setCategory(category);
        foodItem.setStatus(FoodItem.Status.AVAILABLE);

        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
    }

    public List<FoodItem> getFoodItemsByCategory(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return foodItemRepository.findByCategory(category);
    }

    public FoodItem updateFoodItem(Long id, FoodItemRequest request) {
        FoodItem foodItem = getFoodItemById(id);
        Category category = categoryService.getCategoryById(request.getCategoryId());

        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setCategory(category);

        return foodItemRepository.save(foodItem);
    }

    public FoodItem updateStatus(Long id, FoodItem.Status status) {
        FoodItem foodItem = getFoodItemById(id);
        foodItem.setStatus(status);
        return foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }
}