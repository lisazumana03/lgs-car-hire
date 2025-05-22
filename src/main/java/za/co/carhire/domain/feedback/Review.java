package za.co.carhire.domain.feedback;

/*
Olwethu Tshingo - 222634383
Date: 10 May 2025
 */

public class Review {
    protected int reviewID;
    protected int userID;
    protected int carID;
    protected int rating;
    protected String comment;

    private Review() {
    }

    private Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.userID = builder.userID;
        this.carID = builder.carID;
        this.rating = builder.rating;
        this.comment = builder.comment;
    }

    public int getReviewID() {
        return reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public int getCarID() {
        return carID;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", userID=" + userID +
                ", carID=" + carID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class Builder{
        private int reviewID;
        private int userID;
        private int carID;
        private int rating;
        private String comment;

        public Builder setReviewID(int reviewID) {
            this.reviewID = reviewID;
            return this;
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setCarID(int carID) {
            this.carID = carID;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Review.Builder copy(Review review){
            this.reviewID = review.getReviewID();
            this.carID = review.getCarID();
            this.userID = review.getUserID();
            this.rating = review.getRating();
            this.comment = review.getComment();
            return this;
        }

        public Review build(){
            return new Review(this);
        }
    }
}
