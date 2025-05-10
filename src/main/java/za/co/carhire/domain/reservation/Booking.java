package za.co.carhire.domain.reservation;

/*
Lisakhanya Zumana - 230864821
Date: 08 May 2025
 */

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Booking {
    private int bookingID;
    private int customerID;
    private List<Car> cars;
    private LocalDateTime bookingDateAndTime;
    private Date startDate;
    private Date endDate;
    private Location pickupLocation;
    private Location dropOffLocation;
    private String bookingStatus;

    public Booking(){}

    private Booking(Builder builder){
        this.bookingID = builder.bookingID;
        this.customerID = builder.customerID;
        this.cars = builder.cars;
        this.bookingDateAndTime = builder.bookingDateAndTime;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.pickupLocation = builder.pickupLocation;
        this.dropOffLocation = builder.dropOffLocation;
        this.bookingStatus = builder.bookingStatus;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public List<Car> getCar() {
        return cars;
    }

    public LocalDateTime getBookingDateAndTime() {
        return bookingDateAndTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropOffLocation() {
        return dropOffLocation;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", customerID=" + customerID +
                ", car=" + cars +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupLocation=" + pickupLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }

    public static class Builder{
        private int bookingID;
        private int customerID;
        private List<Car> cars;
        private LocalDateTime bookingDateAndTime;
        private Date startDate;
        private Date endDate;
        private Location pickupLocation;
        private Location dropOffLocation;
        private String bookingStatus;

        public Builder setBookingID(int bookingID) {
            this.bookingID = bookingID;
            return this;
        }
        public Builder setCustomerID(int customerID) {
            this.customerID = customerID;
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
        public Builder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder setEndDate(Date endDate) {
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
        public Builder setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder copy(Booking booking){
            this.bookingID = booking.getBookingID();
            this.customerID = booking.getCustomerID();
            this.cars = booking.getCar();
            this.bookingDateAndTime = booking.getBookingDateAndTime();
            this.startDate = booking.getStartDate();
            this.endDate = booking.getEndDate();
            this.pickupLocation = booking.getPickupLocation();
            this.dropOffLocation = booking.getDropOffLocation();
            this.bookingStatus = booking.getBookingStatus();
            return this;
        }

        public Booking build(){
            return new Booking(this);
        }

    }
    
}
