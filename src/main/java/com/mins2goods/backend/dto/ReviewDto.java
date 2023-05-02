package com.mins2goods.backend.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long reviewId;
    private String comment;
    private int rating;
    private Long productId;
    private String username;
}
