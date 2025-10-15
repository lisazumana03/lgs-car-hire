package za.co.carhire.domain.vehicle;

public enum VehicleCategory {
    ECONOMY("Economy - Compact and fuel-efficient vehicles for budget-conscious customers"),
    COMPACT("Compact - Small cars perfect for city driving"),
    SEDAN("Sedan - Comfortable mid-size cars for everyday use"),
    SUV("SUV - Sport Utility Vehicles with more space and capability"),
    LUXURY("Luxury - Premium vehicles with high-end features"),
    VAN("Van - Large vehicles for groups or cargo"),
    MINIVAN("Minivan - Family-friendly vehicles with multiple rows of seating"),
    CONVERTIBLE("Convertible - Open-top vehicles for a premium driving experience"),
    SPORTS("Sports - High-performance vehicles for enthusiasts"),
    ELECTRIC("Electric - Fully electric vehicles with zero emissions"),
    HYBRID("Hybrid - Vehicles combining electric and fuel engines");

    private final String description;

    VehicleCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
