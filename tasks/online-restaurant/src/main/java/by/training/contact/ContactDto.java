package by.training.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto implements Serializable {
    private static final long serialVersionUID = -3399010921223236763L;
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private long userId;
}
