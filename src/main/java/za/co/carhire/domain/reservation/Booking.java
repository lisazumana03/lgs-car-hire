package za.co.carhire.domain.reservation;

/*
Lisakhanya Zumana - 230864821
Date: 08 May 2025
 */

import java.util.Date;

public class Booking {
    private int bookingID;
    private int customerID;
    //private Car car;
    private Date bookingDateAndTime;
    private Date startDate;
    private Date endDate;
    private Location pickupLocation;
    private Location dropOffLocation;

    public Booking(){}

    public enum BookingStatus {
        CONFIRMED, CANCELLED, REJECTED
    }

    private Booking(Builder builder){
        this.bookingID = builder.bookingID;
        this.customerID = builder.customerID;
        //this.car = builder.car;
        this.bookingDateAndTime = builder.bookingDateAndTime;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.pickupLocation = builder.pickupLocation;
        this.dropOffLocation = builder.dropOffLocation;
    }
    
    public static class Builder{
        private int bookingID;
        private int customerID;
        //private Car car;
        private Date bookingDateAndTime;
        private Date startDate;
        private Date endDate;
        private Location pickupLocation;
        private Location dropOffLocation;
    }
    
}
