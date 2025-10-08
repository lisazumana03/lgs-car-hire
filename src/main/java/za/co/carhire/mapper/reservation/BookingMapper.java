package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.reservation.BookingDTO;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between Booking entity and BookingDTO
 * Author: Lisakhanya Zumana - 230864821
 * Date: 08/10/2025
 */
public class BookingMapper {

    /**
     * Convert Booking entity to BookingDTO
     * @param booking the entity to convert
     * @return the DTO representation
     */
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO.Builder builder = new BookingDTO.Builder()
                .setBookingID(booking.getBookingID())
                .setBookingDateAndTime(booking.getBookingDateAndTime())
                .setStartDate(booking.getStartDate())
                .setEndDate(booking.getEndDate())
                .setBookingStatus(booking.getBookingStatus() != null ?
                        booking.getBookingStatus().name() : null);

        if (booking.getUser() != null) {
            builder.setUserID(booking.getUser().getUserId())
                    .setUserName(booking.getUser().getName())
                    .setUserEmail(booking.getUser().getEmail());
        }

        if (booking.getCars() != null && !booking.getCars().isEmpty()) {
            builder.setCarIDs(booking.getCars().stream()
                    .map(Car::getCarID)
                    .collect(Collectors.toList()));

            builder.setCars(booking.getCars().stream()
                    .map(car -> new BookingDTO.CarInfo(
                            car.getCarID(),
                            car.getBrand(),
                            car.getModel(),
                            car.getYear(),
                            car.getRentalPrice(),
                            car.getImageUrl()
                    ))
                    .collect(Collectors.toList()));
        }

        if (booking.getPickupLocation() != null) {
            Location pickup = booking.getPickupLocation();
            builder.setPickupLocationID(pickup.getLocationID())
                    .setPickupLocationName(pickup.getLocationName())
                    .setPickupLocationAddress(formatAddress(pickup));
        }

        if (booking.getDropOffLocation() != null) {
            Location dropOff = booking.getDropOffLocation();
            builder.setDropOffLocationID(dropOff.getLocationID())
                    .setDropOffLocationName(dropOff.getLocationName())
                    .setDropOffLocationAddress(formatAddress(dropOff));
        }

        return builder.build();
    }

    /**
     * Convert BookingDTO to Booking entity (basic fields only)
     * Note: Related entities (User, Cars, Locations) should be set separately via services
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static Booking toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Booking.Builder()
                .setBookingID(dto.getBookingID())
                .setBookingDateAndTime(dto.getBookingDateAndTime())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .build();
    }

    /**
     * Update existing Booking entity from BookingDTO (basic fields only)
     * Note: Related entities should be updated separately via services
     * @param existingBooking the existing entity to update
     * @param dto the DTO with new values
     * @return the updated entity
     */
    public static Booking updateEntityFromDTO(Booking existingBooking, BookingDTO dto) {
        if (existingBooking == null || dto == null) {
            return existingBooking;
        }

        return new Booking.Builder()
                .copy(existingBooking)
                .setBookingDateAndTime(dto.getBookingDateAndTime())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .build();
    }

    /**
     * Format location address into a readable string
     * @param location the Location entity
     * @return formatted address string
     */
    private static String formatAddress(Location location) {
        if (location == null) {
            return null;
        }

        StringBuilder address = new StringBuilder();

        if (location.getStreetNumber() > 0) {
            address.append(location.getStreetNumber()).append(" ");
        }

        if (location.getStreetName() != null && !location.getStreetName().isEmpty()) {
            address.append(location.getStreetName()).append(", ");
        }

        if (location.getCityOrTown() != null && !location.getCityOrTown().isEmpty()) {
            address.append(location.getCityOrTown()).append(", ");
        }

        if (location.getProvinceOrState() != null && !location.getProvinceOrState().isEmpty()) {
            address.append(location.getProvinceOrState()).append(", ");
        }

        if (location.getCountry() != null && !location.getCountry().isEmpty()) {
            address.append(location.getCountry());
        }

        if (location.getPostalCode() != null && !location.getPostalCode().isEmpty()) {
            address.append(" ").append(location.getPostalCode());
        }

        return address.toString().trim();
    }
}
