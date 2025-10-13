package za.co.carhire.dto.reservation;

import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDTO {
    private int invoiceID;
    private double totalAmount;
    private double subTotal;
    private double taxAmount;
    private String status;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private String carModel;

    public InvoiceDTO(Invoice invoice) {
        this.invoiceID = invoice.getInvoiceID();
        this.totalAmount = invoice.getTotalAmount();
        this.subTotal = invoice.getSubTotal();
        this.taxAmount = invoice.getTaxAmount();
        this.status = invoice.getStatus();
        this.issueDate = invoice.getIssueDate();
        this.dueDate = invoice.getDueDate();
        this.carModel = extractCarModel(invoice);
    }

    private String extractCarModel(Invoice invoice) {
        try {
            if (invoice.getBooking() != null && invoice.getBooking().getCar() != null) {
                List<Car> cars = invoice.getBooking().getCar();
                if (!cars.isEmpty() && cars.get(0) != null) {
                    return cars.get(0).getModel() != null ? cars.get(0).getModel() : "Unknown Model";
                }
            }
        } catch (Exception e) {
            // Log error if needed
        }
        return "Unknown";
    }

    // Getters
    public int getInvoiceID() { return invoiceID; }
    public double getTotalAmount() { return totalAmount; }
    public double getSubTotal() { return subTotal; }
    public double getTaxAmount() { return taxAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getIssueDate() { return issueDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public String getCarModel() { return carModel; }
}