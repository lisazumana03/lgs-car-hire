package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.CoverageType;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.repository.reservation.IInsuranceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(4)
public class InsuranceDataInitializer implements CommandLineRunner {

    @Autowired
    private IInsuranceRepository insuranceRepository;

    @Value("${app.database.init.insurance.enabled:true}")
    private boolean initEnabled;

    @Value("${app.database.init.insurance.clear-existing:false}")
    private boolean clearExisting;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initEnabled) {
            System.out.println("Insurance initialization is disabled. Set app.database.init.insurance.enabled=true to enable.");
            return;
        }

        List<Insurance> existingInsurance = insuranceRepository.findAll();

        if (!existingInsurance.isEmpty() && !clearExisting) {
            System.out.println("Database already contains " + existingInsurance.size() + " insurance policies.");
            System.out.println("Skipping insurance initialization. Set app.database.init.insurance.clear-existing=true to override.");
            return;
        }

        if (clearExisting && !existingInsurance.isEmpty()) {
            System.out.println("Clearing existing insurance policies as requested...");
            insuranceRepository.deleteAll();
            System.out.println("Existing insurance policies cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing insurance policy data...");
        System.out.println("============================================");

        createInsurancePolicies();

        System.out.println("============================================");
        System.out.println("Insurance initialization completed successfully!");
        System.out.println("--------------------------------------------");
        System.out.println("Created " + insuranceRepository.count() + " insurance policies");
        System.out.println("--------------------------------------------");
        displayInsuranceSummary();
        System.out.println("============================================");
    }

    private void createInsurancePolicies() {
        List<Insurance> policies = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearLater = now.plusYears(1);
        LocalDateTime sixMonthsLater = now.plusMonths(6);
        LocalDateTime twoYearsLater = now.plusYears(2);

        // Basic Coverage Policies
        policies.add(createPolicy(
            "Santam",
            CoverageType.BASIC,
            150.00,
            5000.00,
            "POL-BASIC-001",
            now,
            oneYearLater,
            true
        ));

        policies.add(createPolicy(
            "Outsurance",
            CoverageType.BASIC,
            120.00,
            7500.00,
            "POL-BASIC-002",
            now,
            oneYearLater,
            true
        ));

        policies.add(createPolicy(
            "Budget Insurance",
            CoverageType.BASIC,
            100.00,
            10000.00,
            "POL-BASIC-003",
            now,
            oneYearLater,
            true
        ));

        // Premium Coverage Policies
        policies.add(createPolicy(
            "Old Mutual",
            CoverageType.PREMIUM,
            300.00,
            2000.00,
            "POL-PREM-001",
            now,
            oneYearLater,
            true
        ));

        policies.add(createPolicy(
            "Discovery Insure",
            CoverageType.PREMIUM,
            350.00,
            1500.00,
            "POL-PREM-002",
            now,
            twoYearsLater,
            true
        ));

        policies.add(createPolicy(
            "Momentum",
            CoverageType.PREMIUM,
            280.00,
            2500.00,
            "POL-PREM-003",
            now,
            oneYearLater,
            true
        ));

        policies.add(createPolicy(
            "MiWay",
            CoverageType.PREMIUM,
            320.00,
            2000.00,
            "POL-PREM-004",
            now,
            oneYearLater,
            true
        ));

        // Comprehensive Coverage Policies
        policies.add(createPolicy(
            "Discovery Insure",
            CoverageType.COMPREHENSIVE,
            500.00,
            0.00,
            "POL-COMP-001",
            now,
            twoYearsLater,
            true
        ));

        policies.add(createPolicy(
            "Santam",
            CoverageType.COMPREHENSIVE,
            550.00,
            0.00,
            "POL-COMP-002",
            now,
            oneYearLater,
            true
        ));

        policies.add(createPolicy(
            "Old Mutual",
            CoverageType.COMPREHENSIVE,
            480.00,
            0.00,
            "POL-COMP-003",
            now,
            twoYearsLater,
            true
        ));

        policies.add(createPolicy(
            "Hollard Insurance",
            CoverageType.COMPREHENSIVE,
            520.00,
            0.00,
            "POL-COMP-004",
            now,
            oneYearLater,
            true
        ));

        // Some expired policies for testing
        policies.add(createPolicy(
            "Budget Insurance",
            CoverageType.BASIC,
            90.00,
            10000.00,
            "POL-BASIC-EXPIRED-001",
            now.minusYears(2),
            now.minusMonths(1),
            false
        ));

        policies.add(createPolicy(
            "Old Mutual",
            CoverageType.PREMIUM,
            250.00,
            3000.00,
            "POL-PREM-EXPIRED-001",
            now.minusYears(1),
            now.minusDays(15),
            false
        ));

        // Policies expiring soon
        policies.add(createPolicy(
            "MiWay",
            CoverageType.PREMIUM,
            310.00,
            2000.00,
            "POL-PREM-EXPIRING-001",
            now.minusMonths(11),
            now.plusDays(30),
            true
        ));

        policies.add(createPolicy(
            "Santam",
            CoverageType.COMPREHENSIVE,
            540.00,
            0.00,
            "POL-COMP-EXPIRING-001",
            now.minusMonths(10),
            now.plusDays(60),
            true
        ));

        // Save all policies
        System.out.println("Creating insurance policies...");
        for (Insurance policy : policies) {
            Insurance saved = insuranceRepository.save(policy);
            System.out.println("✓ Created policy: " + saved.getPolicyNumber() +
                             " - " + saved.getInsuranceProvider() +
                             " (" + saved.getCoverageType() +
                             ") - R" + saved.getInsuranceCost() +
                             "/day - Status: " + (saved.isActive() ? "Active" : "Inactive"));
        }

        System.out.println("\n" + policies.size() + " insurance policies created successfully.");
    }

    private Insurance createPolicy(String provider, CoverageType coverageType,
                                  double cost, double deductible, String policyNumber,
                                  LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        return new Insurance.Builder()
                .setInsuranceProvider(provider)
                .setCoverageType(coverageType)
                .setInsuranceCost(cost)
                .setDeductible(deductible)
                .setPolicyNumber(policyNumber)
                .setInsuranceStartDate(startDate)
                .setInsuranceEndDate(endDate)
                .setActive(active)
                .build();
    }

    private void displayInsuranceSummary() {
        List<Insurance> allPolicies = insuranceRepository.findAll();

        long activeCount = allPolicies.stream().filter(Insurance::isActive).count();
        long inactiveCount = allPolicies.stream().filter(p -> !p.isActive()).count();

        System.out.println("Insurance Policy Summary:");
        System.out.println("- Total Policies: " + allPolicies.size());
        System.out.println("- Active Policies: " + activeCount);
        System.out.println("- Inactive/Expired Policies: " + inactiveCount);
        System.out.println();

        // Group by coverage type
        System.out.println("Policies by Coverage Type:");
        allPolicies.stream()
                .filter(Insurance::isActive)
                .collect(java.util.stream.Collectors.groupingBy(
                    Insurance::getCoverageType,
                    java.util.stream.Collectors.counting()
                ))
                .forEach((type, count) ->
                    System.out.println("  * " + type + ": " + count + " active policy/policies")
                );

        System.out.println();

        // Group by provider
        System.out.println("Policies by Provider:");
        allPolicies.stream()
                .filter(Insurance::isActive)
                .collect(java.util.stream.Collectors.groupingBy(
                    Insurance::getInsuranceProvider,
                    java.util.stream.Collectors.counting()
                ))
                .forEach((provider, count) ->
                    System.out.println("  * " + provider + ": " + count + " active policy/policies")
                );

        System.out.println();

        // Price ranges
        System.out.println("Price Ranges (Active Policies):");
        double minCost = allPolicies.stream()
                .filter(Insurance::isActive)
                .mapToDouble(Insurance::getInsuranceCost)
                .min()
                .orElse(0);
        double maxCost = allPolicies.stream()
                .filter(Insurance::isActive)
                .mapToDouble(Insurance::getInsuranceCost)
                .max()
                .orElse(0);
        double avgCost = allPolicies.stream()
                .filter(Insurance::isActive)
                .mapToDouble(Insurance::getInsuranceCost)
                .average()
                .orElse(0);

        System.out.println("  * Minimum: R" + String.format("%.2f", minCost) + "/day");
        System.out.println("  * Maximum: R" + String.format("%.2f", maxCost) + "/day");
        System.out.println("  * Average: R" + String.format("%.2f", avgCost) + "/day");
    }
}
