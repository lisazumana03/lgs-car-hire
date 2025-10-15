package za.co.carhire.domain.vehicle;

public enum FuelType {
    PETROL("Petrol/Gasoline"),
    DIESEL("Diesel"),
    ELECTRIC("Electric - Zero emissions"),
    HYBRID("Hybrid - Electric and fuel combination"),
    PLUG_IN_HYBRID("Plug-in Hybrid - Rechargeable hybrid with larger battery"),
    CNG("Compressed Natural Gas"),
    LPG("Liquefied Petroleum Gas");

    private final String description;

    FuelType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return switch (this) {
            case PETROL -> "Petrol";
            case DIESEL -> "Diesel";
            case ELECTRIC -> "Electric";
            case HYBRID -> "Hybrid";
            case PLUG_IN_HYBRID -> "Plug-in Hybrid";
            case CNG -> "CNG";
            case LPG -> "LPG";
        };
    }
}
