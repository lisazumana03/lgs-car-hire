package za.co.carhire.service.feedback;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.service.IService;

import java.util.Set;

public interface IReviewService extends IService<Review, Integer> {
    Set<Review> getReviews();
    Review create(Review review);
    Review read(int reviewID);
    Review update(Review review);
    void delete(int reviewID);
}
