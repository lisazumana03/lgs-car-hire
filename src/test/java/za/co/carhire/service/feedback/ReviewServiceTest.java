package za.co.carhire.service.impl.feedback;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.repository.feedback.IReviewRepository;
import za.co.carhire.service.feedback.impl.ReviewService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private IReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        review = new Review.Builder()
                .setReviewID(1)
                .setRating(5)
                .setComment("Excellent service")
                .build();
    }

    @Test
    void create() {
        when(reviewRepository.save(review)).thenReturn(review);
        assertEquals(review, reviewService.create(review));
    }

    @Test
    void read() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        assertEquals(review, reviewService.read(1));
    }

    @Test
    void update() {
        when(reviewRepository.existsById(1)).thenReturn(true);
        when(reviewRepository.save(review)).thenReturn(review);
        assertEquals(review, reviewService.update(review));
    }

    @Test
    void delete() {
        reviewService.delete(1);
        verify(reviewRepository).deleteById(1);
    }

    @Test
    void testGetReviews() {
        List<Review> reviewList = Arrays.asList(review);
        when(reviewRepository.findAll()).thenReturn(reviewList);

        Set<Review> result = reviewService.getReviews();
        assertEquals(1, result.size());
        assertTrue(result.contains(review));
    }
}
