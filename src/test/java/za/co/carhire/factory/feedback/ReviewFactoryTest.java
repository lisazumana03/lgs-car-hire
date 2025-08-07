package za.co.carhire.factory.feedback;
/*
Olwethu Tshingo - 222634383
Date: 31 July 2025
 */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.vehicle.Car;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewFactoryTest {

    @Test
    public void testCreateReviewValid() {

        User user = new User();   // Stub user (can be mocked or built if needed)
        Car car = new Car();      // Stub car
        int reviewID = 1;
        int rating = 4;
        String comment = "Great car, smooth ride.";

        Review review = ReviewFactory.createReview(reviewID, user, car, rating, comment);

        assertNotNull(review);
        assertEquals(reviewID, review.getReviewID());
        assertEquals(user, review.getUser());
        assertEquals(car, review.getCar());
        assertEquals(rating, review.getRating());
        assertEquals(comment, review.getComment());
    }

    @Test
    public void testCreateReviewInvalid() {

        Review review = ReviewFactory.createReview(-1, new User(), new Car(), 4, "Nice");  // Invalid reviewID
        assertNull(review);
    }

    @Test
    public void testDeleteReviewValid() {

        int reviewID = 2;
        Review deletedReview = ReviewFactory.deleteReview(reviewID);

        assertNotNull(deletedReview);
        assertEquals(reviewID, deletedReview.getReviewID());
        assertEquals("This review has been deleted.", deletedReview.getComment());
    }

    @Test
    public void testDeleteReviewInvalid() {
        Review deletedReview = ReviewFactory.deleteReview(-1); // Invalid ID
        assertNull(deletedReview);


    }
}
