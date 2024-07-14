package com.example.shop_server.modules.user.reqository;

import com.example.shop_server.modules.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query(value = "SELECT * FROM user WHERE user.userName = :loginId or (user.email = :loginId and user.isDeleted = true)", nativeQuery = true)
    UserModel findUserByInfor(@Param("loginId") String loginId);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);





}
