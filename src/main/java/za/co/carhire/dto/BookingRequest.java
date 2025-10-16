package za.co.carhire.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import za.co.carhire.domain.reservation.BookingStatus;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest {
    private Integer userId;
    private Integer carId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer pickupLocationId;
    private String pickupLocationName;
    private Integer dropOffLocationId;
    private String dropOffLocationName;
    private BookingStatus bookingStatus;
    private Double discountAmount;

    public BookingRequest() {
    }

    public BookingRequest(Integer userId, Integer carId, LocalDateTime startDate,
                         LocalDateTime endDate, Integer pickupLocationId,
                         String pickupLocationName, Integer dropOffLocationId,
                         String dropOffLocationName, BookingStatus bookingStatus,
                         Double discountAmount) {
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocationId = pickupLocationId;
        this.pickupLocationName = pickupLocationName;
        this.dropOffLocationId = dropOffLocationId;
        this.dropOffLocationName = dropOffLocationName;
        this.bookingStatus = bookingStatus;
        this.discountAmount = discountAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
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

    public Integer getPickupLocationId() {
        return pickupLocationId;
    }

    public void setPickupLocationId(Integer pickupLocationId) {
        this.pickupLocationId = pickupLocationId;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public Integer getDropOffLocationId() {
        return dropOffLocationId;
    }

    public void setDropOffLocationId(Integer dropOffLocationId) {
        this.dropOffLocationId = dropOffLocationId;
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

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "userId=" + userId +
                ", carId=" + carId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupLocationId=" + pickupLocationId +
                ", pickupLocationName='" + pickupLocationName + '\'' +
                ", dropOffLocationId=" + dropOffLocationId +
                ", dropOffLocationName='" + dropOffLocationName + '\'' +
                ", bookingStatus=" + bookingStatus +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
