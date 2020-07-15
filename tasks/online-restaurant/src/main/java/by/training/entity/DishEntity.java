package by.training.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishEntity {
    private long id;
    private String name;
    private double price;
    private String description;
    private byte[] photo;
}
