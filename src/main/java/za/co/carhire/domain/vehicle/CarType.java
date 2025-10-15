package za.co.carhire.domain.vehicle;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car_type")
public class CarType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_typeid")
    private int carTypeID;

    @Column(name = "type")
    private String type;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "number_of_wheels")
    private int numberOfWheels;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    public CarType() {
    }

    public CarType(int carTypeID, String type, String fuelType, int numberOfWheels, int numberOfSeats) {
        this.carTypeID = carTypeID;
        this.type = type;
        this.fuelType = fuelType;
        this.numberOfWheels = numberOfWheels;
        this.numberOfSeats = numberOfSeats;
    }

    private CarType(Builder builder) {
        this.carTypeID = builder.carTypeID;
        this.type = builder.type;
        this.fuelType = builder.fuelType;
        this.numberOfWheels = builder.numberOfWheels;
        this.numberOfSeats = builder.numberOfSeats;
    }

    public static class Builder {
        private int carTypeID;
        private String type;
        private String fuelType;
        private int numberOfWheels;
        private int numberOfSeats;

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

        public Builder copy(CarType carType) {
            this.carTypeID = carType.getCarTypeID();
            this.type = carType.getType();
            this.fuelType = carType.getFuelType();
            this.numberOfWheels = carType.getNumberOfWheels();
            this.numberOfSeats = carType.getNumberOfSeats();
            return this;
        }

        public CarType build() {
            return new CarType(this);
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

    @Override
    public String toString() {
        return "CarType{" +
                "carTypeID=" + carTypeID +
                ", type='" + type + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", numberOfWheels=" + numberOfWheels +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}