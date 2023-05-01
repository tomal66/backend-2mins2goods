package com.mins2goods.backend.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String title;
    private String description;
    private String price;
    private Integer quantity;
    private Double latitude;
    private Double longitude;
    private String sellerUsername;
    private String category;
}
