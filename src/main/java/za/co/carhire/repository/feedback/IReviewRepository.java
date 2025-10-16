package za.co.carhire.repository.feedback;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 16 October 2025 - Added new query methods for enhanced Review entity
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.authentication.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findById(int reviewID);
    List<Review> findByRating(int rating);
    List<Review> findAll();

    // New query methods for enhanced functionality
    List<Review> findByCar(Car car);
    List<Review> findByCarCarID(int carID);
    List<Review> findByUser(User user);
    List<Review> findByUserUserId(Integer userId);
    List<Review> findByIsVerified(boolean isVerified);

    @Query("SELECT r FROM Review r WHERE r.car.carID = :carId ORDER BY r.createdAt DESC")
    List<Review> findByCarIdOrderByCreatedAtDesc(@Param("carId") int carId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.car.carID = :carId")
    Double findAverageRatingByCarId(@Param("carId") int carId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.car.carID = :carId")
    Long countReviewsByCarId(@Param("carId") int carId);
}
