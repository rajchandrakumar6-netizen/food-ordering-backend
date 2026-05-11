package backend.repository;

import backend.entity.FoodItem;
import backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByCategory(Category category);
    List<FoodItem> findByStatus(FoodItem.Status status);
}