package za.co.carhire.domain;

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


    // Constructors
    public Car() {}

    public Car(int carID, String model, String brand, int year, boolean availability, double rentalPrice) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.availability = availability;
        this.rentalPrice = rentalPrice;
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

