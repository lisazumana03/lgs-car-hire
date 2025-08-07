package za.co.carhire.factory.feedback;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 14 May 2025
 */

public class ReviewFactory {

    //Create Review
    public static Review createReview(int reviewID, User user, Car car , int rating, String comment){
        if(Helper.isWithinBoundary(reviewID)||
                Helper.isRating(rating) ||
                Helper.isEmptyOrNull(comment)){
            return null;
        }else{
            return new Review.Builder()
                    .setReviewID(reviewID)
                    .setUser(user)
                    .setCar(car)
                    .setRating(rating)
                    .setComment(comment)
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
