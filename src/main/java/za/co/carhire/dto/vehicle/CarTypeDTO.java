package za.co.carhire.dto.vehicle;

import java.io.Serializable;

/**
 * Data Transfer Object for CarType entity
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 */
public class CarTypeDTO implements Serializable {
    
    private int carTypeID;
    private String type;
    private String fuelType;
    private int numberOfWheels;
    private int numberOfSeats;
    
    private Integer carID;
    private String carBrandModel; 
    

    public CarTypeDTO() {
    }
    

    public CarTypeDTO(int carTypeID, String type, String fuelType, 
                      int numberOfWheels, int numberOfSeats) {
        this.carTypeID = carTypeID;
        this.type = type;
        this.fuelType = fuelType;
        this.numberOfWheels = numberOfWheels;
        this.numberOfSeats = numberOfSeats;
    }
    

    public CarTypeDTO(int carTypeID, String type, String fuelType, 
                      int numberOfWheels, int numberOfSeats,
                      Integer carID, String carBrandModel) {
        this.carTypeID = carTypeID;
        this.type = type;
        this.fuelType = fuelType;
        this.numberOfWheels = numberOfWheels;
        this.numberOfSeats = numberOfSeats;
        this.carID = carID;
        this.carBrandModel = carBrandModel;
    }
    
 
    public static class Builder {
        private int carTypeID;
        private String type;
        private String fuelType;
        private int numberOfWheels;
        private int numberOfSeats;
        private Integer carID;
        private String carBrandModel;
        
        public Builder setCarTypeID(int carTypeID) {
            this.carTypeID = carTypeID;
            return this;
        }
        
        public Builder setType(String type) {
            this.type = type;
            return this;
        }
        
        public Builder setFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }
        
        public Builder setNumberOfWheels(int numberOfWheels) {
            this.numberOfWheels = numberOfWheels;
            return this;
        }
        
        public Builder setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
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
            dto.type = this.type;
            dto.fuelType = this.fuelType;
            dto.numberOfWheels = this.numberOfWheels;
            dto.numberOfSeats = this.numberOfSeats;
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    
    public int getNumberOfWheels() {
        return numberOfWheels;
    }
    
    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }
    
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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
                ", type='" + type + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", numberOfWheels=" + numberOfWheels +
                ", numberOfSeats=" + numberOfSeats +
                ", carID=" + carID +
                ", carBrandModel='" + carBrandModel + '\'' +
                '}';
    }
}