package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.ReviewDto;
import com.mins2goods.backend.model.Review;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.service.ProductService;
import com.mins2goods.backend.service.ReviewService;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewResource {
    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;
    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = convertToEntity(reviewDto);
        Review savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId)
                .map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProductId(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        List<ReviewDto> reviewDtos = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/averageRating/{productId}")
    public ResponseEntity<Optional<Double>> getAverageRating(@PathVariable Long productId) {
        Optional<Double> averageRating = reviewService.findAverageRatingByProductId(productId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/totalReviews/{productId}")
    public ResponseEntity<Long> getTotalReviews(@PathVariable Long productId) {
        Long totalReviews = reviewService.countReviewsByProductId(productId);
        return ResponseEntity.ok(totalReviews);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Review convertToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setReviewId(reviewDto.getReviewId());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setProduct(productService.getProductById(reviewDto.getProductId()));
        User buyer = userService.getUser(reviewDto.getUsername());
        review.setBuyer(buyer);
        return review;
    }

    private ReviewDto convertToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(review.getReviewId());
        reviewDto.setComment(review.getComment());
        reviewDto.setRating(review.getRating());
        reviewDto.setProductId(review.getProduct().getProductId());
        reviewDto.setUsername(review.getBuyer().getUsername());
        return reviewDto;
    }

}
