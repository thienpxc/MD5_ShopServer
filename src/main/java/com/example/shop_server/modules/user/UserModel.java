package com.example.shop_server.modules.user;

import com.example.shop_server.modules.address.AddressModel;
import com.example.shop_server.modules.enums.RoleUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user")
public class UserModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String userName;

    private String password;

    @Column(unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private RoleUser role = RoleUser.USER;

    private String permission = ""; // ex: "read,write,delete"

    private String avatarUrl = "http://localhost:1234/notimg.png";

    private boolean status = true; // true: ok, false: block

    private boolean isDeleted = false; // true: deleted, false: not deleted

    private boolean isVerified = false; // true: verified, false: not verified

    private String createAt;

    private String updateAt;
    @OneToMany()
    @JoinColumn(name = "userId")
    @JsonManagedReference
    private List<AddressModel> address;
}
