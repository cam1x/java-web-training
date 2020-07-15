package by.training.entity;

import lombok.*;

import java.sql.Blob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private long id;
    private String login;
    private String password;
    private boolean locked;
    private long discountId;
    private byte[] avatar;
}