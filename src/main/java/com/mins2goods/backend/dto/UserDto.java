package com.mins2goods.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String mobile;
    private String email;
    private String role;
    private boolean isActive;
    private AddressDto address;
}
