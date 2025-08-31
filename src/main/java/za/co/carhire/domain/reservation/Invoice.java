package za.co.carhire.domain.reservation;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/* Invoice.java
 * Invoice POJO class
 * Sanele Zondi (221602011)
 * Due Date: 11/05/2025
 * */

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int invoiceID;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id", nullable = false)
  private Payment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  @Column(name = "issue_date", nullable = false)
  private LocalDateTime issueDate;

  @Column(name = "due_date", nullable = false)
  private LocalDateTime dueDate;

  @Column(name = "sub_total", nullable = false)
  private double subTotal;

  @Column(name = "tax_amount", nullable = false)
  private double taxAmount;

  @Column(name = "total_amount", nullable = false)
  private double totalAmount;

  @Column(nullable = false, length = 20)
  private String status;

  protected Invoice() {
  }

  private Invoice(Builder builder) {
    this.invoiceID = builder.invoiceID;
    this.payment = builder.payment;
    this.booking = builder.booking;
    this.issueDate = builder.issueDate;
    this.dueDate = builder.dueDate;
    this.subTotal = builder.subTotal;
    this.taxAmount = builder.taxAmount;
    this.totalAmount = builder.totalAmount;
    this.status = builder.status;
  }

  public int getInvoiceID() {
    return invoiceID;
  }

  public void setInvoiceID(int invoiceID) {
    this.invoiceID = invoiceID;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Booking getBooking() {
    return booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public LocalDateTime getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDateTime issueDate) {
    this.issueDate = issueDate;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public double getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(double subTotal) {
    this.subTotal = subTotal;
  }

  public double getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(double taxAmount) {
    this.taxAmount = taxAmount;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Invoice{" +
        "invoiceID=" + invoiceID +
        ", payment=" + payment +
        ", booking=" + booking +
        ", issueDate=" + issueDate +
        ", dueDate=" + dueDate +
        ", subTotal=" + subTotal +
        ", taxAmount=" + taxAmount +
        ", totalAmount=" + totalAmount +
        ", status='" + status + '\'' +
        '}';
  }

  public static class Builder {
    private int invoiceID;
    private Payment payment;
    private Booking booking;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private double subTotal;
    private double taxAmount;
    private double totalAmount;
    private String status;

    public Builder setInvoiceID(int invoiceID) {
      this.invoiceID = invoiceID;
      return this;
    }

    public Builder setPayment(Payment payment) {
      this.payment = payment;
      return this;
    }

    public Builder setBooking(Booking booking) {
      this.booking = booking;
      return this;
    }

    public Builder setIssueDate(LocalDateTime issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public Builder setDueDate(LocalDateTime dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public Builder setSubTotal(double subTotal) {
      this.subTotal = subTotal;
      return this;
    }

    public Builder setTaxAmount(double taxAmount) {
      this.taxAmount = taxAmount;
      return this;
    }

    public Builder setTotalAmount(double totalAmount) {
      this.totalAmount = totalAmount;
      return this;
    }

    public Builder setStatus(String status) {
      this.status = status;
      return this;
    }

    public Builder copy(Invoice invoice) {
      this.invoiceID = invoice.getInvoiceID();
      this.payment = invoice.getPayment();
      this.booking = invoice.getBooking();
      this.issueDate = invoice.getIssueDate();
      this.dueDate = invoice.getDueDate();
      this.subTotal = invoice.getSubTotal();
      this.taxAmount = invoice.getTaxAmount();
      this.totalAmount = invoice.getTotalAmount();
      this.status = invoice.getStatus();
      return this;
    }

    public Invoice build() {
      return new Invoice(this);
    }
  }
}
