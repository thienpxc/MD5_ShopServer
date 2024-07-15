package com.example.shop_server.modules.user.dto.req;




import com.example.shop_server.modules.enums.RoleUser;
import com.example.shop_server.modules.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDTO {
    private Integer id;

    private String userName;

    private String password;

    private String email;

    private String phone;

    private RoleUser role;

    private String permission;

    private String avatarUrl;

    private boolean status;

    private boolean isDeleted;

    private boolean isVerified;

    private String createAt;

    private String updateAt;

    private String newPassword;

}
