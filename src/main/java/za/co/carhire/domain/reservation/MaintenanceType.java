package za.co.carhire.domain.reservation;

public enum MaintenanceType {
    ROUTINE,           // Regular scheduled maintenance (oil change, filter replacement)
    REPAIR,            // Fixing broken or malfunctioning components
    INSPECTION,        // Safety and compliance inspections
    TIRE_SERVICE,      // Tire rotation, replacement, balancing
    BRAKE_SERVICE,     // Brake pad replacement, brake fluid change
    ENGINE_SERVICE,    // Engine-related maintenance
    TRANSMISSION,      // Transmission service or repair
    ELECTRICAL,        // Electrical system repairs
    BODYWORK,          // Body damage repair, paint work
    DETAILING,         // Interior/exterior cleaning and detailing
    ACCIDENT_REPAIR,   // Repairs from accidents
    RECALL,            // Manufacturer recall service
    DIAGNOSTIC,        // Diagnostic testing
    OTHER              // Other maintenance types
}
