package za.co.carhire.domain.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //
// Updated: 31/08/2025 - Added image URL support
// Updated: 15/10/2025 - Refactored with proper car rental fields

import jakarta.persistence.*;
import za.co.carhire.domain.reservation.Location;
import java.io.Serializable;

@Entity
@Table(name = "car")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carid")
    private int carID;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "year")
    private int year;

    @Column(name = "license_plate", unique = true)
    private String licensePlate;

    @Column(name = "vin", unique = true)
    private String vin; // Vehicle Identification Number

    @Column(name = "color")
    private String color;

    @Column(name = "mileage")
    private int mileage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CarStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "`condition`")
    private CarCondition condition;

    @ManyToOne
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_typeid")
    private CarType carType;

    public Car() {
    }

    private Car(Builder builder) {
        this.carID = builder.carID;
        this.model = builder.model;
        this.brand = builder.brand;
        this.year = builder.year;
        this.licensePlate = builder.licensePlate;
        this.vin = builder.vin;
        this.color = builder.color;
        this.mileage = builder.mileage;
        this.status = builder.status;
        this.condition = builder.condition;
        this.currentLocation = builder.currentLocation;
        this.imageData = builder.imageData;
        this.imageName = builder.imageName;
        this.imageType = builder.imageType;
        this.carType = builder.carType;
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

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public CarCondition getCondition() {
        return condition;
    }

    public void setCondition(CarCondition condition) {
        this.condition = condition;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carID=" + carID +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                ", licensePlate='" + licensePlate + '\'' +
                ", vin='" + vin + '\'' +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", status=" + status +
                ", condition=" + condition +
                ", currentLocation=" + (currentLocation != null ? currentLocation.getLocationID() : "null") +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", hasImage=" + (imageData != null && imageData.length > 0) +
                ", carType=" + (carType != null ? carType.getCarTypeID() : "null") +
                '}';
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
        private CarStatus status = CarStatus.AVAILABLE;
        private CarCondition condition = CarCondition.GOOD;
        private Location currentLocation;
        private byte[] imageData;
        private String imageName;
        private String imageType;
        private CarType carType;

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

        public Builder setStatus(CarStatus status) {
            this.status = status;
            return this;
        }

        public Builder setCondition(CarCondition condition) {
            this.condition = condition;
            return this;
        }

        public Builder setCurrentLocation(Location currentLocation) {
            this.currentLocation = currentLocation;
            return this;
        }

        public Builder setImageData(byte[] imageData) {
            this.imageData = imageData;
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

        public Builder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public Builder copy(Car car) {
            this.carID = car.getCarID();
            this.model = car.getModel();
            this.brand = car.getBrand();
            this.year = car.getYear();
            this.licensePlate = car.getLicensePlate();
            this.vin = car.getVin();
            this.color = car.getColor();
            this.mileage = car.getMileage();
            this.status = car.getStatus();
            this.condition = car.getCondition();
            this.currentLocation = car.getCurrentLocation();
            this.imageData = car.getImageData();
            this.imageName = car.getImageName();
            this.imageType = car.getImageType();
            this.carType = car.getCarType();
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}