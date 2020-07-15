package by.training.wish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDto implements Serializable {
    private static final long serialVersionUID = -1434055242242504902L;

    private long id;
    private String wish;
    private long foodOrderId;
}
