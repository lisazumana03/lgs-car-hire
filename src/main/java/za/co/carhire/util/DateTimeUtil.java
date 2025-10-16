package za.co.carhire.util;

import java.time.*;

public class DateTimeUtil {

    // Default timezone for South Africa
    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Africa/Johannesburg");

    /**
     * Convert LocalDateTime to ZonedDateTime using default timezone
     */
    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(DEFAULT_ZONE);
    }

    /**
     * Convert LocalDateTime to ZonedDateTime with specific timezone
     */
    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(zoneId);
    }

    /**
     * Convert ZonedDateTime to LocalDateTime (loses timezone info)
     */
    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.withZoneSameInstant(DEFAULT_ZONE).toLocalDateTime();
    }

    /**
     * Get current time with timezone awareness
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now(DEFAULT_ZONE);
    }

    /**
     * Get current LocalDateTime in default timezone
     */
    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(DEFAULT_ZONE);
    }

    /**
     * Check if a LocalDateTime is in the past (using default timezone)
     */
    public static boolean isPast(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return dateTime.isBefore(nowLocal());
    }

    /**
     * Check if a LocalDateTime is in the future (using default timezone)
     */
    public static boolean isFuture(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return dateTime.isAfter(nowLocal());
    }

    /**
     * Convert user input from specific timezone to system timezone
     * Useful for international bookings
     */
    public static LocalDateTime convertToSystemTime(LocalDateTime userDateTime, ZoneId userZone) {
        if (userDateTime == null) {
            return null;
        }
        ZonedDateTime userZoned = userDateTime.atZone(userZone);
        return userZoned.withZoneSameInstant(DEFAULT_ZONE).toLocalDateTime();
    }

    /**
     * Convert system time to user's timezone
     */
    public static LocalDateTime convertFromSystemTime(LocalDateTime systemDateTime, ZoneId userZone) {
        if (systemDateTime == null) {
            return null;
        }
        ZonedDateTime systemZoned = systemDateTime.atZone(DEFAULT_ZONE);
        return systemZoned.withZoneSameInstant(userZone).toLocalDateTime();
    }
}
