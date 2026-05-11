package backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;
    private Double totalAmount;
}