package com.mins2goods.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String address;

    private String country;

    private String zipcode;

    private String city;

    private Double longitude;

    private Double latitude;

    private String state;
}
