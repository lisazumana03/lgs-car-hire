package za.co.carhire.dto.reservation;

import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDateTime;

public class InvoiceDTO {
    private int invoiceID;
    private double totalAmount;
    private double subTotal;
    private double taxAmount;
    private String status;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private String carModel;
    private LocalDateTime bookingStartDate;
    private LocalDateTime bookingEndDate;

    // Add these new fields
    private String customerName;
    private String customerEmail;
    private String pickupLocationName;
    private String pickupLocationAddress;

    public InvoiceDTO() {
        // Default constructor
    }

    public InvoiceDTO(Invoice invoice) {
        this.invoiceID = invoice.getInvoiceID();
        this.totalAmount = invoice.getTotalAmount();
        this.subTotal = invoice.getSubTotal();
        this.taxAmount = invoice.getTaxAmount();
        this.status = invoice.getStatus();
        this.issueDate = invoice.getIssueDate();
        this.dueDate = invoice.getDueDate();

        // FIXED: Only call extractCarModel once
        this.carModel = extractCarModel(invoice);

        // Extract customer information
        if (invoice.getBooking() != null && invoice.getBooking().getUser() != null) {
            var user = invoice.getBooking().getUser();
            this.customerName = user.getFirstName(); // FIXED: Changed from getFirstName() to getName()
            this.customerEmail = user.getEmail();
        } else {
            this.customerName = "Customer";
            this.customerEmail = "customer@email.com";
        }

        // Extract pickup location
        if (invoice.getBooking() != null && invoice.getBooking().getPickupLocation() != null) {
            var location = invoice.getBooking().getPickupLocation();
            this.pickupLocationName = location.getLocationName();
            this.pickupLocationAddress = formatLocationAddress(location);
        } else {
            this.pickupLocationName = "Pickup Location";
            this.pickupLocationAddress = "Address not available";
        }

        // Extract booking dates
        if (invoice.getBooking() != null) {
            this.bookingStartDate = invoice.getBooking().getStartDate();
            this.bookingEndDate = invoice.getBooking().getEndDate();
        }
    }

    private String extractCarModel(Invoice invoice) {
        if (invoice.getBooking() == null) {
            return "Vehicle";
        }

        // FIXED: Use the single car relationship (after you change Booking.java)
        Car car = invoice.getBooking().getCar();
        if (car != null) {
            return buildCarName(car);
        }

        return "Vehicle";
    }

    private String buildCarName(Car car) {
        StringBuilder carName = new StringBuilder();

        if (car.getBrand() != null && !car.getBrand().trim().isEmpty()) {
            carName.append(car.getBrand());
        }

        if (car.getModel() != null && !car.getModel().trim().isEmpty()) {
            if (carName.length() > 0) carName.append(" ");
            carName.append(car.getModel());
        }

        if (car.getYear() > 0) {
            if (carName.length() > 0) carName.append(" ");
            carName.append("(").append(car.getYear()).append(")");
        }

        return carName.length() > 0 ? carName.toString() : "Vehicle";
    }

    private String formatLocationAddress(Location location) {
        StringBuilder address = new StringBuilder();
        if (location.getStreetNumber() > 0) {
            address.append(location.getStreetNumber()).append(" ");
        }
        if (location.getStreetName() != null) {
            address.append(location.getStreetName());
        }
        if (location.getCityOrTown() != null) {
            if (address.length() > 0) address.append(", ");
            address.append(location.getCityOrTown());
        }
        if (location.getProvinceOrState() != null) {
            if (address.length() > 0) address.append(", ");
            address.append(location.getProvinceOrState());
        }
        if (location.getCountry() != null) {
            if (address.length() > 0) address.append(", ");
            address.append(location.getCountry());
        }
        return address.toString();
    }

    // Getters and Setters
    public int getInvoiceID() { return invoiceID; }
    public double getTotalAmount() { return totalAmount; }
    public double getSubTotal() { return subTotal; }
    public double getTaxAmount() { return taxAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getIssueDate() { return issueDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public String getCarModel() { return carModel; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getPickupLocationName() { return pickupLocationName; }
    public String getPickupLocationAddress() { return pickupLocationAddress; }
    public LocalDateTime getBookingStartDate() { return bookingStartDate; }
    public LocalDateTime getBookingEndDate() { return bookingEndDate; }
    public void setBookingStartDate(LocalDateTime bookingStartDate) { this.bookingStartDate = bookingStartDate; }
    public void setBookingEndDate(LocalDateTime bookingEndDate) { this.bookingEndDate = bookingEndDate; }
    public void setInvoiceID(int invoiceID) { this.invoiceID = invoiceID; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setCarModel(String carModel) { this.carModel = carModel; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public void setPickupLocationName(String pickupLocationName) { this.pickupLocationName = pickupLocationName; }
    public void setPickupLocationAddress(String pickupLocationAddress) { this.pickupLocationAddress = pickupLocationAddress; }
}