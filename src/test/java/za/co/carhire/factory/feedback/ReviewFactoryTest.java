package za.co.carhire.factory.feedback;

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.feedback.Review;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewFactoryTest {

    @Test
    public void testCreateReviewValid() {
        Review review = ReviewFactory.createReview(1, 101, 201, 4, "Great car, smooth ride.");

        assertNotNull(review);
        assertEquals(1, review.getReviewID());
        assertEquals(4, review.getRating());
        assertEquals("Great car, smooth ride.", review.getComment());
    }

    @Test
    public void testCreateReviewInvalid() {
        Review review = ReviewFactory.createReview(-1, 101, 201, 4, "Nice");  // Invalid reviewID
        assertNull(review);
    }

    @Test
    public void testDeleteReviewValid() {
        Review deletedReview = ReviewFactory.deleteReview(1);

        assertNotNull(deletedReview);
        assertEquals(1, deletedReview.getReviewID());
        assertEquals("This review has been deleted.", deletedReview.getComment());
    }

    @Test
    public void testDeleteReviewInvalid() {
        Review deletedReview = ReviewFactory.deleteReview(-1); // Invalid ID
        assertNull(deletedReview);
    }
}
