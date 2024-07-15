package com.example.shop_server.modules.user.controller;

import com.example.shop_server.modules.jwt.JwtService;
import com.example.shop_server.modules.mail.MailService;
import com.example.shop_server.modules.mail.Option;
import com.example.shop_server.modules.user.UserModel;
import com.example.shop_server.modules.user.dto.req.LoginDTO;
import com.example.shop_server.modules.user.dto.req.RegisterDTO;
import com.example.shop_server.modules.user.dto.req.UpdateDTO;
import com.example.shop_server.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO data) {
        System.out.println("register" + data);
        try {
            UserModel user = new UserModel();
            user.setUserName(data.getUserName());
            user.setPassword(BCrypt.hashpw(data.getPassword(), BCrypt.gensalt()));
            user.setEmail(data.getEmail());
            user.setPhone(data.getPhone());
            user.setCreateAt(String.valueOf(System.currentTimeMillis()));
            user.setUpdateAt(String.valueOf(System.currentTimeMillis()));
            userService.save(user);

            ArrayList<String> mails = new ArrayList<>();
            mails.add(data.getEmail());
            mailService.sendMail(new Option("Chào mừng", "Cảm ơn bạn đã đăng ký tài khoản", mails));

            return new ResponseEntity<>("Đăng ký thành công", HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate")) {
                return new ResponseEntity<>("Email or user name already exists.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Lỗi chưa xác định", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO data) {
        try {
            UserModel user = userService.findUserByInfor(data.getLoginId());

            if (user == null) {
                throw new Exception("Tài khoản không tồn tại");
            }
            if (!BCrypt.checkpw(data.getPassword(), user.getPassword())) {
                throw new Exception("Sai mật khẩu");
            }

            if (!user.isStatus()) {
                throw new Exception("Tài khoản da bi khoa");
            }


            String token = JwtService.createTokenUser(user);

            JedisPool jedisPool = new JedisPool("localhost", 6379);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set(String.valueOf(user.getId()), token);
            }
            jedisPool.close();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Đăng nhập thành công");
            response.put("token", token);


            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/authen")
    public ResponseEntity<Object> authen(@RequestAttribute("data") UserModel user) {

        try {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getAllUser(@RequestAttribute("data") UserModel user) {
        try {
            if (!user.getPermission().contains("user")) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestAttribute("data") UserModel user, @RequestBody UpdateDTO updateData) {
        System.out.println("updateData" + updateData);
        try {
            if (!user.getPermission().contains("user.u")) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            Optional<UserModel> userToUpdate = userService.findUserById(updateData.getId());
            if (!userToUpdate.isPresent()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            System.out.println("userToUpdate.get()" + userToUpdate.get());

            // Update fields from UpdateDTO to UserModel
            if (updateData.getUserName() != null) userToUpdate.get().setUserName(updateData.getUserName());
            if (updateData.getEmail() != null) userToUpdate.get().setEmail(updateData.getEmail());
            if (updateData.getPhone() != null) userToUpdate.get().setPhone(updateData.getPhone());
            if (updateData.getRole() != null) userToUpdate.get().setRole(updateData.getRole());
            if (updateData.getPermission() != null) userToUpdate.get().setPermission(updateData.getPermission());
            if (updateData.getAvatarUrl() != null) userToUpdate.get().setAvatarUrl(updateData.getAvatarUrl());
            userToUpdate.get().setStatus(updateData.isStatus());
            userToUpdate.get().setDeleted(updateData.isDeleted());
            userToUpdate.get().setVerified(updateData.isVerified());
            if (updateData.getCreateAt() != null) userToUpdate.get().setCreateAt(updateData.getCreateAt());
            if (updateData.getUpdateAt() != null) userToUpdate.get().setUpdateAt(updateData.getUpdateAt());

            UserModel updatedUser = userService.update(userToUpdate.get());

            System.out.println("userToUpdate.get().getId()" + userToUpdate.get().getId());

            JedisPool jedisPool = new JedisPool("localhost", 6379);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set(String.valueOf(userToUpdate.get().getId()), "");
            }
            jedisPool.close();

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

  @PutMapping("/changePassword")
public ResponseEntity<Object> changePassword(@RequestAttribute("data") UserModel user, @RequestBody UpdateDTO updateData) {
    try {
        if (!user.getPermission().contains("user.u")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Optional<UserModel> userToUpdate = userService.findUserById(updateData.getId());
        if (!userToUpdate.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!userService.verifyCurrentPassword(updateData.getPassword(), userToUpdate.get().getPassword())) {
            return new ResponseEntity<>("Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST);
        }

        userToUpdate.get().setPassword(BCrypt.hashpw(updateData.getNewPassword(), BCrypt.gensalt()));
        userToUpdate.get().setUpdateAt(String.valueOf(System.currentTimeMillis()));

        UserModel updatedUser = userService.update(userToUpdate.get());

        JedisPool jedisPool = new JedisPool("localhost", 6379);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(String.valueOf(userToUpdate.get().getId()), "");
        }
        jedisPool.close();

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}

//@GetMapping("/search")
//public ResponseEntity<Object> searchUser(@RequestAttribute("data") UserModel user, @RequestParam String userName) {
//    try {
//        if (!user.getPermission().contains("user.r")) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(userService.findByNameIsContainingIgnoreCase(userName), HttpStatus.OK);
//    } catch (Exception e) {
//        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
//    }
//}

//@GetMapping("/pagination")
//public ResponseEntity<Object> getUserByPagination(@RequestAttribute("data") UserModel user, @RequestParam int page, @RequestParam int size) {
//    try {
//        if (!user.getPermission().contains("user.r")) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
//        Pageable pageable = PageRequest.of(page, size);
//        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
//    } catch (Exception e) {
//        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
//    }
//}


    @PostMapping("/user/update")
    public ResponseEntity<?> updateUserWithoutPassword(@RequestBody UserModel user) {
        UserModel currentUser = userService.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Copy properties from the user object to the currentUser object, excluding the password property
        BeanUtils.copyProperties(user, currentUser, "password");

        return ResponseEntity.ok(userService.save(currentUser));
    }
    //thay doi password
    @PutMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> data) {
        UserModel currentUser = userService.findById(Integer.parseInt(data.get("id"))).orElseThrow(() -> new RuntimeException("User not found"));

        if (!BCrypt.checkpw(data.get("oldPassword"), currentUser.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng");
        }

        currentUser.setPassword(BCrypt.hashpw(data.get("newPassword"), BCrypt.gensalt()));

        return ResponseEntity.ok(userService.save(currentUser));
    }
    //lấy mật khẩu ra để thay đổi
    @GetMapping("/user/get-password")
    public ResponseEntity<?> getPassword(@RequestParam int id) {
        return ResponseEntity.ok(userService.findPasswordById(id));
    }



}
