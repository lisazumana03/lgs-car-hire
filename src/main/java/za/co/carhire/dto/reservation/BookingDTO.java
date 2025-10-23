package za.co.carhire.dto.reservation;

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BookingDTO implements Serializable {
    private int bookingID;
    private User user;
    private Car car;
    private LocalDateTime bookingDateAndTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Location pickupLocation;
    private Location dropOffLocation;
    private BookingStatus bookingStatus;

    public BookingDTO() {
    }

    public BookingDTO(int bookingID, User user, Car car, LocalDateTime bookingDateAndTime,
                      LocalDateTime startDate, LocalDateTime endDate,
                      Location pickupLocation, Location dropOffLocation,
                      BookingStatus bookingStatus) {
        this.bookingID = bookingID;
        this.user = user;
        this.car = car;
        this.bookingDateAndTime = bookingDateAndTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.bookingStatus = bookingStatus;
    }

    // Builder pattern for DTO
    public static class Builder {
        private int bookingID;
        private User user;
        private Car car;
        private LocalDateTime bookingDateAndTime;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Location pickupLocation;
        private Location dropOffLocation;
        private BookingStatus bookingStatus;

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

        public BookingDTO build() {
            return new BookingDTO(bookingID, user, car, bookingDateAndTime, startDate, endDate,
                    pickupLocation, dropOffLocation, bookingStatus);
        }
    }

    // Getters and Setters
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public LocalDateTime getBookingDateAndTime() { return bookingDateAndTime; }
    public void setBookingDateAndTime(LocalDateTime bookingDateAndTime) { this.bookingDateAndTime = bookingDateAndTime; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Location getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(Location pickupLocation) { this.pickupLocation = pickupLocation; }

    public Location getDropOffLocation() { return dropOffLocation; }
    public void setDropOffLocation(Location dropOffLocation) { this.dropOffLocation = dropOffLocation; }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID +
                ", user=" + (user != null ? user.getUserId() : "null") +
                ", car=" + (car != null ? car.getCarID() : "null") +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupLocation=" + (pickupLocation != null ? pickupLocation.getLocationID() : "null") +
                ", dropOffLocation=" + (dropOffLocation != null ? dropOffLocation.getLocationID() : "null") +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}