package by.training.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {
    private static final long serialVersionUID = -4748447832015533858L;

    private long id;
    private String role;
    private boolean defaultRole;
}