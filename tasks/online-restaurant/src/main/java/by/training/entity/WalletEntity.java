package by.training.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {
    private long id;
    private String name;
    private double amount;
    private long userId;
}