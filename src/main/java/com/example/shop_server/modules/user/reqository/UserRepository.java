package com.example.shop_server.modules.user.reqository;

import com.example.shop_server.modules.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query(value = "SELECT * FROM user WHERE user.userName = :loginId or (user.email = :loginId and user.isDeleted = true)", nativeQuery = true)
    UserModel findUserByInfor(@Param("loginId") String loginId);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    //lấy mật khẩu ra để thay đổi
    @Query(value = "SELECT user.password FROM user WHERE user.id = :id", nativeQuery = true)
    String findPasswordById(@Param("id") int id);

    //thay doi password
    @Query(value = "UPDATE user SET user.password = :password WHERE user.id = :id ", nativeQuery = true)
    void changePassword(@Param("password") String password, @Param("id") int id);

//List<UserModel> findByUserNameContainsIgnoreCase(String name);



}
