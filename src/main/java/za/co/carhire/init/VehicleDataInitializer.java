package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.service.vehicle.ICarService;
import za.co.carhire.service.vehicle.ICarTypeService;

/*
    Imtiyaaz Waggie 219374759
    Date: 09/10/2025
*/

@Component
@Order(2)
public class VehicleDataInitializer implements CommandLineRunner {

    @Autowired
    private ICarService carService;

    @Autowired
    private ICarTypeService carTypeService;

    @Value("${app.init.create-sample-vehicles:true}")
    private boolean createSampleVehicles;

    @Override
    public void run(String... args) throws Exception {
        if (!createSampleVehicles) {
            System.out.println("Sample vehicle creation is disabled.");
            return;
        }

        // Check if vehicles already exist
        if (!carService.getCars().isEmpty()) {
            System.out.println("Vehicles already exist. Skipping sample data creation.");
            return;
        }

        System.out.println("============================================");
        System.out.println("Creating sample vehicle data...");
        System.out.println("============================================");

        createSampleVehicles();

        System.out.println("Sample vehicle data created successfully!");
        System.out.println("Total Cars: " + carService.getCars().size());
        System.out.println("Total Car Types: " + carTypeService.getCarTypes().size());
        System.out.println("============================================");
    }

    private void createSampleVehicles() {
        // Create Toyota Corolla (Sedan)
        CarType corollaType = new CarType.Builder()
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        corollaType = carTypeService.create(corollaType);

        Car corolla = new Car.Builder()
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(450.0)
                .setImageUrl("https://images.unsplash.com/photo-1590362891991-f776e747a588")
                .setCarType(corollaType)
                .build();
        carService.create(corolla);

        // Create Honda CR-V (SUV)
        CarType crvType = new CarType.Builder()
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();
        crvType = carTypeService.create(crvType);

        Car crv = new Car.Builder()
                .setModel("CR-V")
                .setBrand("Honda")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(650.0)
                .setImageUrl("https://images.unsplash.com/photo-1519641471654-76ce0107ad1b")
                .setCarType(crvType)
                .build();
        carService.create(crv);

        // Create BMW 3 Series (Luxury)
        CarType bmw3SeriesType = new CarType.Builder()
                .setType("Luxury")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        bmw3SeriesType = carTypeService.create(bmw3SeriesType);

        Car bmw3Series = new Car.Builder()
                .setModel("3 Series")
                .setBrand("BMW")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(850.0)
                .setImageUrl("https://images.unsplash.com/photo-1555215695-3004980ad54e")
                .setCarType(bmw3SeriesType)
                .build();
        carService.create(bmw3Series);

        // Create Volkswagen Polo (Economy)
        CarType poloType = new CarType.Builder()
                .setType("Economy")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        poloType = carTypeService.create(poloType);

        Car polo = new Car.Builder()
                .setModel("Polo")
                .setBrand("Volkswagen")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(350.0)
                .setImageUrl("https://images.unsplash.com/photo-1552519507-da3b142c6e3d")
                .setCarType(poloType)
                .build();
        carService.create(polo);

        // Create Tesla Model 3 (Electric)
        CarType teslaModel3Type = new CarType.Builder()
                .setType("Electric")
                .setFuelType("Electric")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        teslaModel3Type = carTypeService.create(teslaModel3Type);

        Car teslaModel3 = new Car.Builder()
                .setModel("Model 3")
                .setBrand("Tesla")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(900.0)
                .setImageUrl("https://images.unsplash.com/photo-1560958089-b8a1929cea89")
                .setCarType(teslaModel3Type)
                .build();
        carService.create(teslaModel3);

        // Create Toyota Prius (Hybrid)
        CarType priusType = new CarType.Builder()
                .setType("Hybrid")
                .setFuelType("Hybrid")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        priusType = carTypeService.create(priusType);

        Car prius = new Car.Builder()
                .setModel("Prius")
                .setBrand("Toyota")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(550.0)
                .setImageUrl("https://images.unsplash.com/photo-1549317661-bd32c8ce0db2")
                .setCarType(priusType)
                .build();
        carService.create(prius);

        // Create Mazda MX-5 (Sports/Convertible)
        CarType mx5Type = new CarType.Builder()
                .setType("Convertible")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(2)
                .build();
        mx5Type = carTypeService.create(mx5Type);

        Car mx5 = new Car.Builder()
                .setModel("MX-5")
                .setBrand("Mazda")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(700.0)
                .setImageUrl("https://images.unsplash.com/photo-1503376780353-7e6692767b70")
                .setCarType(mx5Type)
                .build();
        carService.create(mx5);

        // Create Chrysler Pacifica (Minivan)
        CarType pacificaType = new CarType.Builder()
                .setType("Minivan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(8)
                .build();
        pacificaType = carTypeService.create(pacificaType);

        Car pacifica = new Car.Builder()
                .setModel("Pacifica")
                .setBrand("Chrysler")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(750.0)
                .setImageUrl("https://images.unsplash.com/photo-1464219789935-c2d9d9aba644")
                .setCarType(pacificaType)
                .build();
        carService.create(pacifica);

        // Create Mercedes-Benz C-Class (Luxury) - Currently unavailable
        CarType mercCClassType = new CarType.Builder()
                .setType("Luxury")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        mercCClassType = carTypeService.create(mercCClassType);

        Car mercCClass = new Car.Builder()
                .setModel("C-Class")
                .setBrand("Mercedes-Benz")
                .setYear(2024)
                .setAvailability(false)
                .setRentalPrice(950.0)
                .setImageUrl("https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8")
                .setCarType(mercCClassType)
                .build();
        carService.create(mercCClass);

        // Create Ford Ranger (Pickup/SUV)
        CarType rangerType = new CarType.Builder()
                .setType("SUV")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
        rangerType = carTypeService.create(rangerType);

        Car ranger = new Car.Builder()
                .setModel("Ranger")
                .setBrand("Ford")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(800.0)
                .setImageUrl("https://images.unsplash.com/photo-1533473359331-0135ef1b58bf")
                .setCarType(rangerType)
                .build();
        carService.create(ranger);

        System.out.println("✓ Created 10 sample vehicles with their types");
    }
}
