package za.co.carhire.factory.feedback;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 16 October 2025 - Updated to match new Review entity structure
 */

public class ReviewFactory {

    public static Review createReview(int reviewID, Car car, User user, Booking booking, String title, String comment, int rating, boolean isVerified){
        if(Helper.isWithinBoundary(reviewID) ||
                car == null ||
                user == null ||
                Helper.isEmptyOrNull(comment) ||
                rating < 1 || rating > 5){
            return null;
        }else{
            return new Review.Builder()
                    .setReviewID(reviewID)
                    .setCar(car)
                    .setUser(user)
                    .setBooking(booking)
                    .setTitle(title)
                    .setComment(comment)
                    .setRating(rating)
                    .setIsVerified(isVerified)
                    .build();
        }
    }

    public static Review createReview(Car car, User user, Booking booking, String title, String comment, int rating){
        if(car == null ||
                user == null ||
                Helper.isEmptyOrNull(comment) ||
                rating < 1 || rating > 5){
            return null;
        }else{
            return new Review.Builder()
                    .setCar(car)
                    .setUser(user)
                    .setBooking(booking)
                    .setTitle(title)
                    .setComment(comment)
                    .setRating(rating)
                    .setIsVerified(booking != null) // Verified if linked to booking
                    .build();
        }
    }

    //Create Review - simplified version without booking
    public static Review createReview(Car car, User user, String title, String comment, int rating){
        if(car == null ||
                user == null ||
                Helper.isEmptyOrNull(comment) ||
                rating < 1 || rating > 5){
            return null;
        }else{
            return new Review.Builder()
                    .setCar(car)
                    .setUser(user)
                    .setTitle(title)
                    .setComment(comment)
                    .setRating(rating)
                    .setIsVerified(false)
                    .build();
        }
    }

    //Delete Review
    public static Review deleteReview(int reviewID){
        if (Helper.isWithinBoundary(reviewID)) {
            return null;
        }

        return new Review.Builder()
                .setReviewID(reviewID)
                .setComment("This review has been deleted.")
                .build();
    }
}
