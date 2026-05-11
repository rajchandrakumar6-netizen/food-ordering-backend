package backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodItemRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    private String imageUrl;

    @NotNull(message = "Category is required")
    private Long categoryId;
}