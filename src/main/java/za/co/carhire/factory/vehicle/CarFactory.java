package za.co.carhire.factory.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //
// Updated: 31/08/2025 - Added image URL support

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

public class CarFactory {

    public static Car createBasicCar(int carID, String model, String brand, int year, double rentalPrice) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setAvailability(true)
                .build();
    }

    public static Car createBasicCarWithImage(int carID, String model, String brand, int year,
                                              double rentalPrice, String imageUrl) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageUrl(imageUrl)
                .setAvailability(true)
                .build();
    }

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

    public static Car createCompleteCarWithImage(int carID, String model, String brand, int year,
                                                 boolean availability, double rentalPrice, String imageUrl,
                                                 CarType carType, Insurance insurance, Booking booking) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setAvailability(availability)
                .setRentalPrice(rentalPrice)
                .setImageUrl(imageUrl)
                .setCarType(carType)
                .setInsurance(insurance)
                .setBooking(booking)
                .build();
    }

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

    public static Car createCarWithTypeAndImage(int carID, String model, String brand, int year,
                                                double rentalPrice, String imageUrl, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageUrl(imageUrl)
                .setCarType(carType)
                .setAvailability(true)
                .build();
    }

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

    public static Car createCarWithInsuranceAndImage(int carID, String model, String brand, int year,
                                                     double rentalPrice, String imageUrl,
                                                     CarType carType, Insurance insurance) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageUrl(imageUrl)
                .setCarType(carType)
                .setInsurance(insurance)
                .setAvailability(true)
                .build();
    }

    public static Car createCarCopy(Car originalCar, int newCarID) {
        return new Car.Builder()
                .copy(originalCar)
                .setCarID(newCarID)
                .build();
    }

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

    public static Car createUnavailableCarWithImage(int carID, String model, String brand, int year,
                                                    double rentalPrice, String imageUrl, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageUrl(imageUrl)
                .setCarType(carType)
                .setAvailability(false)
                .build();
    }
}