package com.example.shop_server.modules.address;

import com.example.shop_server.modules.address.dto.CreateDTO;
import com.example.shop_server.modules.user.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    private final String ghn_key = "6086c41e-0326-11ed-8636-7617f3863de9";
    @GetMapping("/find-provine")
    public ResponseEntity<Object> findProvine() {
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/master-data/province");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Token", ghn_key);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();

            StringBuilder responseContent = new StringBuilder();
            if (status >= 200 && status <= 299) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonResponse = objectMapper.readValue(responseContent.toString(), Object.class);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("loi gi do", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find-district/{provine_id}")
    public ResponseEntity<Object> findDistrict(@PathVariable("provine_id") int province_id){
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + province_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Token", ghn_key);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();

            StringBuilder responseContent = new StringBuilder();
            if (status >= 200 && status <= 299) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonResponse = objectMapper.readValue(responseContent.toString(), Object.class);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("loi gi do", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find-ward/{district_id}")
    public ResponseEntity<Object> findWard(@PathVariable("district_id") int district_id){
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + district_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Token", ghn_key);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();

            StringBuilder responseContent = new StringBuilder();
            if (status >= 200 && status <= 299) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonResponse = objectMapper.readValue(responseContent.toString(), Object.class);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("loi gi do", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAddress(@RequestBody CreateDTO data, @RequestAttribute("data") UserModel user){

        try {
            System.out.println("data " + data);

            AddressModel addressModel = new AddressModel();

            addressModel.setPhone(data.getPhone());
            addressModel.setDetail(data.getDetail());
            addressModel.setName(data.getName());
            addressModel.setTitle(data.getTitle());

            addressModel.setWardCode(data.getWardCode());
            addressModel.setWardName(data.getWardName());
            addressModel.setProvinceID(data.getProvinceID());
            addressModel.setProvinceName(data.getProvinceName());
            addressModel.setDistrictID(data.getDistrictID());
            addressModel.setDistrictName(data.getDistrictName());

            addressModel.setUserId(user.getId());

            addressModel = addressService.save(addressModel);

            return new ResponseEntity<>(addressModel, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("loi gi do", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-for-user")
    public ResponseEntity<Object> getAllAddress(@RequestAttribute("data") UserModel user){
        try {

            List<AddressModel> addressModels = addressService.findByUserId(user.getId());
            System.out.println("addressModels " + addressModels);
            return new ResponseEntity<>(addressModels, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("loi gi do", HttpStatus.BAD_REQUEST);
        }
    }
}
