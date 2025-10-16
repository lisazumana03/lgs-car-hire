package za.co.carhire.domain.reservation;

/**
Booking.java
Booking POJO class
Lisakhanya Zumana - 230864821
Date: 08 May 2025
 */

import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.authentication.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    private LocalDateTime bookingDateAndTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "pickup_location_id")
    private Location pickupLocation;
    @ManyToOne
    @JoinColumn(name = "dropoff_location_id")
    private Location dropOffLocation;
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @Column(name = "rental_days")
    private int rentalDays;
    @Column(name = "subtotal")
    private double subtotal;
    @Column(name = "tax_amount")
    private double taxAmount;
    @Column(name = "discount_amount")
    private double discountAmount;
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(name = "currency")
    private String currency;

    public Booking(){}// for JPA implementation

    private Booking(Builder builder){
        this.bookingID = builder.bookingID;
        this.user = builder.user;
        this.car = builder.car;
        this.bookingDateAndTime = builder.bookingDateAndTime;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.payment = builder.payment;
        this.pickupLocation = builder.pickupLocation;
        this.dropOffLocation = builder.dropOffLocation;
        this.insurance = builder.insurance;
        this.bookingStatus = builder.bookingStatus;
        this.rentalDays = builder.rentalDays;
        this.subtotal = builder.subtotal;
        this.taxAmount = builder.taxAmount;
        this.discountAmount = builder.discountAmount;
        this.totalAmount = builder.totalAmount;
        this.currency = builder.currency;
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

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setPayment(Payment payment) { this.payment = payment; }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", user=" + user +
                ", car=" + car +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", payment=" + payment +
                ", pickupLocation=" + pickupLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", insurance=" + insurance +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", rentalDays=" + rentalDays +
                ", subtotal=" + subtotal +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                '}';
    }

    public static class Builder{
        private int bookingID;
        private User user;
        private Car car;
        private LocalDateTime bookingDateAndTime;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Payment payment;
        private Location pickupLocation;
        private Location dropOffLocation;
        private Insurance insurance;
        private BookingStatus bookingStatus;
        private int rentalDays;
        private double subtotal;
        private double taxAmount;
        private double discountAmount;
        private double totalAmount;
        private String currency = "ZAR";

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

        public Builder setInsurance(Insurance insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder setBookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder setRentalDays(int rentalDays) {
            this.rentalDays = rentalDays;
            return this;
        }

        public Builder setSubtotal(double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder setDiscountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder copy(Booking booking){
            this.bookingID = booking.getBookingID();
            this.user = booking.getUser();
            this.car = booking.getCar();
            this.bookingDateAndTime = booking.getBookingDateAndTime();
            this.startDate = booking.getStartDate();
            this.endDate = booking.getEndDate();
            this.payment = booking.getPayment();
            this.pickupLocation = booking.getPickupLocation();
            this.dropOffLocation = booking.getDropOffLocation();
            this.insurance = booking.getInsurance();
            this.bookingStatus = booking.getBookingStatus();
            this.rentalDays = booking.getRentalDays();
            this.subtotal = booking.getSubtotal();
            this.taxAmount = booking.getTaxAmount();
            this.discountAmount = booking.getDiscountAmount();
            this.totalAmount = booking.getTotalAmount();
            this.currency = booking.getCurrency();
            return this;
        }

        public Booking build(){
            return new Booking(this);
        }

    }
    
}
