package za.co.carhire.controller.feedback;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.service.feedback.IReviewService;

import java.util.Set;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:3046", "http://127.0.0.1:3046"})
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> create(@RequestBody Review review) {
        Review created = reviewService.create(review);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Review> read(@PathVariable int id) {
        Review review = reviewService.read(id);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Review> update(@RequestBody Review review) {
        Review updated = reviewService.update(review);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Review>> getAll() {
        Set<Review> reviews = reviewService.getReviews();
        return ResponseEntity.ok(reviews);
    }
}
