package za.co.carhire.factory.vehicle;
// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //


import za.co.carhire.domain.vehicle.CarType;


public class CarTypeFactory {


    public static CarType createCarType(int carTypeID, String type, String fuelType,
                                        int numberOfWheels, int numberOfSeats) {
        return new CarType.Builder()
                .setCarTypeID(carTypeID)
                .setType(type)
                .setFuelType(fuelType)
                .setNumberOfWheels(numberOfWheels)
                .setNumberOfSeats(numberOfSeats)
                .build();
    }



    public static CarType createSedan(int carTypeID) {
        return createCarType(carTypeID, "Sedan", "Petrol", 4, 5);
    }


    public static CarType createSUV(int carTypeID) {
        return createCarType(carTypeID, "SUV", "Petrol", 4, 7);
    }


    public static CarType createLuxury(int carTypeID) {
        return createCarType(carTypeID, "Luxury", "Petrol", 4, 5);
    }


    public static CarType createEconomy(int carTypeID) {
        return createCarType(carTypeID, "Economy", "Petrol", 4, 5);
    }


    public static CarType createSports(int carTypeID) {
        return createCarType(carTypeID, "Sports", "Petrol", 4, 2);
    }


    public static CarType createConvertible(int carTypeID) {
        return createCarType(carTypeID, "Convertible", "Petrol", 4, 2);
    }


    public static CarType createMinivan(int carTypeID) {
        return createCarType(carTypeID, "Minivan", "Petrol", 4, 8);
    }


    public static CarType createElectric(int carTypeID) {
        return createCarType(carTypeID, "Electric", "Electric", 4, 5);
    }


    public static CarType createHybrid(int carTypeID) {
        return createCarType(carTypeID, "Hybrid", "Hybrid", 4, 5);
    }


    public static CarType createMotorcycle(int carTypeID) {
        return createCarType(carTypeID, "Motorcycle", "Petrol", 2, 2);
    }


    public static CarType copy(CarType original, int newCarTypeID) {
        if (original == null) {
            throw new IllegalArgumentException("Original CarType cannot be null");
        }

        return new CarType.Builder()
                .setCarTypeID(newCarTypeID)
                .setCar(original.getCar())
                .setType(original.getType())
                .setFuelType(original.getFuelType())
                .setNumberOfWheels(original.getNumberOfWheels())
                .setNumberOfSeats(original.getNumberOfSeats())
                .build();
    }
}