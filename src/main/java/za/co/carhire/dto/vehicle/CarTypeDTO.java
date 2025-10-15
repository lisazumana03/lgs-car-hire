package za.co.carhire.dto.vehicle;

import java.io.Serializable;

/**
 * Data Transfer Object for CarType entity
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 */
public class CarTypeDTO implements Serializable {

    private int carTypeID;
    private String category;
    private String fuelType;
    private String transmissionType;
    private int numberOfSeats;
    private int numberOfDoors;
    private boolean airConditioned;
    private int luggageCapacity;
    private String description;

    private Integer carID;
    private String carBrandModel; 
    

    public CarTypeDTO() {
    }
    
 
    public static class Builder {
        private int carTypeID;
        private String category;
        private String fuelType;
        private String transmissionType;
        private int numberOfSeats;
        private int numberOfDoors;
        private boolean airConditioned;
        private int luggageCapacity;
        private String description;
        private Integer carID;
        private String carBrandModel;

        public Builder setCarTypeID(int carTypeID) {
            this.carTypeID = carTypeID;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Builder setTransmissionType(String transmissionType) {
            this.transmissionType = transmissionType;
            return this;
        }

        public Builder setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public Builder setNumberOfDoors(int numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
            return this;
        }

        public Builder setAirConditioned(boolean airConditioned) {
            this.airConditioned = airConditioned;
            return this;
        }

        public Builder setLuggageCapacity(int luggageCapacity) {
            this.luggageCapacity = luggageCapacity;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCarID(Integer carID) {
            this.carID = carID;
            return this;
        }

        public Builder setCarBrandModel(String carBrandModel) {
            this.carBrandModel = carBrandModel;
            return this;
        }

        public CarTypeDTO build() {
            CarTypeDTO dto = new CarTypeDTO();
            dto.carTypeID = this.carTypeID;
            dto.category = this.category;
            dto.fuelType = this.fuelType;
            dto.transmissionType = this.transmissionType;
            dto.numberOfSeats = this.numberOfSeats;
            dto.numberOfDoors = this.numberOfDoors;
            dto.airConditioned = this.airConditioned;
            dto.luggageCapacity = this.luggageCapacity;
            dto.description = this.description;
            dto.carID = this.carID;
            dto.carBrandModel = this.carBrandModel;
            return dto;
        }
    }
    
    public int getCarTypeID() {
        return carTypeID;
    }

    public void setCarTypeID(int carTypeID) {
        this.carTypeID = carTypeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public boolean isAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(boolean airConditioned) {
        this.airConditioned = airConditioned;
    }

    public int getLuggageCapacity() {
        return luggageCapacity;
    }

    public void setLuggageCapacity(int luggageCapacity) {
        this.luggageCapacity = luggageCapacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public String getCarBrandModel() {
        return carBrandModel;
    }

    public void setCarBrandModel(String carBrandModel) {
        this.carBrandModel = carBrandModel;
    }

    @Override
    public String toString() {
        return "CarTypeDTO{" +
                "carTypeID=" + carTypeID +
                ", category='" + category + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", transmissionType='" + transmissionType + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", numberOfDoors=" + numberOfDoors +
                ", airConditioned=" + airConditioned +
                ", luggageCapacity=" + luggageCapacity +
                ", description='" + description + '\'' +
                ", carID=" + carID +
                ", carBrandModel='" + carBrandModel + '\'' +
                '}';
    }
}