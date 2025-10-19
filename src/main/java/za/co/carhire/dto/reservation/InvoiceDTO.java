package za.co.carhire.dto.reservation;

import za.co.carhire.domain.reservation.Invoice;
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
    private String customerName;
    private String customerEmail;
    private String pickupLocationName;
    private String pickupLocationAddress;
    private LocalDateTime bookingStartDate;
    private LocalDateTime bookingEndDate;

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

        // Extract booking data
        if (invoice.getBooking() != null) {
            var booking = invoice.getBooking();
            this.bookingStartDate = booking.getStartDate();
            this.bookingEndDate = booking.getEndDate();

            // Extract car data
            if (booking.getCar() != null && !booking.getCar().isEmpty()) {
                var car = booking.getCar().get(0);
                this.carModel = car.getBrand() + " " + car.getModel();
            } else {
                this.carModel = "Car Rental";
            }

            // Extract user/customer data
            if (booking.getUser() != null) {
                var user = booking.getUser();
                this.customerName = user.getFirstName() + " " + user.getLastName();
                this.customerEmail = user.getEmail();
            }

            // Extract pickup location data
            if (booking.getPickupLocation() != null) {
                var location = booking.getPickupLocation();
                this.pickupLocationName = location.getLocationName();
                this.pickupLocationAddress = location.getFullAddress();
            }
        } else {
            this.carModel = "Car Rental";
        }
    }

    // Getters
    public int getInvoiceID() {
        return invoiceID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public String getPickupLocationAddress() {
        return pickupLocationAddress;
    }

    public LocalDateTime getBookingStartDate() {
        return bookingStartDate;
    }

    public LocalDateTime getBookingEndDate() {
        return bookingEndDate;
    }

    // Setters
    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public void setPickupLocationAddress(String pickupLocationAddress) {
        this.pickupLocationAddress = pickupLocationAddress;
    }

    public void setBookingStartDate(LocalDateTime bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public void setBookingEndDate(LocalDateTime bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

}