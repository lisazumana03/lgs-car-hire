package za.co.carhire.controller.feedback;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.feedback.IReviewService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private IReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review testReview;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        User user = new User();
        Car car = new Car();
        testReview = new Review.Builder()
                .setReviewID(1)
                .setUser(user)
                .setCar(car)
                .setRating(5)
                .setComment("Excellent service!")
                .build();
    }

    @Test
    void create() {

        when(reviewService.create(testReview)).thenReturn(testReview);

        Review result = reviewController.create(testReview).getBody();

        assertNotNull(result);
        assertEquals(testReview, result);
        verify(reviewService, times(1)).create(testReview);
    }

    @Test
    void createWithNullReview() {

        when(reviewService.create(null)).thenReturn(null);

        Review result = reviewController.create(null).getBody();

        assertNull(result);
        verify(reviewService, times(1)).create(null);
    }

    @Test
    void read() {

        int reviewId = 1;
        when(reviewService.read(reviewId)).thenReturn(testReview);

        Review result = reviewController.create(null).getBody();

        assertNotNull(result);
        assertEquals(testReview, result);
        verify(reviewService, times(1)).read(reviewId);
    }
    @Test
    void readInvalidReviewId() {

        int invalidId = -1;
        when(reviewService.read(invalidId)).thenReturn(null);

        Review result = reviewController.create(null).getBody();

        assertNull(result);
        verify(reviewService, times(1)).read(invalidId);
    }


    @Test
    void updateReview() {

        when(reviewService.update(testReview)).thenReturn(testReview);

        Review result = reviewController.update(testReview).getBody();

        assertNotNull(result);
        assertEquals(testReview, result);
        verify(reviewService, times(1)).update(testReview);
    }

    @Test
    void updateWithEmptyReview() {

        when(reviewService.update(null)).thenReturn(null);

        Review result = reviewController.update(null).getBody();

        assertNull(result);
        verify(reviewService, times(1)).update(null);
    }

    @Test
    void delete() {
        // Arrange
        int revId = 1;
        doNothing().when(reviewService).delete(revId);

        reviewController.delete(revId);

        verify(reviewService, times(1)).delete(revId);
    }

    @Test
    void deleteInvalidReviewId() {

        int revID = -1;
        doNothing().when(reviewService).delete(revID);

        assertDoesNotThrow(() -> reviewController.delete(revID));
        verify(reviewService, times(1)).delete(revID);
    }
}

