package com.mins2goods.backend.service;

import com.mins2goods.backend.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review saveReview(Review review);

    Optional<Review> getReviewById(Long reviewId);

    List<Review> getAllReviews();

    void deleteReview(Long reviewId);

    List<Review> getReviewsByProductId(Long productId);
    Long countReviewsByProductId(Long productId);

    List<Review> getReviewsByUserId(Long userId);

    List<Review> getReviewsByProductIdAndRatingGreaterThanEqual(Long productId, int minRating);

    Optional<Double> findAverageRatingByProductId(Long productId);

    Optional<Double> findAverageRatingBySellerId(Long sellerId);

    long countRatingsBySellerId(Long sellerId);
}
