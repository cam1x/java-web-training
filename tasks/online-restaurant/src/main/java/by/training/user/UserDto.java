package by.training.user;

import by.training.contact.ContactDto;
import by.training.order.OrderDto;
import by.training.role.RoleDto;
import by.training.util.ImageManager;
import by.training.wallet.WalletDto;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 8585683306880745643L;

    private long userId;
    private String login;
    private String password;
    private boolean locked;
    private byte[] avatar;

    private long discountId;

    private List<RoleDto> roles;
    private List<ContactDto> contacts;
    private List<OrderDto> orders;
    private List<WalletDto> wallets;

    public String getBase64Encoded() throws IOException {
        return ImageManager.getBase64Encoded(avatar);
    }
}