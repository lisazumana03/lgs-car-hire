package za.co.carhire.domain.vehicle;

/*
Imtiyaaz Waggie - 219374759
Date: 10/05/2025
 */

public class CarType {
    // Primary attributes
    private int carTypeID;
    private String type;
    private String fuelType;

    // Relationship attributes
    private Car car;



    // Private constructor for Builder pattern
    private CarType(Builder builder) {
        this.carTypeID = builder.carTypeID;
        this.type = builder.type;
        this.fuelType = builder.fuelType;
        this.car = builder.car;
    }

    // Methods
    public void addCarDescription(String description) {
        // Implementation
    }

    public void updateCarDescription(boolean status) {
        // Implementation
    }

    // Getters


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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarType{" +
                "carTypeID=" + carTypeID +
                ", type='" + type + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", car=" + car +
                '}';
    }

    // Builder class
    public static class Builder {
        private int carTypeID;
        private String type;
        private String fuelType;
        private Car car;

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

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder copy(CarType carType) {
            this.carTypeID = carType.getCarTypeID();
            this.type = carType.getType();
            this.fuelType = carType.getFuelType();
            this.car = carType.getCar();
            return this;
        }

        public CarType build() {
            return new CarType(this);
        }
    }
}