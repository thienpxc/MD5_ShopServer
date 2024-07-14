package com.example.shop_server.modules.user.service;

import com.example.shop_server.modules.user.UserModel;
import com.example.shop_server.modules.user.reqository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserModel save(UserModel user){
        return userRepository.save(user);
    }

    public UserModel findUserByInfor(String loginId){
        return userRepository.findUserByInfor(loginId);
    }

    public List<UserModel> findUsers(){
        return userRepository.findAll();
    }

    public Optional<UserModel> findUserById(int id){
        return userRepository.findById(id);
    }

    public UserModel update(UserModel data){
        return userRepository.save(data);
    }

    public Optional<UserModel> findById(Integer id)
            {
        return userRepository.findById(id);
    }
}
