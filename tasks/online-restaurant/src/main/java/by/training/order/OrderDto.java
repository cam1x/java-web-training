package by.training.order;

import by.training.dish.DishDto;
import by.training.order.state.OrderState;
import by.training.order.state.ProcessingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 3756777466962116716L;

    private long id;
    private Date orderDate;
    private Date bookingDate;
    private long customerId;
    private String status;
    private double totalPrice;

    private List<DishDto> orderDishes;
    private OrderState state = new ProcessingState();
}
