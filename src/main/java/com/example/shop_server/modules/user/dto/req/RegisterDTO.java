package com.example.shop_server.modules.user.dto.req;

import com.example.shop_server.validator.UniqueField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {
    @NotBlank(message = "Khong duoc de trong")
    @Size(min = 4, max = 20, message = "Do dai tu 6 den 20 ky tu")
    @UniqueField(fieldName = "userName", message = "Tài khoản đã tồn tại")

    String userName;
    @NotBlank(message = "Khong duoc de trong")
    @Size(min = 4, max = 20, message = "Do dai tu 6 den 20 ky tu")
    String password;
    @NotBlank(message = "Khong duoc de trong")
    @Email(message = "Email khong hop le")
    @UniqueField(fieldName = "email", message = "Email đã tồn tại")
    String email;
    @NotBlank(message = "Khong duoc de trong")
    @Size(min = 10, max = 10, message = "Do dai 10 ky tu")
    @UniqueField(fieldName = "phone", message = "Số điện thoại đã tồn tại")
    String phone;


    @Override
    public String toString() {
        return "RegisterDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +

                '}';
    }
}
