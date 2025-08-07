package za.co.carhire.service.feedback.impl;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.repository.feedback.IReviewRepository;
import za.co.carhire.service.feedback.IReviewService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override

    public Review read(Integer reviewID) {
        Optional<Review> review = reviewRepository.findById(reviewID);
        return review.orElse(null);
    }

    public Review read(int reviewID) {
        return reviewRepository.findById(reviewID).orElse(null);
    }


    @Override
    public Review update(Review review) {
        if (reviewRepository.existsById(review.getReviewID())) {
            return reviewRepository.save(review);
        }
        return null;
    }

    @Override
    public void delete(int reviewID) {
        reviewRepository.deleteById(reviewID);
    }

    @Override
    public Set<Review> getReviews() {
        return reviewRepository.findAll().stream().collect(Collectors.toSet());
    }
}
