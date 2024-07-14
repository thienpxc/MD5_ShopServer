package com.example.shop_server.modules.user.dto.req;

import com.example.shop_server.validator.UniqueField;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @NotBlank(message = "Khong duoc de trong")

    private String loginId;
    @NotBlank(message = "Khong duoc de trong")
    private String password;
}
