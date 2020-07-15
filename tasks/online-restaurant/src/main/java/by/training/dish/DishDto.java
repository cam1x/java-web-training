package by.training.dish;

import by.training.util.ImageManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDto implements Serializable {
    private static final long serialVersionUID = 3975655402650288659L;

    private long id;
    private long quantity = 1;
    private String name;
    private double price;
    private String description;
    private byte[] photo;

    public String getBase64Encoded() throws IOException {
        return ImageManager.getBase64Encoded(photo);
    }
}
