package za.co.carhire.domain.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //
// Updated: 31/08/2025 - Added image URL support

import jakarta.persistence.*;
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

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "rental_price")
    private double rentalPrice;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
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
        this.availability = builder.availability;
        this.rentalPrice = builder.rentalPrice;
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
                ", availability=" + availability +
                ", rentalPrice=" + rentalPrice +
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
        private boolean availability;
        private double rentalPrice;
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
            this.availability = car.isAvailability();
            this.rentalPrice = car.getRentalPrice();
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