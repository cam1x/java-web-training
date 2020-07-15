package by.training.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private long userId;
}
