package za.co.carhire.domain.vehicle;

public enum TransmissionType {
    MANUAL("Manual transmission - Driver operates clutch and gearshift"),
    AUTOMATIC("Automatic transmission - No clutch, automatic gear changes"),
    SEMI_AUTOMATIC("Semi-automatic - Automated manual transmission"),
    CVT("Continuously Variable Transmission - Seamless gear ratios"),
    DUAL_CLUTCH("Dual-clutch transmission - Fast automatic shifting");

    private final String description;

    TransmissionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return switch (this) {
            case MANUAL -> "Manual";
            case AUTOMATIC -> "Automatic";
            case SEMI_AUTOMATIC -> "Semi-Automatic";
            case CVT -> "CVT";
            case DUAL_CLUTCH -> "Dual-Clutch";
        };
    }
}
