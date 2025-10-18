package za.co.carhire.domain.feedback;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 16 October 2025 - Enhanced with proper relationships and fields
 */

import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    protected int reviewID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reviews", "imageData"})
    protected Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "idNumber"})
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user", "car", "payment"})
    protected Booking booking;

    @Column(name = "rating", nullable = false)
    protected int rating;

    @Column(name = "title")
    protected String title;

    @Column(name = "comment", columnDefinition = "TEXT")
    protected String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @Column(name = "is_verified")
    protected boolean isVerified;

    public Review() {
    }

    private Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.car = builder.car;
        this.user = builder.user;
        this.booking = builder.booking;
        this.rating = builder.rating;
        this.title = builder.title;
        this.comment = builder.comment;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.isVerified = builder.isVerified;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getReviewID() {
        return reviewID;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", car=" + (car != null ? car.getCarID() : "null") +
                ", user=" + (user != null ? user.getUserId() : "null") +
                ", booking=" + (booking != null ? booking.getBookingID() : "null") +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isVerified=" + isVerified +
                '}';
    }

    public static class Builder{
        private int reviewID;
        private Car car;
        private User user;
        private Booking booking;
        private int rating;
        private String title;
        private String comment;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean isVerified;

        public Builder setReviewID(int reviewID) {
            this.reviewID = reviewID;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setIsVerified(boolean isVerified) {
            this.isVerified = isVerified;
            return this;
        }

        public Review.Builder copy(Review review){
            this.reviewID = review.getReviewID();
            this.car = review.getCar();
            this.user = review.getUser();
            this.booking = review.getBooking();
            this.rating = review.getRating();
            this.title = review.getTitle();
            this.comment = review.getComment();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.isVerified = review.isVerified();
            return this;
        }

        public Review build(){
            return new Review(this);
        }
    }
}
