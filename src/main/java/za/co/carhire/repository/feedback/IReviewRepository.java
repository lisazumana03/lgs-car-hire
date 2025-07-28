package za.co.carhire.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.feedback.Review;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {
}
