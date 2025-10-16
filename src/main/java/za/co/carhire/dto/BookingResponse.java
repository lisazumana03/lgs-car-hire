package za.co.carhire.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import za.co.carhire.domain.reservation.BookingStatus;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
    private Integer bookingId;
    private UserDTO user;
    private Integer carId;
    private String carBrand;
    private String carModel;
    private LocalDateTime bookingDateAndTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pickupLocationName;
    private String dropOffLocationName;
    private BookingStatus bookingStatus;
    private Integer rentalDays;
    private Double subtotal;
    private Double taxAmount;
    private Double discountAmount;
    private Double totalAmount;
    private String currency;
    private String paymentStatus;

    public BookingResponse() {
    }

    public BookingResponse(Integer bookingId, UserDTO user, Integer carId,
                          String carBrand, String carModel,
                          LocalDateTime bookingDateAndTime, LocalDateTime startDate,
                          LocalDateTime endDate, String pickupLocationName,
                          String dropOffLocationName, BookingStatus bookingStatus,
                          Integer rentalDays, Double subtotal, Double taxAmount,
                          Double discountAmount, Double totalAmount, String currency,
                          String paymentStatus) {
        this.bookingId = bookingId;
        this.user = user;
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.bookingDateAndTime = bookingDateAndTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocationName = pickupLocationName;
        this.dropOffLocationName = dropOffLocationName;
        this.bookingStatus = bookingStatus;
        this.rentalDays = rentalDays;
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.paymentStatus = paymentStatus;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public LocalDateTime getBookingDateAndTime() {
        return bookingDateAndTime;
    }

    public void setBookingDateAndTime(LocalDateTime bookingDateAndTime) {
        this.bookingDateAndTime = bookingDateAndTime;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public String getDropOffLocationName() {
        return dropOffLocationName;
    }

    public void setDropOffLocationName(String dropOffLocationName) {
        this.dropOffLocationName = dropOffLocationName;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", carId=" + carId +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupLocationName='" + pickupLocationName + '\'' +
                ", dropOffLocationName='" + dropOffLocationName + '\'' +
                ", bookingStatus=" + bookingStatus +
                ", rentalDays=" + rentalDays +
                ", subtotal=" + subtotal +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
