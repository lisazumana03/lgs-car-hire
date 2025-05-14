package za.co.carhire.factory.reservation;
import za.co.carhire.domain.Review;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 10 May 2025
 */

public class ReviewFactory {

    //Create Review
    public static Review createReview(int reviewID,int userID,int carID ,int rating, String comment){
        if(Helper.isWithinBoundary(reviewID)||
                Helper.isWithinBoundary(userID) ||
                Helper.isWithinBoundary(carID)||
                Helper.isRating(rating) ||
                Helper.isNullOrEmpty(comment)){
            return null;
        }else{
            return new Review.Builder()
                    .setReviewID(reviewID)
                    .setUserID(userID)
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
