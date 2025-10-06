package za.co.carhire.domain.feedback;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;

import java.io.Serializable;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int reviewID;

    @ManyToOne
    @JoinColumn(name ="car_id")
    protected Car car;

    protected String fullName;
    protected int rating;
    protected String comment;

    public Review() {
    }

    private Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.car = builder.car;
        this.fullName = builder.fullName;
        this.rating = builder.rating;
        this.comment = builder.comment;
    }

    public int getReviewID() {
        return reviewID;
    }

    public String getFullName() {
        return fullName;
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
                ", carID=" + car +
                ", fullName=" + fullName +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class Builder{
        private int reviewID;
        private String fullName;
        private Car car;
        private int rating;
        private String comment;

        public Builder setReviewID(int reviewID) {
            this.reviewID = reviewID;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
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
            this.fullName = review.getFullName();
            this.rating = review.getRating();
            this.comment = review.getComment();
            return this;
        }

        public Review build(){
            return new Review(this);
        }
    }
}
