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

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private VehicleCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type", nullable = false)
    private TransmissionType transmissionType;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @Column(name = "number_of_doors")
    private int numberOfDoors;

    @Column(name = "air_conditioned", nullable = false)
    private boolean airConditioned;

    @Column(name = "luggage_capacity")
    private int luggageCapacity;

    @Column(name = "description", length = 500)
    private String description;

    public CarType() {
    }

    private CarType(Builder builder) {
        this.carTypeID = builder.carTypeID;
        this.category = builder.category;
        this.fuelType = builder.fuelType;
        this.transmissionType = builder.transmissionType;
        this.numberOfSeats = builder.numberOfSeats;
        this.numberOfDoors = builder.numberOfDoors;
        this.airConditioned = builder.airConditioned;
        this.luggageCapacity = builder.luggageCapacity;
        this.description = builder.description;
    }

    public static class Builder {
        private int carTypeID;
        private VehicleCategory category;
        private FuelType fuelType;
        private TransmissionType transmissionType;
        private int numberOfSeats;
        private int numberOfDoors;
        private boolean airConditioned;
        private int luggageCapacity;
        private String description;

        public Builder setCarTypeID(int carTypeID) {
            this.carTypeID = carTypeID;
            return this;
        }

        public Builder setCategory(VehicleCategory category) {
            this.category = category;
            return this;
        }

        public Builder setFuelType(FuelType fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Builder setTransmissionType(TransmissionType transmissionType) {
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

        public Builder copy(CarType carType) {
            this.carTypeID = carType.getCarTypeID();
            this.category = carType.getCategory();
            this.fuelType = carType.getFuelType();
            this.transmissionType = carType.getTransmissionType();
            this.numberOfSeats = carType.getNumberOfSeats();
            this.numberOfDoors = carType.getNumberOfDoors();
            this.airConditioned = carType.isAirConditioned();
            this.luggageCapacity = carType.getLuggageCapacity();
            this.description = carType.getDescription();
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

    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(VehicleCategory category) {
        this.category = category;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
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

    @Override
    public String toString() {
        return "CarType{" +
                "carTypeID=" + carTypeID +
                ", category=" + category +
                ", fuelType=" + fuelType +
                ", transmissionType=" + transmissionType +
                ", numberOfSeats=" + numberOfSeats +
                ", numberOfDoors=" + numberOfDoors +
                ", airConditioned=" + airConditioned +
                ", luggageCapacity=" + luggageCapacity +
                ", description='" + description + '\'' +
                '}';
    }
}