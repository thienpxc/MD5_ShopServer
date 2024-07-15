package com.example.shop_server.modules.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDTO {
    private String title;
    private String detail;
    private String phone;
    private String name;


    private Integer provinceID;
    private String provinceName;
    private Integer districtID;
    private String districtName;
    private String wardCode;
    private String wardName;

    @Override
    public String toString() {
        return "CreateDTO{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", provinceID=" + provinceID +
                ", provinceName='" + provinceName + '\'' +
                ", districtID=" + districtID +
                ", districtName='" + districtName + '\'' +
                ", wardCode='" + wardCode + '\'' +
                ", wardName='" + wardName + '\'' +
                '}';
    }
}