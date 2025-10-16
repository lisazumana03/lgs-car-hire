package za.co.carhire.domain.reservation;

/**
 * CoverageType.java
 * Enum for insurance coverage levels
 * Date: 2025-10-16
 */
public enum CoverageType {
    NONE("No Insurance"),
    BASIC("Basic Coverage"),
    PREMIUM("Premium Coverage"),
    COMPREHENSIVE("Comprehensive Coverage");

    private final String displayName;

    CoverageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
