package za.co.carhire.repository.feedback;
/*
Olwethu Tshingo - 222634383
Date: 31 July 2025
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.feedback.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findById(int reviewID);
    List<Review> findByRating(int rating);
    List<Review> findAll();
}
