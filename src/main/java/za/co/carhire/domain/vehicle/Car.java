package za.co.carhire.domain.vehicle;
// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //
public class Car {
    private int carID;
    private String model;
    private String brand;
    private int year;
    private boolean availability;
    private double rentalPrice;
    // Reference to related entities
    private CarType carType;



    // Private constructor for Builder
    private Car(Builder builder) {
        this.carID = builder.carID;
        this.model = builder.model;
        this.brand = builder.brand;
        this.year = builder.year;
        this.availability = builder.availability;
        this.rentalPrice = builder.rentalPrice;
        this.carType = builder.carType;
    }

    public Car(int carID, String model, String brand, int year, boolean availability, double rentalPrice) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.availability = availability;
        this.rentalPrice = rentalPrice;
    }

    // Builder class
    public static class Builder {
        private int carID;
        private String model;
        private String brand;
        private int year;
        private boolean availability;
        private double rentalPrice;
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

        public Builder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    // Getters and Setters
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
    public CarType getCarType() {
        return carType;
    }
    public void setCarType(CarType carType) {
        this.carType = carType;
    }
    // Methods from the diagram
    public boolean checkAvailability() {
        return this.availability;
    }
    public void updateAvailability(boolean status) {
        this.availability = status;
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
                '}';
    }
}