package by.training.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountEntity {
    private long id;
    private double amount;
}