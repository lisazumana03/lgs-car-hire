package za.co.carhire.domain.vehicle;

public class CarType {
    private int carTypeID;
    private Car car;
    private String type;
    private String fuelType;
    private int numberOfWheels;
    private int numberOfSeats;


    // Constructor with all fields
    public CarType(int carTypeID, Car car, String type, String fuelType, int numberOfWheels, int numberOfSeats) {
        this.carTypeID = carTypeID;
        this.car = car;
        this.type = type;
        this.fuelType = fuelType;
        this.numberOfWheels = numberOfWheels;
        this.numberOfSeats = numberOfSeats;
    }

    // Constructor using Builder
    private CarType(Builder builder) {
        this.carTypeID = builder.carTypeID;
        this.car = builder.car;
        this.type = builder.type;
        this.fuelType = builder.fuelType;
        this.numberOfWheels = builder.numberOfWheels;
        this.numberOfSeats = builder.numberOfSeats;
    }

    // Builder class
    public static class Builder {
        private int carTypeID;
        private Car car;
        private String type;
        private String fuelType;
        private int numberOfWheels;
        private int numberOfSeats;

        public Builder setCarTypeID(int carTypeID) {
            this.carTypeID = carTypeID;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
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

        public CarType build() {
            return new CarType(this);
        }
    }

    // Getters and Setters


    public int getCarTypeID() {
        return carTypeID;
    }

    public void setCarTypeID(int carTypeID) {
        this.carTypeID = carTypeID;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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

    // Methods from diagram
    public void addCarDescription(String description) {
        // Implementation for adding car description
    }

    public void updateCarDescription(String description, boolean update) {
        // Implementation for updating car description
    }

    @Override
    public String toString() {
        return "CarType{" +
                "carTypeID=" + carTypeID +
                ", car=" + (car != null ? car.getCarID() : "null") +
                ", type='" + type + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", numberOfWheels=" + numberOfWheels +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}