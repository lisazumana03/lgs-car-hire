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
        this.carModel = "Car Rental"; // Simple default - remove the complex extraction
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
    // Add these setters to InvoiceDTO
    public void setInvoiceID(int invoiceID) { this.invoiceID = invoiceID; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setCarModel(String carModel) { this.carModel = carModel; }

}