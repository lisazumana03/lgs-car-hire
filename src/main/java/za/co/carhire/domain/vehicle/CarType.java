package za.co.carhire.domain.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 /

public class CarType {
    private int carTypeID;
    private int carID;
    private String type;
    private String fuelType;

    // Constructor
    public CarType() {}

    public CarType(int carTypeID, int carID, String type, String fuelType) {
        this.carTypeID = carTypeID;
        this.carID = carID;
        this.type = type;
        this.fuelType = fuelType;
    }

    // Getters and Setters

    public int getCarTypeID() {
        return carTypeID;
    }

    public void setCarTypeID(int carTypeID) {
        this.carTypeID = carTypeID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
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

    // Methods from the diagram
    public void addCarDescription(String description) {
        // Implementation for adding car description
        // This might involve updating the type or additional properties
        this.type = description;
    }

    public void updateCarDescription(boolean status) {
        // Implementation for updating car description status
        // This method might need additional parameters or logic
    }

    @Override
    public String toString() {
        return "CarType{" +
                "carTypeID=" + carTypeID +
                ", carID=" + carID +
                ", type='" + type + '\'' +
                ", fuelType='" + fuelType + '\'' +
                '}';
    }
}

