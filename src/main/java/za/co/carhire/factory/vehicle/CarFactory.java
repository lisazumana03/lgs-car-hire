package za.co.carhire.factory.vehicle;
// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //

import za.co.carhire.domain.Insurance;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

/**
 * Factory class for creating Car objects
 * Provides various methods to create Car instances with different configurations
 */
public class CarFactory {

    /**
     * Creates a basic car with essential information
     */
    public static Car createBasicCar(int carID, String model, String brand, int year, double rentalPrice) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setAvailability(true) // Default to available
                .build();
    }

    /**
     * Creates a car with all details including type, insurance, and booking
     */
    public static Car createCompleteCar(int carID, String model, String brand, int year,
                                        boolean availability, double rentalPrice,
                                        CarType carType, Insurance insurance, Booking booking) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setAvailability(availability)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .setInsurance(insurance)
                .setBooking(booking)
                .build();
    }

    /**
     * Creates a car with type but no insurance or booking
     */
    public static Car createCarWithType(int carID, String model, String brand, int year,
                                        double rentalPrice, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .setAvailability(true)
                .build();
    }

    /**
     * Creates a car with insurance but no booking
     */
    public static Car createCarWithInsurance(int carID, String model, String brand, int year,
                                             double rentalPrice, CarType carType, Insurance insurance) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .setInsurance(insurance)
                .setAvailability(true)
                .build();
    }

    /**
     * Creates a copy of an existing car with a new car ID
     */
    public static Car createCarCopy(Car originalCar, int newCarID) {
        return new Car.Builder()
                .copy(originalCar)
                .setCarID(newCarID)
                .build();
    }

    /**
     * Creates an unavailable car (for maintenance, etc.)
     */
    public static Car createUnavailableCar(int carID, String model, String brand, int year,
                                           double rentalPrice, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .setAvailability(false)
                .build();
    }

}