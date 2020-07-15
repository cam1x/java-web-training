package by.training.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto implements Serializable {
    private static final long serialVersionUID = 4281772377914759611L;

    private long id;
    private double amount;
}
