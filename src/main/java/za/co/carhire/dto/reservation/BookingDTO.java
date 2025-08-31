package za.co.carhire.dto.reservation;

import za.co.carhire.domain.reservation.BookingStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * Author:  Imtiyaaz Waggie 219374759
 * Fixed BookingDTO
 *
 */
public class BookingDTO implements Serializable {

    private int bookingID;
    private Integer userID;
    private String userName;
    private String userEmail;

    private Integer carID;
    private String carBrandModel;
    private double carRentalPrice;

    private Integer insuranceID;
    private String insuranceProvider;
    private double insuranceCost;

    private LocalDateTime bookingDateAndTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer pickupLocationID;
    private String pickupLocationName;

    private Integer dropOffLocationID;
    private String dropOffLocationName;

    private BookingStatus bookingStatus;
    private double totalCost;

    public BookingDTO() {
    }

    public static class Builder {
        private int bookingID;
        private Integer userID;
        private String userName;
        private String userEmail;
        private Integer carID;
        private String carBrandModel;
        private double carRentalPrice;
        private Integer insuranceID;
        private String insuranceProvider;
        private double insuranceCost;
        private LocalDateTime bookingDateAndTime;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Integer pickupLocationID;
        private String pickupLocationName;
        private Integer dropOffLocationID;
        private String dropOffLocationName;
        private BookingStatus bookingStatus;
        private double totalCost;

        public Builder setBookingID(int bookingID) {
            this.bookingID = bookingID;
            return this;
        }

        public Builder setUserID(Integer userID) {
            this.userID = userID;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder setCarID(Integer carID) {
            this.carID = carID;
            return this;
        }

        public Builder setCarBrandModel(String carBrandModel) {
            this.carBrandModel = carBrandModel;
            return this;
        }

        public Builder setCarRentalPrice(double carRentalPrice) {
            this.carRentalPrice = carRentalPrice;
            return this;
        }

        public Builder setInsuranceID(Integer insuranceID) {
            this.insuranceID = insuranceID;
            return this;
        }

        public Builder setInsuranceProvider(String insuranceProvider) {
            this.insuranceProvider = insuranceProvider;
            return this;
        }

        public Builder setInsuranceCost(double insuranceCost) {
            this.insuranceCost = insuranceCost;
            return this;
        }

        public Builder setBookingDateAndTime(LocalDateTime bookingDateAndTime) {
            this.bookingDateAndTime = bookingDateAndTime;
            return this;
        }

        public Builder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setPickupLocationID(Integer pickupLocationID) {
            this.pickupLocationID = pickupLocationID;
            return this;
        }

        public Builder setPickupLocationName(String pickupLocationName) {
            this.pickupLocationName = pickupLocationName;
            return this;
        }

        public Builder setDropOffLocationID(Integer dropOffLocationID) {
            this.dropOffLocationID = dropOffLocationID;
            return this;
        }

        public Builder setDropOffLocationName(String dropOffLocationName) {
            this.dropOffLocationName = dropOffLocationName;
            return this;
        }

        public Builder setBookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder setTotalCost(double totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public BookingDTO build() {
            BookingDTO dto = new BookingDTO();
            dto.bookingID = this.bookingID;
            dto.userID = this.userID;
            dto.userName = this.userName;
            dto.userEmail = this.userEmail;
            dto.carID = this.carID;
            dto.carBrandModel = this.carBrandModel;
            dto.carRentalPrice = this.carRentalPrice;
            dto.insuranceID = this.insuranceID;
            dto.insuranceProvider = this.insuranceProvider;
            dto.insuranceCost = this.insuranceCost;
            dto.bookingDateAndTime = this.bookingDateAndTime;
            dto.startDate = this.startDate;
            dto.endDate = this.endDate;
            dto.pickupLocationID = this.pickupLocationID;
            dto.pickupLocationName = this.pickupLocationName;
            dto.dropOffLocationID = this.dropOffLocationID;
            dto.dropOffLocationName = this.dropOffLocationName;
            dto.bookingStatus = this.bookingStatus;
            dto.totalCost = this.totalCost;
            return dto;
        }
    }

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public String getCarBrandModel() {
        return carBrandModel;
    }

    public void setCarBrandModel(String carBrandModel) {
        this.carBrandModel = carBrandModel;
    }

    public double getCarRentalPrice() {
        return carRentalPrice;
    }

    public void setCarRentalPrice(double carRentalPrice) {
        this.carRentalPrice = carRentalPrice;
    }

    public Integer getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(Integer insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public double getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(double insuranceCost) {
        this.insuranceCost = insuranceCost;
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

    public Integer getPickupLocationID() {
        return pickupLocationID;
    }

    public void setPickupLocationID(Integer pickupLocationID) {
        this.pickupLocationID = pickupLocationID;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public Integer getDropOffLocationID() {
        return dropOffLocationID;
    }

    public void setDropOffLocationID(Integer dropOffLocationID) {
        this.dropOffLocationID = dropOffLocationID;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", carID=" + carID +
                ", carBrandModel='" + carBrandModel + '\'' +
                ", insuranceID=" + insuranceID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupLocationName='" + pickupLocationName + '\'' +
                ", dropOffLocationName='" + dropOffLocationName + '\'' +
                ", bookingStatus=" + bookingStatus +
                ", totalCost=" + totalCost +
                '}';
    }
}