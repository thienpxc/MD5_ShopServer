package com.example.shop_server.modules.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressService {
    @Autowired
    private AddressRepositroy addressRepository;

    public AddressModel save(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }

    public List<AddressModel> findByUserId(Integer userId) {
        return addressRepository.findByUserId(userId);
    }
}
