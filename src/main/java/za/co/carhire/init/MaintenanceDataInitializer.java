package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.reservation.MaintenanceStatus;
import za.co.carhire.domain.reservation.MaintenanceType;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.factory.reservation.MaintenanceFactory;
import za.co.carhire.repository.reservation.IMaintenanceRepository;
import za.co.carhire.service.vehicle.ICarService;

import java.util.*;

@Component
@Order(4)  // Run after Users, Cars, Locations
public class MaintenanceDataInitializer implements CommandLineRunner {

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Autowired
    private ICarService carService;

    @Value("${app.database.init.maintenance.enabled:true}")
    private boolean initMaintenanceEnabled;

    @Value("${app.database.init.maintenance.clear-existing:false}")
    private boolean clearExistingMaintenance;

    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initMaintenanceEnabled) {
            System.out.println("Maintenance data initialization is disabled. Set app.database.init.maintenance.enabled=true to enable.");
            return;
        }

        List<Maintenance> existingMaintenance = maintenanceRepository.findAll();

        if (!existingMaintenance.isEmpty() && !clearExistingMaintenance) {
            System.out.println("Database already contains " + existingMaintenance.size() + " maintenance records.");
            System.out.println("Skipping maintenance initialization. Set app.database.init.maintenance.clear-existing=true to override.");
            return;
        }

        if (clearExistingMaintenance && !existingMaintenance.isEmpty()) {
            System.out.println("Clearing existing maintenance records as requested...");
            maintenanceRepository.deleteAll();
            System.out.println("Existing maintenance records cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing maintenance data...");
        System.out.println("============================================");

        createSampleMaintenanceRecords();

        System.out.println("============================================");
        System.out.println("Maintenance initialization completed successfully!");
        System.out.println("--------------------------------------------");
        displayMaintenanceSummary();
        System.out.println("============================================");
    }

    private void createSampleMaintenanceRecords() {
        Set<Car> cars = carService.getCars();

        if (cars.isEmpty()) {
            System.out.println("No cars found. Skipping maintenance creation.");
            return;
        }

        System.out.println("Creating sample maintenance records for cars...");

        int maintenanceCreated = 0;

        // Create maintenance records for a subset of cars
        List<Car> carList = new ArrayList<>(cars);
        int carsToService = Math.min(20, carList.size()); // Service up to 20 cars

        for (int i = 0; i < carsToService; i++) {
            Car car = carList.get(i);

            // Create 1-3 maintenance records per car
            int recordCount = RANDOM.nextInt(3) + 1;

            for (int j = 0; j < recordCount; j++) {
                Maintenance maintenance = createRandomMaintenance(car, j);
                if (maintenance != null) {
                    maintenanceRepository.save(maintenance);
                    maintenanceCreated++;
                    System.out.println("✓ Created " + maintenance.getServiceType() +
                                     " maintenance for " + car.getBrand() + " " + car.getModel() +
                                     " (Status: " + maintenance.getStatus() + ")");
                }
            }
        }

        System.out.println("\n" + maintenanceCreated + " sample maintenance records created.");
    }

    private Maintenance createRandomMaintenance(Car car, int recordIndex) {
        // Generate date in the past (0-365 days ago)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -(RANDOM.nextInt(365)));
        Date maintenanceDate = calendar.getTime();

        // Service type based on record index and randomness
        MaintenanceType[] types = MaintenanceType.values();
        MaintenanceType serviceType = types[RANDOM.nextInt(types.length)];

        // Generate description based on service type
        String description = generateDescription(serviceType);

        // Generate cost based on service type
        double cost = generateCost(serviceType);

        // Mileage at service (current mileage minus some random amount)
        int mileageAtService = Math.max(0, car.getMileage() - RANDOM.nextInt(10000));

        // Next service date (30-180 days from maintenance date)
        Calendar nextServiceCal = Calendar.getInstance();
        nextServiceCal.setTime(maintenanceDate);
        nextServiceCal.add(Calendar.DAY_OF_YEAR, RANDOM.nextInt(151) + 30);
        Date nextServiceDate = nextServiceCal.getTime();

        // Next service mileage
        int nextServiceMileage = mileageAtService + (RANDOM.nextInt(5000) + 5000);

        // Mechanic name
        String mechanicName = getRandomMechanic();

        // Status (90% completed, 10% pending/in-progress)
        MaintenanceStatus status = RANDOM.nextInt(10) < 9 ?
                MaintenanceStatus.COMPLETED :
                (RANDOM.nextBoolean() ? MaintenanceStatus.PENDING : MaintenanceStatus.IN_PROGRESS);

        // Notes
        String notes = generateNotes(serviceType, status);

        return MaintenanceFactory.buildMaintenance(
                0, // ID will be auto-generated
                maintenanceDate,
                serviceType,
                description,
                cost,
                mileageAtService,
                nextServiceDate,
                nextServiceMileage,
                mechanicName,
                status,
                notes,
                car
        );
    }

    private String generateDescription(MaintenanceType type) {
        Map<MaintenanceType, String[]> descriptions = new HashMap<>();

        descriptions.put(MaintenanceType.ROUTINE, new String[]{
            "Regular engine oil and filter change",
            "Scheduled maintenance service",
            "Routine service including fluid checks and replacements"
        });

        descriptions.put(MaintenanceType.TIRE_SERVICE, new String[]{
            "Standard tire rotation and balancing",
            "Tire rotation with alignment check",
            "Complete tire service including rotation and pressure check"
        });

        descriptions.put(MaintenanceType.BRAKE_SERVICE, new String[]{
            "Front brake pad replacement",
            "Complete brake system service",
            "Brake pad and rotor replacement"
        });

        descriptions.put(MaintenanceType.ENGINE_SERVICE, new String[]{
            "Complete engine service and diagnostics",
            "Engine performance optimization",
            "Full engine service and spark plug replacement"
        });

        descriptions.put(MaintenanceType.TRANSMISSION, new String[]{
            "Transmission fluid change",
            "Complete transmission service and inspection",
            "Transmission flush and filter replacement"
        });

        descriptions.put(MaintenanceType.ELECTRICAL, new String[]{
            "Electrical system diagnostics and repair",
            "Battery replacement and electrical system check",
            "Complete electrical system service"
        });

        descriptions.put(MaintenanceType.INSPECTION, new String[]{
            "Annual vehicle safety inspection",
            "Comprehensive vehicle inspection",
            "Pre-rental detailed inspection"
        });

        descriptions.put(MaintenanceType.DETAILING, new String[]{
            "Full interior and exterior detailing",
            "Deep cleaning and detailing service",
            "Premium detailing package"
        });

        descriptions.put(MaintenanceType.REPAIR, new String[]{
            "General repair service",
            "Component replacement and repair",
            "Mechanical repair work"
        });

        descriptions.put(MaintenanceType.BODYWORK, new String[]{
            "Body damage repair and paint work",
            "Dent removal and panel repair",
            "Exterior body restoration"
        });

        descriptions.put(MaintenanceType.ACCIDENT_REPAIR, new String[]{
            "Collision damage repair",
            "Accident restoration service",
            "Complete accident repair and restoration"
        });

        descriptions.put(MaintenanceType.RECALL, new String[]{
            "Manufacturer recall service",
            "Factory recall maintenance",
            "Recall-related component replacement"
        });

        descriptions.put(MaintenanceType.DIAGNOSTIC, new String[]{
            "Complete vehicle diagnostic scan",
            "System diagnostic and troubleshooting",
            "Electronic diagnostic service"
        });

        descriptions.put(MaintenanceType.OTHER, new String[]{
            "General maintenance and minor repairs",
            "Miscellaneous service and adjustments",
            "Various maintenance tasks completed"
        });

        String[] options = descriptions.getOrDefault(type, new String[]{"Standard maintenance service"});
        return options[RANDOM.nextInt(options.length)];
    }

    private double generateCost(MaintenanceType type) {
        Map<MaintenanceType, double[]> costRanges = new HashMap<>();
        costRanges.put(MaintenanceType.ROUTINE, new double[]{400, 800});
        costRanges.put(MaintenanceType.TIRE_SERVICE, new double[]{500, 1200});
        costRanges.put(MaintenanceType.BRAKE_SERVICE, new double[]{1500, 3500});
        costRanges.put(MaintenanceType.ENGINE_SERVICE, new double[]{1500, 3000});
        costRanges.put(MaintenanceType.TRANSMISSION, new double[]{2000, 4500});
        costRanges.put(MaintenanceType.ELECTRICAL, new double[]{800, 2000});
        costRanges.put(MaintenanceType.INSPECTION, new double[]{300, 800});
        costRanges.put(MaintenanceType.DETAILING, new double[]{600, 1200});
        costRanges.put(MaintenanceType.REPAIR, new double[]{800, 2500});
        costRanges.put(MaintenanceType.BODYWORK, new double[]{2000, 5000});
        costRanges.put(MaintenanceType.ACCIDENT_REPAIR, new double[]{3000, 10000});
        costRanges.put(MaintenanceType.RECALL, new double[]{0, 500});
        costRanges.put(MaintenanceType.DIAGNOSTIC, new double[]{500, 1500});
        costRanges.put(MaintenanceType.OTHER, new double[]{500, 1500});

        double[] range = costRanges.getOrDefault(type, new double[]{500, 1000});
        return range[0] + (RANDOM.nextDouble() * (range[1] - range[0]));
    }

    private String getRandomMechanic() {
        String[] mechanics = {
            "John Mkhize", "Sarah van der Merwe", "Thabo Nkosi", "Maria Fernandez",
            "David Johnson", "Lerato Molefe", "Peter Smith", "Nomusa Dlamini",
            "Chris Botha", "Sipho Radebe", "Amanda Brown", "Mandla Zungu"
        };
        return mechanics[RANDOM.nextInt(mechanics.length)];
    }

    private String generateNotes(MaintenanceType type, MaintenanceStatus status) {
        if (status == MaintenanceStatus.COMPLETED) {
            String[] completedNotes = {
                "All work completed successfully. Vehicle in excellent condition.",
                "Service completed as scheduled. No additional issues found.",
                "Maintenance performed without complications. Ready for rental.",
                "Work completed within estimated timeframe. All systems checked.",
                "Service finished. Vehicle tested and approved for operation."
            };
            return completedNotes[RANDOM.nextInt(completedNotes.length)];
        } else if (status == MaintenanceStatus.IN_PROGRESS) {
            return "Service in progress. Expected completion within 24 hours.";
        } else {
            return "Service scheduled. Awaiting parts and mechanic availability.";
        }
    }

    private void displayMaintenanceSummary() {
        List<Maintenance> allMaintenance = maintenanceRepository.findAll();

        long completed = allMaintenance.stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.COMPLETED).count();
        long inProgress = allMaintenance.stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.IN_PROGRESS).count();
        long pending = allMaintenance.stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.PENDING).count();

        double totalCost = allMaintenance.stream()
                .mapToDouble(Maintenance::getCost)
                .sum();

        System.out.println("Maintenance Summary:");
        System.out.println("- Total Maintenance Records: " + allMaintenance.size());
        System.out.println("- Completed: " + completed);
        System.out.println("- In Progress: " + inProgress);
        System.out.println("- Pending: " + pending);
        System.out.println("- Total Maintenance Cost: R" + String.format("%.2f", totalCost));

        // Count by service type
        Map<MaintenanceType, Long> countByType = new HashMap<>();
        for (Maintenance m : allMaintenance) {
            countByType.merge(m.getServiceType(), 1L, Long::sum);
        }

        System.out.println("\nMaintenance by Type:");
        countByType.forEach((type, count) -> {
            System.out.println("- " + type + ": " + count + " records");
        });
    }
}
