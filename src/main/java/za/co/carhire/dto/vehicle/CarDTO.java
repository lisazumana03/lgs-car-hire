package za.co.carhire.dto.vehicle;

import java.io.Serializable;

/**
 * Data Transfer Object for Car entity
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 */
public class CarDTO implements Serializable {
    
    private int carID;
    private String model;
    private String brand;
    private int year;
    private boolean availability;
    private double rentalPrice;
    
    private Integer carTypeID;
    private String carTypeName;
    private Integer insuranceID;
    private Integer bookingID;
    
    public CarDTO() {
    }
    
    public CarDTO(int carID, String model, String brand, int year, 
                  boolean availability, double rentalPrice) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.availability = availability;
        this.rentalPrice = rentalPrice;
    }
    
    public CarDTO(int carID, String model, String brand, int year, 
                  boolean availability, double rentalPrice,
                  Integer carTypeID, String carTypeName, 
                  Integer insuranceID, Integer bookingID) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.availability = availability;
        this.rentalPrice = rentalPrice;
        this.carTypeID = carTypeID;
        this.carTypeName = carTypeName;
        this.insuranceID = insuranceID;
        this.bookingID = bookingID;
    }
    
    public static class Builder {
        private int carID;
        private String model;
        private String brand;
        private int year;
        private boolean availability;
        private double rentalPrice;
        private Integer carTypeID;
        private String carTypeName;
        private Integer insuranceID;
        private Integer bookingID;
        
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
        
        public Builder setCarTypeID(Integer carTypeID) {
            this.carTypeID = carTypeID;
            return this;
        }
        
        public Builder setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
            return this;
        }
        
        public Builder setInsuranceID(Integer insuranceID) {
            this.insuranceID = insuranceID;
            return this;
        }
        
        public Builder setBookingID(Integer bookingID) {
            this.bookingID = bookingID;
            return this;
        }
        
        public CarDTO build() {
            CarDTO dto = new CarDTO();
            dto.carID = this.carID;
            dto.model = this.model;
            dto.brand = this.brand;
            dto.year = this.year;
            dto.availability = this.availability;
            dto.rentalPrice = this.rentalPrice;
            dto.carTypeID = this.carTypeID;
            dto.carTypeName = this.carTypeName;
            dto.insuranceID = this.insuranceID;
            dto.bookingID = this.bookingID;
            return dto;
        }
    }
    
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
    
    public Integer getCarTypeID() {
        return carTypeID;
    }
    
    public void setCarTypeID(Integer carTypeID) {
        this.carTypeID = carTypeID;
    }
    
    public String getCarTypeName() {
        return carTypeName;
    }
    
    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
    
    public Integer getInsuranceID() {
        return insuranceID;
    }
    
    public void setInsuranceID(Integer insuranceID) {
        this.insuranceID = insuranceID;
    }
    
    public Integer getBookingID() {
        return bookingID;
    }
    
    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }
    
    @Override
    public String toString() {
        return "CarDTO{" +
                "carID=" + carID +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                ", availability=" + availability +
                ", rentalPrice=" + rentalPrice +
                ", carTypeID=" + carTypeID +
                ", carTypeName='" + carTypeName + '\'' +
                ", insuranceID=" + insuranceID +
                ", bookingID=" + bookingID +
                '}';
    }
}