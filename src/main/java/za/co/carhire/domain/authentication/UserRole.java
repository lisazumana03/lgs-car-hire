package za.co.carhire.domain.authentication;

public enum UserRole {
    CUSTOMER("Customer"),
    ADMIN("Administrator");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static UserRole fromString(String role) {
        if (role == null) {
            return CUSTOMER;
        }

        String upperRole = role.toUpperCase();
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equals(upperRole)) {
                return userRole;
            }
        }
        return CUSTOMER;
    }
}