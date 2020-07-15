package by.training.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto implements Serializable {
    private static final long serialVersionUID = -5275056952799795865L;

    private long id;
    private double amount;
    private String name;
    private long userId;
}
