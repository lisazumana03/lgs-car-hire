package za.co.carhire.dto.reservation;

import za.co.carhire.domain.reservation.BookingStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UpdateBookingDTO implements Serializable {
    private int bookingID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus bookingStatus;

    public UpdateBookingDTO() {
    }

    public UpdateBookingDTO(int bookingID, LocalDateTime startDate, LocalDateTime endDate, BookingStatus bookingStatus) {
        this.bookingID = bookingID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
    }

    // Getters and Setters
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }

    @Override
    public String toString() {
        return "UpdateBookingDTO{" +
                "bookingID=" + bookingID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}