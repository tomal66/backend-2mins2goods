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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewResource {
    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = convertToEntity(reviewDto);
        Review savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId)
                .map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        // Set the Product and Buyer entities using their IDs
        // You might need to add methods in the ProductService and UserService to fetch the entities by their IDs
        review.setProduct(productService.getProductById(reviewDto.getProductId()));
        User buyer = userService.getUser(reviewDto.getUsername());
        review.setBuyer(buyer);
        return review;
    }
}
