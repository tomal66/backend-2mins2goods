package com.mins2goods.backend.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String mobile;
    private String email;
    private String role;
    private boolean isActive;
    private AddressDto address;
}
