package com.mins2goods.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    private String address;

    private String country;

    private String zipcode;

    private String city;

    private String longitude;

    private String latitude;

    private String state;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
