package za.co.carhire.service.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.PricingRule;
import za.co.carhire.repository.vehicle.IPricingRuleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingPricingService {

    @Autowired
    private IPricingRuleRepository pricingRuleRepository;

    // Tax rate (15% VAT for South Africa)
    private static final double TAX_RATE = 0.15;

    /**
     * Calculate all pricing fields for a booking
     * @param booking The booking to calculate pricing for
     * @return Updated booking with calculated pricing fields
     * @throws IllegalArgumentException if car, carType, or dates are missing
     * @throws IllegalStateException if no active pricing rule found
     */
    public Booking calculatePricing(Booking booking) {
        validateBooking(booking);

        Car car = booking.getCar();
        CarType carType = car.getCarType();

        // Get active pricing rule for this car type
        PricingRule pricingRule = getActivePricingRule(carType, booking.getStartDate());

        // Calculate rental days
        int rentalDays = calculateRentalDays(booking.getStartDate(), booking.getEndDate());

        // Calculate subtotal based on pricing rule and rental duration
        double subtotal = calculateSubtotal(pricingRule, rentalDays, booking.getStartDate());

        // Calculate tax
        double taxAmount = subtotal * TAX_RATE;

        // Get discount amount (if already set, otherwise 0)
        double discountAmount = booking.getDiscountAmount();

        // Calculate total
        double totalAmount = subtotal + taxAmount - discountAmount;

        // Build and return updated booking
        return new Booking.Builder()
                .copy(booking)
                .setRentalDays(rentalDays)
                .setSubtotal(subtotal)
                .setTaxAmount(taxAmount)
                .setDiscountAmount(discountAmount)
                .setTotalAmount(totalAmount)
                .setCurrency("ZAR")
                .build();
    }

    /**
     * Calculate rental days between start and end date
     * Minimum rental is 1 day
     */
    private int calculateRentalDays(LocalDateTime startDate, LocalDateTime endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        // Minimum 1 day rental
        return (int) Math.max(1, days);
    }

    /**
     * Calculate subtotal based on pricing rule and rental duration
     * Applies weekly/monthly rates if applicable
     */
    private double calculateSubtotal(PricingRule pricingRule, int rentalDays, LocalDateTime startDate) {
        double subtotal;

        // Apply monthly rate if available and rental >= 30 days
        if (rentalDays >= 30 && pricingRule.getMonthlyRate() > 0) {
            int months = rentalDays / 30;
            int remainingDays = rentalDays % 30;
            subtotal = (months * pricingRule.getMonthlyRate()) +
                       (remainingDays * pricingRule.getBaseDailyRate());
        }
        // Apply weekly rate if available and rental >= 7 days
        else if (rentalDays >= 7 && pricingRule.getWeeklyRate() > 0) {
            int weeks = rentalDays / 7;
            int remainingDays = rentalDays % 7;
            subtotal = (weeks * pricingRule.getWeeklyRate()) +
                       (remainingDays * pricingRule.getBaseDailyRate());
        }
        // Apply daily rate
        else {
            subtotal = rentalDays * pricingRule.getBaseDailyRate();
        }

        // Apply weekend rate if applicable (Saturday/Sunday)
        subtotal = applyWeekendRate(subtotal, pricingRule, startDate, rentalDays);

        // Apply seasonal multiplier
        subtotal = subtotal * pricingRule.getSeasonalMultiplier();

        return Math.round(subtotal * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * Apply weekend rate if the rental includes weekend days
     */
    private double applyWeekendRate(double subtotal, PricingRule pricingRule, LocalDateTime startDate, int rentalDays) {
        if (pricingRule.getWeekendRate() <= 0) {
            return subtotal;
        }

        // Count weekend days in the rental period
        int weekendDays = 0;
        for (int i = 0; i < rentalDays; i++) {
            LocalDateTime currentDay = startDate.plusDays(i);
            if (isWeekend(currentDay)) {
                weekendDays++;
            }
        }

        if (weekendDays > 0) {
            int weekDays = rentalDays - weekendDays;
            subtotal = (weekDays * pricingRule.getBaseDailyRate()) +
                       (weekendDays * pricingRule.getWeekendRate());
        }

        return subtotal;
    }

    /**
     * Check if a date falls on weekend (Saturday or Sunday)
     */
    private boolean isWeekend(LocalDateTime date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7; // Saturday = 6, Sunday = 7
    }

    /**
     * Get active pricing rule for car type on specific date
     * @throws IllegalStateException if no active pricing rule found
     */
    private PricingRule getActivePricingRule(CarType carType, LocalDateTime bookingDate) {
        LocalDate date = bookingDate.toLocalDate();

        // Find active pricing rules for this car type
        List<PricingRule> pricingRules = pricingRuleRepository
                .findByCarTypeCarTypeIDAndActive(carType.getCarTypeID(), true);

        // Filter by date validity
        PricingRule validRule = pricingRules.stream()
                .filter(rule -> isValidForDate(rule, date))
                .findFirst()
                .orElse(null);

        if (validRule == null) {
            throw new IllegalStateException(
                "No active pricing rule found for car type: " + carType.getCategory() +
                " on date: " + date
            );
        }

        return validRule;
    }

    /**
     * Check if pricing rule is valid for given date
     */
    private boolean isValidForDate(PricingRule rule, LocalDate date) {
        LocalDate validFrom = rule.getValidFrom();
        LocalDate validTo = rule.getValidTo();

        // If no date range specified, rule is always valid
        if (validFrom == null && validTo == null) {
            return true;
        }

        // Check if date falls within valid range
        boolean afterStart = validFrom == null || !date.isBefore(validFrom);
        boolean beforeEnd = validTo == null || !date.isAfter(validTo);

        return afterStart && beforeEnd;
    }

    /**
     * Validate booking has required fields for pricing calculation
     */
    private void validateBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (booking.getCar() == null) {
            throw new IllegalArgumentException("Booking must have a car assigned");
        }

        if (booking.getCar().getCarType() == null) {
            throw new IllegalArgumentException("Car must have a car type assigned");
        }

        if (booking.getStartDate() == null || booking.getEndDate() == null) {
            throw new IllegalArgumentException("Booking must have start and end dates");
        }

        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    /**
     * Apply a discount to a booking and recalculate total
     * @param booking The booking to apply discount to
     * @param discountAmount The discount amount to apply
     * @return Updated booking with discount applied
     */
    public Booking applyDiscount(Booking booking, double discountAmount) {
        if (discountAmount < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }

        double newTotal = booking.getSubtotal() + booking.getTaxAmount() - discountAmount;

        if (newTotal < 0) {
            throw new IllegalArgumentException("Discount cannot exceed total amount");
        }

        return new Booking.Builder()
                .copy(booking)
                .setDiscountAmount(discountAmount)
                .setTotalAmount(newTotal)
                .build();
    }
}
