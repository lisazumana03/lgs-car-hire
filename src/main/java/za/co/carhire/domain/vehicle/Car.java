package za.co.carhire.domain.vehicle;
// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.reservation.Booking;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int carID;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "year")
    private int year;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "rental_price")
    private double rentalPrice;

    // One-to-One relationship with CarType
    @OneToOne(mappedBy = "car")
    private CarType carType;

    // One-to-One relationship with Insurance
    @OneToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    // Many-to-One relationship with Booking (one booking can have many cars)
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Car() {
    }

    // Private constructor for Builder pattern
    private Car(Builder builder) {
        this.carID = builder.carID;
        this.model = builder.model;
        this.brand = builder.brand;
        this.year = builder.year;
        this.availability = builder.availability;
        this.rentalPrice = builder.rentalPrice;
        this.carType = builder.carType;
        this.insurance = builder.insurance;
        this.booking = builder.booking;
    }

    // Getters and Setters
    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    // Methods from the diagram
    public boolean checkAvailability() {
        return this.availability;
    }

    public void updateAvailability(boolean status) {
        this.availability = status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carID=" + carID +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                ", availability=" + availability +
                ", rentalPrice=" + rentalPrice +
                ", carType=" + (carType != null ? carType.getCarTypeID() : "null") +
                ", insurance=" + (insurance != null ? insurance.getInsuranceID() : "null") +
                ", booking=" + (booking != null ? booking.getBookingID() : "null") +
                '}';
    }

    // Builder class
    public static class Builder {
        private int carID;
        private String model;
        private String brand;
        private int year;
        private boolean availability;
        private double rentalPrice;
        private CarType carType;
        private Insurance insurance;
        private Booking booking;

        public Builder setCarID(int carID) {
            this.carID = carID;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Builder setAvailability(boolean availability) {
            this.availability = availability;
            return this;
        }

        public Builder setRentalPrice(double rentalPrice) {
            this.rentalPrice = rentalPrice;
            return this;
        }

        public Builder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public Builder setInsurance(Insurance insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public Builder copy(Car car) {
            this.carID = car.getCarID();
            this.model = car.getModel();
            this.brand = car.getBrand();
            this.year = car.getYear();
            this.availability = car.isAvailability();
            this.rentalPrice = car.getRentalPrice();
            this.carType = car.getCarType();
            this.insurance = car.getInsurance();
            this.booking = car.getBooking();
            return this;
        }
        // The missing build() method
        public Car build() {
            return new Car(this);
        }

    }
}