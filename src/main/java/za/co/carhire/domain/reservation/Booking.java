package za.co.carhire.domain.reservation;

/*
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "booking")// showing that one booking can be made for many cars
    private List<Car> cars;
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
    @OneToMany
    private List<Notification> bookingNotifications;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public Booking(){}// for JPA implementation coming soon

    private Booking(Builder builder){
        this.bookingID = builder.bookingID;
        this.user = builder.user;
        this.cars = builder.cars;
        this.bookingDateAndTime = builder.bookingDateAndTime;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.payment = builder.payment;
        this.pickupLocation = builder.pickupLocation;
        this.dropOffLocation = builder.dropOffLocation;
        this.bookingNotifications = builder.bookingNotification;
        this.bookingStatus = builder.bookingStatus;
    }

    public int getBookingID() {
        return bookingID;
    }

    public User getUser() {
        return user;
    }

    public List<Car> getCar() {
        return cars;
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

    public List<Notification> getBookingNotification() {
        return bookingNotifications;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setPayment(Payment payment) { this.payment = payment; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", user=" + user +
                ", car=" + cars +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", payment=" + payment +
                ", pickupLocation=" + pickupLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }

    public static class Builder{
        private int bookingID;
        private User user;
        private List<Car> cars;
        private LocalDateTime bookingDateAndTime;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Payment payment;
        private Location pickupLocation;
        private Location dropOffLocation;
        private List<Notification> bookingNotification;
        private BookingStatus bookingStatus;

        public Builder setBookingID(int bookingID) {
            this.bookingID = bookingID;
            return this;
        }
        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        public Builder setCar(List<Car> cars) {
            this.cars = cars;
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

        public Builder setBookingNotification(List<Notification> bookingNotification) {
            this.bookingNotification = bookingNotification;
            return this;
        }

        public Builder setBookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder copy(Booking booking){
            this.bookingID = booking.getBookingID();
            this.user = booking.getUser();
            this.cars = booking.getCar();
            this.bookingDateAndTime = booking.getBookingDateAndTime();
            this.startDate = booking.getStartDate();
            this.endDate = booking.getEndDate();
            this.payment = booking.getPayment();
            this.pickupLocation = booking.getPickupLocation();
            this.dropOffLocation = booking.getDropOffLocation();
            this.bookingNotification = booking.getBookingNotification();
            this.bookingStatus = booking.getBookingStatus();
            return this;
        }

        public Booking build(){
            return new Booking(this);
        }

    }
    
}
