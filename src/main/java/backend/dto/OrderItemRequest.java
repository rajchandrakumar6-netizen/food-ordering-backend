package backend.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long foodItemId;
    private Integer quantity;
    private Double price;
}