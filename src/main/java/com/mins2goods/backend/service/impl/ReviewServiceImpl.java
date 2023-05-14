package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.model.Review;
import com.mins2goods.backend.repository.ReviewRepository;
import com.mins2goods.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_ProductId(productId);
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByBuyer_UserId(userId);
    }

    @Override
    public List<Review> getReviewsByProductIdAndRatingGreaterThanEqual(Long productId, int minRating) {
        return reviewRepository.findByProduct_ProductIdAndRatingGreaterThanEqual(productId, minRating);
    }

    @Override
    public Optional<Double> findAverageRatingByProductId(Long productId) {
        return reviewRepository.findAverageRatingByProductId(productId);
    }
    @Override
    public Long countReviewsByProductId(Long productId) {
        return reviewRepository.findTotalReviewsByProductId(productId);
    }
    @Override
    public Optional<Double> findAverageRatingBySellerId(Long sellerId) {
        return reviewRepository.findAverageRatingBySellerId(sellerId);
    }

    @Override
    public long countRatingsBySellerId(Long sellerId) {
        return reviewRepository.countRatingsBySellerId(sellerId);
    }
}
