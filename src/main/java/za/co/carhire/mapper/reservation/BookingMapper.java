package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.reservation.BookingDTO;

import java.time.Duration;

/**
 *
 * Author: Imtiyaaz Waggie 219374759
 * Fixed Booking Mapper
 *
 */
public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO.Builder builder = new BookingDTO.Builder()
                .setBookingID(booking.getBookingID())
                .setBookingDateAndTime(booking.getBookingDateAndTime())
                .setStartDate(booking.getStartDate())
                .setEndDate(booking.getEndDate())
                .setBookingStatus(booking.getBookingStatus());

        if (booking.getUser() != null) {
            User user = booking.getUser();
            builder.setUserID(user.getUserId())
                    .setUserName(user.getName())
                    .setUserEmail(user.getEmail());
        }

        if (booking.getCar() != null) {
            Car car = booking.getCar();
            builder.setCarID(car.getCarID())
                    .setCarBrandModel(car.getBrand() + " " + car.getModel())
                    .setCarRentalPrice(car.getRentalPrice());
        }

        if (booking.getInsurance() != null) {
            Insurance insurance = booking.getInsurance();
            builder.setInsuranceID(insurance.getInsuranceID())
                    .setInsuranceProvider(insurance.getInsuranceProvider())
                    .setInsuranceCost(insurance.getInsuranceCost());
        }

        if (booking.getPickupLocation() != null) {
            Location pickup = booking.getPickupLocation();
            builder.setPickupLocationID(pickup.getLocationID())
                    .setPickupLocationName(pickup.getLocationName());
        }

        if (booking.getDropOffLocation() != null) {
            Location dropOff = booking.getDropOffLocation();
            builder.setDropOffLocationID(dropOff.getLocationID())
                    .setDropOffLocationName(dropOff.getLocationName());
        }

        double totalCost = calculateTotalCost(booking);
        builder.setTotalCost(totalCost);

        return builder.build();
    }

    public static Booking toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Booking.Builder()
                .setBookingID(dto.getBookingID())
                .setBookingDateAndTime(dto.getBookingDateAndTime())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setBookingStatus(dto.getBookingStatus())
                .build();
    }

    public static Booking updateEntityFromDTO(Booking existingBooking, BookingDTO dto) {
        if (existingBooking == null || dto == null) {
            return existingBooking;
        }

        existingBooking.setStartDate(dto.getStartDate());
        existingBooking.setEndDate(dto.getEndDate());
        existingBooking.setBookingStatus(dto.getBookingStatus());

        return existingBooking;
    }

    private static double calculateTotalCost(Booking booking) {
        double totalCost = 0.0;

        if (booking.getCar() != null && booking.getStartDate() != null && booking.getEndDate() != null) {
            long days = Duration.between(booking.getStartDate(), booking.getEndDate()).toDays();
            if (days < 1) days = 1;

            totalCost += booking.getCar().getRentalPrice() * days;

            if (booking.getInsurance() != null) {
                totalCost += booking.getInsurance().getInsuranceCost();
            }
        }

        return totalCost;
    }
}