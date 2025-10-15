package za.co.carhire.dto.vehicle;

import java.io.Serializable;

/**
 * Data Transfer Object for Car entity with full CarType details
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 * Updated: 31/08/2025 - Added image URL support
 */
public class CarDTO implements Serializable {

    private int carID;
    private String model;
    private String brand;
    private int year;
    private boolean availability;
    private double rentalPrice;
    private byte[] imageData;
    private String imageBase64;
    private String imageName;
    private String imageType;

    private Integer carTypeID;
    private String carTypeName;
    private String carTypeFuelType;
    private Integer carTypeNumberOfWheels;
    private Integer carTypeNumberOfSeats;

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
            boolean availability, double rentalPrice, String imageBase64,
            String imageName, String imageType,
            Integer carTypeID, String carTypeName) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.availability = availability;
        this.rentalPrice = rentalPrice;
        this.imageBase64 = imageBase64;
        this.imageName = imageName;
        this.imageType = imageType;
        this.carTypeID = carTypeID;
        this.carTypeName = carTypeName;
    }

    public static class Builder {
        private int carID;
        private String model;
        private String brand;
        private int year;
        private boolean availability;
        private double rentalPrice;
        private byte[] imageData;
        private String imageBase64;
        private String imageName;
        private String imageType;
        private Integer carTypeID;
        private String carTypeName;
        private String carTypeFuelType;
        private Integer carTypeNumberOfWheels;
        private Integer carTypeNumberOfSeats;

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

        public Builder setImageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public Builder setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
            return this;
        }

        public Builder setImageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public Builder setImageType(String imageType) {
            this.imageType = imageType;
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

        public Builder setCarTypeFuelType(String carTypeFuelType) {
            this.carTypeFuelType = carTypeFuelType;
            return this;
        }

        public Builder setCarTypeNumberOfWheels(Integer carTypeNumberOfWheels) {
            this.carTypeNumberOfWheels = carTypeNumberOfWheels;
            return this;
        }

        public Builder setCarTypeNumberOfSeats(Integer carTypeNumberOfSeats) {
            this.carTypeNumberOfSeats = carTypeNumberOfSeats;
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
            dto.imageData = this.imageData;
            dto.imageBase64 = this.imageBase64;
            dto.imageName = this.imageName;
            dto.imageType = this.imageType;
            dto.carTypeID = this.carTypeID;
            dto.carTypeName = this.carTypeName;
            dto.carTypeFuelType = this.carTypeFuelType;
            dto.carTypeNumberOfWheels = this.carTypeNumberOfWheels;
            dto.carTypeNumberOfSeats = this.carTypeNumberOfSeats;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
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

    public String getCarTypeFuelType() {
        return carTypeFuelType;
    }

    public void setCarTypeFuelType(String carTypeFuelType) {
        this.carTypeFuelType = carTypeFuelType;
    }

    public Integer getCarTypeNumberOfWheels() {
        return carTypeNumberOfWheels;
    }

    public void setCarTypeNumberOfWheels(Integer carTypeNumberOfWheels) {
        this.carTypeNumberOfWheels = carTypeNumberOfWheels;
    }

    public Integer getCarTypeNumberOfSeats() {
        return carTypeNumberOfSeats;
    }

    public void setCarTypeNumberOfSeats(Integer carTypeNumberOfSeats) {
        this.carTypeNumberOfSeats = carTypeNumberOfSeats;
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
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", carTypeID=" + carTypeID +
                ", carTypeName='" + carTypeName + '\'' +
                ", carTypeFuelType='" + carTypeFuelType + '\'' +
                ", carTypeNumberOfWheels=" + carTypeNumberOfWheels +
                ", carTypeNumberOfSeats=" + carTypeNumberOfSeats +
                '}';
    }
}