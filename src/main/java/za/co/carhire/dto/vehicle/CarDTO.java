package za.co.carhire.dto.vehicle;

import java.io.Serializable;

/**
 * Data Transfer Object for Car entity with full CarType details
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 * Updated: 31/08/2025 - Added image URL support
 * Updated: 15/10/2025 - Refactored to match improved Car entity
 */
public class CarDTO implements Serializable {

    private int carID;
    private String model;
    private String brand;
    private int year;
    private String licensePlate;
    private String vin;
    private String color;
    private int mileage;
    private String status;
    private String condition;
    private Integer currentLocationID;
    private String currentLocationName;
    private byte[] imageData;
    private String imageBase64;
    private String imageName;
    private String imageType;

    private Integer carTypeID;
    private String carTypeCategory;
    private String carTypeFuelType;
    private String carTypeTransmissionType;
    private Integer carTypeNumberOfSeats;
    private Integer carTypeNumberOfDoors;
    private Boolean carTypeAirConditioned;
    private Integer carTypeLuggageCapacity;
    private String carTypeDescription;

    public CarDTO() {
    }

    public CarDTO(int carID, String model, String brand, int year,
            String licensePlate, String status) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.licensePlate = licensePlate;
        this.status = status;
    }

    public static class Builder {
        private int carID;
        private String model;
        private String brand;
        private int year;
        private String licensePlate;
        private String vin;
        private String color;
        private int mileage;
        private String status;
        private String condition;
        private Integer currentLocationID;
        private String currentLocationName;
        private byte[] imageData;
        private String imageBase64;
        private String imageName;
        private String imageType;
        private Integer carTypeID;
        private String carTypeCategory;
        private String carTypeFuelType;
        private String carTypeTransmissionType;
        private Integer carTypeNumberOfSeats;
        private Integer carTypeNumberOfDoors;
        private Boolean carTypeAirConditioned;
        private Integer carTypeLuggageCapacity;
        private String carTypeDescription;

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

        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setVin(String vin) {
            this.vin = vin;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setMileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder setCurrentLocationID(Integer currentLocationID) {
            this.currentLocationID = currentLocationID;
            return this;
        }

        public Builder setCurrentLocationName(String currentLocationName) {
            this.currentLocationName = currentLocationName;
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

        public Builder setCarTypeCategory(String carTypeCategory) {
            this.carTypeCategory = carTypeCategory;
            return this;
        }

        public Builder setCarTypeFuelType(String carTypeFuelType) {
            this.carTypeFuelType = carTypeFuelType;
            return this;
        }

        public Builder setCarTypeTransmissionType(String carTypeTransmissionType) {
            this.carTypeTransmissionType = carTypeTransmissionType;
            return this;
        }

        public Builder setCarTypeNumberOfSeats(Integer carTypeNumberOfSeats) {
            this.carTypeNumberOfSeats = carTypeNumberOfSeats;
            return this;
        }

        public Builder setCarTypeNumberOfDoors(Integer carTypeNumberOfDoors) {
            this.carTypeNumberOfDoors = carTypeNumberOfDoors;
            return this;
        }

        public Builder setCarTypeAirConditioned(Boolean carTypeAirConditioned) {
            this.carTypeAirConditioned = carTypeAirConditioned;
            return this;
        }

        public Builder setCarTypeLuggageCapacity(Integer carTypeLuggageCapacity) {
            this.carTypeLuggageCapacity = carTypeLuggageCapacity;
            return this;
        }

        public Builder setCarTypeDescription(String carTypeDescription) {
            this.carTypeDescription = carTypeDescription;
            return this;
        }

        public CarDTO build() {
            CarDTO dto = new CarDTO();
            dto.carID = this.carID;
            dto.model = this.model;
            dto.brand = this.brand;
            dto.year = this.year;
            dto.licensePlate = this.licensePlate;
            dto.vin = this.vin;
            dto.color = this.color;
            dto.mileage = this.mileage;
            dto.status = this.status;
            dto.condition = this.condition;
            dto.currentLocationID = this.currentLocationID;
            dto.currentLocationName = this.currentLocationName;
            dto.imageData = this.imageData;
            dto.imageBase64 = this.imageBase64;
            dto.imageName = this.imageName;
            dto.imageType = this.imageType;
            dto.carTypeID = this.carTypeID;
            dto.carTypeCategory = this.carTypeCategory;
            dto.carTypeFuelType = this.carTypeFuelType;
            dto.carTypeTransmissionType = this.carTypeTransmissionType;
            dto.carTypeNumberOfSeats = this.carTypeNumberOfSeats;
            dto.carTypeNumberOfDoors = this.carTypeNumberOfDoors;
            dto.carTypeAirConditioned = this.carTypeAirConditioned;
            dto.carTypeLuggageCapacity = this.carTypeLuggageCapacity;
            dto.carTypeDescription = this.carTypeDescription;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getCurrentLocationID() {
        return currentLocationID;
    }

    public void setCurrentLocationID(Integer currentLocationID) {
        this.currentLocationID = currentLocationID;
    }

    public String getCurrentLocationName() {
        return currentLocationName;
    }

    public void setCurrentLocationName(String currentLocationName) {
        this.currentLocationName = currentLocationName;
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

    public String getCarTypeCategory() {
        return carTypeCategory;
    }

    public void setCarTypeCategory(String carTypeCategory) {
        this.carTypeCategory = carTypeCategory;
    }

    public String getCarTypeFuelType() {
        return carTypeFuelType;
    }

    public void setCarTypeFuelType(String carTypeFuelType) {
        this.carTypeFuelType = carTypeFuelType;
    }

    public String getCarTypeTransmissionType() {
        return carTypeTransmissionType;
    }

    public void setCarTypeTransmissionType(String carTypeTransmissionType) {
        this.carTypeTransmissionType = carTypeTransmissionType;
    }

    public Integer getCarTypeNumberOfSeats() {
        return carTypeNumberOfSeats;
    }

    public void setCarTypeNumberOfSeats(Integer carTypeNumberOfSeats) {
        this.carTypeNumberOfSeats = carTypeNumberOfSeats;
    }

    public Integer getCarTypeNumberOfDoors() {
        return carTypeNumberOfDoors;
    }

    public void setCarTypeNumberOfDoors(Integer carTypeNumberOfDoors) {
        this.carTypeNumberOfDoors = carTypeNumberOfDoors;
    }

    public Boolean getCarTypeAirConditioned() {
        return carTypeAirConditioned;
    }

    public void setCarTypeAirConditioned(Boolean carTypeAirConditioned) {
        this.carTypeAirConditioned = carTypeAirConditioned;
    }

    public Integer getCarTypeLuggageCapacity() {
        return carTypeLuggageCapacity;
    }

    public void setCarTypeLuggageCapacity(Integer carTypeLuggageCapacity) {
        this.carTypeLuggageCapacity = carTypeLuggageCapacity;
    }

    public String getCarTypeDescription() {
        return carTypeDescription;
    }

    public void setCarTypeDescription(String carTypeDescription) {
        this.carTypeDescription = carTypeDescription;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "carID=" + carID +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                ", licensePlate='" + licensePlate + '\'' +
                ", vin='" + vin + '\'' +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", status='" + status + '\'' +
                ", condition='" + condition + '\'' +
                ", currentLocationID=" + currentLocationID +
                ", currentLocationName='" + currentLocationName + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", carTypeID=" + carTypeID +
                ", carTypeCategory='" + carTypeCategory + '\'' +
                ", carTypeFuelType='" + carTypeFuelType + '\'' +
                ", carTypeTransmissionType='" + carTypeTransmissionType + '\'' +
                ", carTypeNumberOfSeats=" + carTypeNumberOfSeats +
                ", carTypeNumberOfDoors=" + carTypeNumberOfDoors +
                ", carTypeAirConditioned=" + carTypeAirConditioned +
                ", carTypeLuggageCapacity=" + carTypeLuggageCapacity +
                ", carTypeDescription='" + carTypeDescription + '\'' +
                '}';
    }
}
