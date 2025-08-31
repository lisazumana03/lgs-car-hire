package za.co.carhire.domain.reservation;

/*
Booking.java
Lisakhanya Zumana - 230864821
Date: 08 May 2025

updated - Imtiyaaz Waggie 219374759
 */

import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.authentication.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    private LocalDateTime bookingDateAndTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "pickup_location_id")
    private Location pickupLocation;

    @ManyToOne
    @JoinColumn(name = "dropoff_location_id")
    private Location dropOffLocation;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private double totalCost;

    public Booking(){}

    private Booking(Builder builder){
        this.bookingID = builder.bookingID;
        this.user = builder.user;
        this.car = builder.car;
        this.insurance = builder.insurance;
        this.bookingDateAndTime = builder.bookingDateAndTime;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.payment = builder.payment;
        this.pickupLocation = builder.pickupLocation;
        this.dropOffLocation = builder.dropOffLocation;
        this.bookingStatus = builder.bookingStatus;
        this.totalCost = builder.totalCost;
    }

    public int getBookingID() {
        return bookingID;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public LocalDateTime getBookingDateAndTime() {
        return bookingDateAndTime;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropOffLocation() {
        return dropOffLocation;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public void setBookingDateAndTime(LocalDateTime bookingDateAndTime) {
        this.bookingDateAndTime = bookingDateAndTime;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropOffLocation(Location dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", user=" + (user != null ? user.getUserId() : "null") +
                ", car=" + (car != null ? car.getCarID() : "null") +
                ", insurance=" + (insurance != null ? insurance.getInsuranceID() : "null") +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", payment=" + (payment != null ? payment.getPaymentID() : "null") +
                ", pickupLocation=" + (pickupLocation != null ? pickupLocation.getLocationID() : "null") +
                ", dropOffLocation=" + (dropOffLocation != null ? dropOffLocation.getLocationID() : "null") +
                ", bookingStatus=" + bookingStatus +
                ", totalCost=" + totalCost +
                '}';
    }

    public static class Builder{
        private int bookingID;
        private User user;
        private Car car;
        private Insurance insurance;
        private LocalDateTime bookingDateAndTime;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Payment payment;
        private Location pickupLocation;
        private Location dropOffLocation;
        private BookingStatus bookingStatus;
        private double totalCost;

        public Builder setBookingID(int bookingID) {
            this.bookingID = bookingID;
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

        public Builder setInsurance(Insurance insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder setBookingDateAndTime(LocalDateTime bookingDateAndTime) {
            this.bookingDateAndTime = bookingDateAndTime;
            return this;
        }

        public Builder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Builder setPickupLocation(Location pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public Builder setDropOffLocation(Location dropOffLocation) {
            this.dropOffLocation = dropOffLocation;
            return this;
        }

        public Builder setBookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder setTotalCost(double totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public Builder copy(Booking booking){
            this.bookingID = booking.getBookingID();
            this.user = booking.getUser();
            this.car = booking.getCar();
            this.insurance = booking.getInsurance();
            this.bookingDateAndTime = booking.getBookingDateAndTime();
            this.startDate = booking.getStartDate();
            this.endDate = booking.getEndDate();
            this.payment = booking.getPayment();
            this.pickupLocation = booking.getPickupLocation();
            this.dropOffLocation = booking.getDropOffLocation();
            this.bookingStatus = booking.getBookingStatus();
            this.totalCost = booking.getTotalCost();
            return this;
        }

        public Booking build(){
            return new Booking(this);
        }
    }
}