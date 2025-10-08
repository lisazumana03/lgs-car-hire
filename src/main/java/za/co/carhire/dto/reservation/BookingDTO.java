package za.co.carhire.dto.reservation;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Booking entity
 * Author: Lisakhanya Zumana - 230864821
 * Date: 08/10/2025
 */
public class BookingDTO implements Serializable {

    private int bookingID;
    private Integer userID;
    private String userName;
    private String userEmail;
    private List<Integer> carIDs;
    private List<CarInfo> cars;
    private LocalDateTime bookingDateAndTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer pickupLocationID;
    private String pickupLocationName;
    private String pickupLocationAddress;
    private Integer dropOffLocationID;
    private String dropOffLocationName;
    private String dropOffLocationAddress;
    private String bookingStatus;

    /**
     * Nested class for Car information in Booking
     */
    public static class CarInfo implements Serializable {
        private int carID;
        private String brand;
        private String model;
        private int year;
        private double rentalPrice;
        private String imageUrl;

        public CarInfo() {}

        public CarInfo(int carID, String brand, String model, int year, double rentalPrice, String imageUrl) {
            this.carID = carID;
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.rentalPrice = rentalPrice;
            this.imageUrl = imageUrl;
        }

        public int getCarID() {
            return carID;
        }

        public void setCarID(int carID) {
            this.carID = carID;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public double getRentalPrice() {
            return rentalPrice;
        }

        public void setRentalPrice(double rentalPrice) {
            this.rentalPrice = rentalPrice;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return "CarInfo{" +
                    "carID=" + carID +
                    ", brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    ", rentalPrice=" + rentalPrice +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }

    public BookingDTO() {
    }

    public BookingDTO(int bookingID, Integer userID, List<Integer> carIDs,
                      LocalDateTime bookingDateAndTime, LocalDateTime startDate,
                      LocalDateTime endDate, Integer pickupLocationID,
                      Integer dropOffLocationID, String bookingStatus) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.carIDs = carIDs;
        this.bookingDateAndTime = bookingDateAndTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocationID = pickupLocationID;
        this.dropOffLocationID = dropOffLocationID;
        this.bookingStatus = bookingStatus;
    }

    public static class Builder {
        private int bookingID;
        private Integer userID;
        private String userName;
        private String userEmail;
        private List<Integer> carIDs;
        private List<CarInfo> cars;
        private LocalDateTime bookingDateAndTime;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Integer pickupLocationID;
        private String pickupLocationName;
        private String pickupLocationAddress;
        private Integer dropOffLocationID;
        private String dropOffLocationName;
        private String dropOffLocationAddress;
        private String bookingStatus;

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

        public Builder setCarIDs(List<Integer> carIDs) {
            this.carIDs = carIDs;
            return this;
        }

        public Builder setCars(List<CarInfo> cars) {
            this.cars = cars;
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

        public Builder setPickupLocationAddress(String pickupLocationAddress) {
            this.pickupLocationAddress = pickupLocationAddress;
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

        public Builder setDropOffLocationAddress(String dropOffLocationAddress) {
            this.dropOffLocationAddress = dropOffLocationAddress;
            return this;
        }

        public Builder setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public BookingDTO build() {
            BookingDTO dto = new BookingDTO();
            dto.bookingID = this.bookingID;
            dto.userID = this.userID;
            dto.userName = this.userName;
            dto.userEmail = this.userEmail;
            dto.carIDs = this.carIDs;
            dto.cars = this.cars;
            dto.bookingDateAndTime = this.bookingDateAndTime;
            dto.startDate = this.startDate;
            dto.endDate = this.endDate;
            dto.pickupLocationID = this.pickupLocationID;
            dto.pickupLocationName = this.pickupLocationName;
            dto.pickupLocationAddress = this.pickupLocationAddress;
            dto.dropOffLocationID = this.dropOffLocationID;
            dto.dropOffLocationName = this.dropOffLocationName;
            dto.dropOffLocationAddress = this.dropOffLocationAddress;
            dto.bookingStatus = this.bookingStatus;
            return dto;
        }
    }

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

    public List<Integer> getCarIDs() {
        return carIDs;
    }

    public void setCarIDs(List<Integer> carIDs) {
        this.carIDs = carIDs;
    }

    public List<CarInfo> getCars() {
        return cars;
    }

    public void setCars(List<CarInfo> cars) {
        this.cars = cars;
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

    public String getPickupLocationAddress() {
        return pickupLocationAddress;
    }

    public void setPickupLocationAddress(String pickupLocationAddress) {
        this.pickupLocationAddress = pickupLocationAddress;
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

    public String getDropOffLocationAddress() {
        return dropOffLocationAddress;
    }

    public void setDropOffLocationAddress(String dropOffLocationAddress) {
        this.dropOffLocationAddress = dropOffLocationAddress;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", carIDs=" + carIDs +
                ", cars=" + cars +
                ", bookingDateAndTime=" + bookingDateAndTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupLocationID=" + pickupLocationID +
                ", pickupLocationName='" + pickupLocationName + '\'' +
                ", pickupLocationAddress='" + pickupLocationAddress + '\'' +
                ", dropOffLocationID=" + dropOffLocationID +
                ", dropOffLocationName='" + dropOffLocationName + '\'' +
                ", dropOffLocationAddress='" + dropOffLocationAddress + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
