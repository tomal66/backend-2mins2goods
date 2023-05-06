package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_ProductId(Long productId);
    List<Review> findByBuyer_UserId(Long userId);
    List<Review> findByProduct_ProductIdAndRatingGreaterThanEqual(Long productId, int minRating);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
    Optional<Double> findAverageRatingByProductId(@Param("productId") Long productId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.seller.userId = :sellerId")
    Optional<Double> findAverageRatingBySellerId(@Param("sellerId") Long sellerId);
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.seller.userId = :sellerId")
    long countRatingsBySellerId(@Param("sellerId") Long sellerId);

}
