package za.co.carhire.factory.feedback;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 14 May 2025
 */

public class ReviewFactory {

    //Create Review
    public static Review createReview(int reviewID, User user, int carID , int rating, String comment){
        if(Helper.isWithinBoundary(reviewID)||
                Helper.isWithinBoundary(carID)||
                Helper.isRating(rating) ||
                Helper.isNullOrEmpty(comment)){
            return null;
        }else{
            return new Review.Builder()
                    .setReviewID(reviewID)
                    .setUser(user)
                    .setCarID(carID)
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
