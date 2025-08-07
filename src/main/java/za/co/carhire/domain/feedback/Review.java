package za.co.carhire.domain.feedback;

/*
Olwethu Tshingo - 222634383
Date: 31 July 2025
 */

import jakarta.persistence.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.vehicle.Car;

import java.io.Serializable;

@Entity
public class Review implements Serializable {
    @Id
    protected int reviewID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
    @ManyToOne
    @JoinColumn(name = "car_id")
    protected Car car;
    protected int rating;
    protected String comment;

    public Review() {
    }

    private Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.user = builder.user;
        this.car = builder.car;
        this.rating = builder.rating;
        this.comment = builder.comment;
    }

    public int getReviewID() {
        return reviewID;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
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
                ", userID=" + user +
                ", carID=" + car +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class Builder{
        private int reviewID;
        private User user;
        private Car car;
        private int rating;
        private String comment;

        public Builder setReviewID(int reviewID) {
            this.reviewID = reviewID;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
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
            this.car = review.getCar();
            this.user = review.getUser();
            this.rating = review.getRating();
            this.comment = review.getComment();
            return this;
        }

        public Review build(){
            return new Review(this);
        }
    }
}
