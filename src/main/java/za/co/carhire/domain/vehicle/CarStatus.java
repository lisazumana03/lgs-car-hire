package za.co.carhire.domain.vehicle;

public enum CarStatus {
    AVAILABLE("Available for rental"),
    RENTED("Currently rented out"),
    MAINTENANCE("Under maintenance"),
    OUT_OF_SERVICE("Out of service - not available"),
    RESERVED("Reserved for upcoming booking");

    private final String description;

    CarStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
