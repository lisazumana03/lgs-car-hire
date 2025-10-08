package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.reservation.IInsuranceService;
import za.co.carhire.service.vehicle.ICarService;

import java.text.SimpleDateFormat;
import java.util.*;

/*
    Imtiyaaz Waggie 219374759
    Date: 09/10/2025
*/

@Component
@Order(3) // Run after VehicleDataInitializer (Order 2)
public class InsuranceDataInitializer implements CommandLineRunner {

    @Autowired
    private IInsuranceService insuranceService;

    @Autowired
    private ICarService carService;

    @Value("${app.init.create-sample-insurance:true}")
    private boolean createSampleInsurance;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void run(String... args) throws Exception {
        if (!createSampleInsurance) {
            System.out.println("Sample insurance creation is disabled.");
            return;
        }

        // Check if insurance already exists
        if (!insuranceService.getAll().isEmpty()) {
            System.out.println("Insurance data already exists. Skipping sample data creation.");
            return;
        }

        System.out.println("============================================");
        System.out.println("Creating sample insurance data...");
        System.out.println("============================================");

        createSampleInsurance();

        System.out.println("Sample insurance data created successfully!");
        System.out.println("Total Insurance Policies: " + insuranceService.getAll().size());
        System.out.println("============================================");
    }

    private void createSampleInsurance() throws Exception {
        Set<Car> carSet = carService.getCars();
        List<Car> cars = new ArrayList<>(carSet);

        if (cars.isEmpty()) {
            System.out.println("No cars available to insure. Please run VehicleDataInitializer first.");
            return;
        }

        // Create insurance for first 7 cars (leaving some uninsured for testing)
        int insuranceCount = Math.min(7, cars.size());

        for (int i = 0; i < insuranceCount; i++) {
            Car car = cars.get(i);
            Insurance insurance = createInsuranceForCar(i + 1, car);
            Insurance createdInsurance = insuranceService.create(insurance);

            // Update car to link to insurance
            car.setInsurance(createdInsurance);
            carService.update(car);

            System.out.println("✓ Created insurance for " + car.getBrand() + " " + car.getModel());
        }

        System.out.println("✓ Created " + insuranceCount + " insurance policies");
    }

    private Insurance createInsuranceForCar(int index, Car car) throws Exception {
        // Different providers and statuses for variety
        String[] providers = {
            "Discovery Insure",
            "OUTsurance",
            "Santam",
            "MiWay",
            "King Price",
            "Budget Insurance",
            "First for Women"
        };

        String[] statuses = {
            "ACTIVE",
            "ACTIVE",
            "ACTIVE",
            "ACTIVE",
            "ACTIVE",
            "PENDING",
            "ACTIVE"
        };

        String[] mechanics = {
            "John's Auto Service",
            "City Motors",
            "Premium Car Care",
            "Quick Fix Garage",
            "Elite Auto Repairs",
            "Budget Mechanics",
            "Fast Lane Service"
        };

        // Calculate costs based on car rental price (typically 10-15% of rental price per month)
        double insuranceCost = car.getRentalPrice() * 0.12 * 30; // 12% of daily rental * 30 days
        insuranceCost = Math.round(insuranceCost * 100.0) / 100.0; // Round to 2 decimals

        // Start dates vary
        Date startDate = dateFormat.parse("2025-0" + ((index % 9) + 1) + "-15");

        long policyNumber = 1000000000L + (index * 111111);

        return new Insurance.Builder()
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(insuranceCost)
                .setInsuranceProvider(providers[index % providers.length])
                .setPolicyNumber(policyNumber)
                .setStatus(statuses[index % statuses.length])
                .setMechanic(mechanics[index % mechanics.length])
                .setCar(car)
                .build();
    }
}
