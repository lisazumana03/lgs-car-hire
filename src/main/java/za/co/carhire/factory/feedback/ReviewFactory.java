package za.co.carhire.factory.feedback;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

public class ReviewFactory {

    //Create Review
    public static Review createReview(int reviewID,Car car , String fullName, String comment, int rating){
        if(Helper.isWithinBoundary(reviewID)||
                Helper.isEmptyOrNull(comment)){
            return null;
        }else{
            return new Review.Builder()
                    .setReviewID(reviewID)
                    .setCar(car)
                    .setFullName(fullName)
                    .setComment(comment)
                    .setRating(rating)
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
