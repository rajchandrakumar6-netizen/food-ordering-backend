package backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {

    @NotNull(message = "Food item is required")
    private Long foodItemId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}