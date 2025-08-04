package za.co.carhire.dto;

import java.time.LocalDate;

/**
 * Guys i kept on getting errors for the NotificationController, because of the object User
 * so thats why i had to create DTO's and mappers for them.
 * Data Transfer Object (DTO) for Notification
 * 
 * Purpose:
 * - Transfers notification data between the API layer and client applications
 * - Prevents circular reference issues that occur when serializing domain
 * objects
 * - Provides a clean, flat structure for JSON responses
 * - Separates API contract from internal domain model

 * Usage:
 * - Used in REST controllers for request/response bodies
 * - Converted to/from domain objects using NotificationMapper
 * - Serialized to JSON for API responses
 * 
 * Author: Bonga Velem (220052379)
 * Date: 18 May 2025
 */
public class NotificationDTO {

    // Primary key - unique identifier for the notification
    private Integer notificationID;

    // The actual notification message/content
    private String message;

    // When the notification was sent (date)
    private LocalDate dateSent;

    // Current status of the notification (e.g., "BOOKED", "CANCELLED", "PENDING")
    private String status;

    // Reference to the user who owns this notification (nullable)
    private Integer userId;

    // User's name for convenience (nullable) - provides better UX
    private String userName;

    /**
     * Default constructor required for JSON deserialization
     * Spring Boot uses this to create objects from JSON requests
     */
    public NotificationDTO() {
    }



    public NotificationDTO(Integer notificationID, String message, LocalDate dateSent,
            String status, Integer userId, String userName) {
        this.notificationID = notificationID;
        this.message = message;
        this.dateSent = dateSent;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
    }

    //  GETTERS AND SETTERS
    // These are required for JSON serialization/deserialization
    public Integer getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * String representation of the DTO for debugging and logging
     * 
     * @return Formatted string with all DTO fields
     */
    @Override
    public String toString() {
        return "NotificationDTO{" +
                "notificationID=" + notificationID +
                ", message='" + message + '\'' +
                ", dateSent=" + dateSent +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}