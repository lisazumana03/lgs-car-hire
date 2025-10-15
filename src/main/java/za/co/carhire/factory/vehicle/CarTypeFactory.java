package za.co.carhire.factory.vehicle;

import za.co.carhire.domain.vehicle.*;

public class CarTypeFactory {

    public static CarType createCarType(int carTypeID, VehicleCategory category, FuelType fuelType,
                                        TransmissionType transmissionType, int numberOfSeats,
                                        int numberOfDoors, boolean airConditioned,
                                        int luggageCapacity, String description) {
        return new CarType.Builder()
                .setCarTypeID(carTypeID)
                .setCategory(category)
                .setFuelType(fuelType)
                .setTransmissionType(transmissionType)
                .setNumberOfSeats(numberOfSeats)
                .setNumberOfDoors(numberOfDoors)
                .setAirConditioned(airConditioned)
                .setLuggageCapacity(luggageCapacity)
                .setDescription(description)
                .build();
    }

    public static CarType createEconomy(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.ECONOMY,
                FuelType.PETROL,
                TransmissionType.MANUAL,
                5,
                4,
                true,
                2,
                "Compact and fuel-efficient vehicle perfect for city driving and budget-conscious travelers"
        );
    }

    public static CarType createCompact(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.COMPACT,
                FuelType.PETROL,
                TransmissionType.AUTOMATIC,
                5,
                4,
                true,
                2,
                "Small and nimble vehicle ideal for urban environments and easy parking"
        );
    }

    public static CarType createSedan(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.SEDAN,
                FuelType.PETROL,
                TransmissionType.AUTOMATIC,
                5,
                4,
                true,
                3,
                "Comfortable mid-size sedan with spacious interior and smooth ride for everyday use"
        );
    }

    public static CarType createSUV(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.SUV,
                FuelType.DIESEL,
                TransmissionType.AUTOMATIC,
                7,
                5,
                true,
                5,
                "Spacious SUV with elevated seating, perfect for families and off-road adventures"
        );
    }

    public static CarType createLuxury(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.LUXURY,
                FuelType.PETROL,
                TransmissionType.AUTOMATIC,
                5,
                4,
                true,
                3,
                "Premium luxury vehicle with high-end features, leather interior, and superior comfort"
        );
    }

    public static CarType createSports(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.SPORTS,
                FuelType.PETROL,
                TransmissionType.DUAL_CLUTCH,
                2,
                2,
                true,
                1,
                "High-performance sports car with powerful engine and dynamic handling"
        );
    }

    public static CarType createConvertible(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.CONVERTIBLE,
                FuelType.PETROL,
                TransmissionType.AUTOMATIC,
                4,
                2,
                true,
                2,
                "Stylish convertible with retractable roof for an exhilarating open-air driving experience"
        );
    }

    public static CarType createMinivan(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.MINIVAN,
                FuelType.DIESEL,
                TransmissionType.AUTOMATIC,
                8,
                5,
                true,
                4,
                "Spacious minivan with three rows of seating, ideal for large families and group travel"
        );
    }

    public static CarType createVan(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.VAN,
                FuelType.DIESEL,
                TransmissionType.MANUAL,
                9,
                5,
                false,
                6,
                "Large van for transporting groups or cargo with maximum versatility"
        );
    }

    public static CarType createElectric(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.ELECTRIC,
                FuelType.ELECTRIC,
                TransmissionType.AUTOMATIC,
                5,
                4,
                true,
                3,
                "Fully electric vehicle with zero emissions, quiet operation, and modern technology"
        );
    }

    public static CarType createHybrid(int carTypeID) {
        return createCarType(
                carTypeID,
                VehicleCategory.HYBRID,
                FuelType.HYBRID,
                TransmissionType.CVT,
                5,
                4,
                true,
                3,
                "Hybrid vehicle combining petrol and electric power for exceptional fuel efficiency"
        );
    }

    public static CarType copy(CarType original, int newCarTypeID) {
        if (original == null) {
            throw new IllegalArgumentException("Original CarType cannot be null");
        }

        return new CarType.Builder()
                .copy(original)
                .setCarTypeID(newCarTypeID)
                .build();
    }
}