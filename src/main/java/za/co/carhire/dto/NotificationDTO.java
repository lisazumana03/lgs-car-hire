package za.co.carhire.dto;
/* NotificationDTO.java

     dto/NotificationDTO class

     Author: Bonga Velem

     Student Number: 220052379

     */
import java.time.LocalDate;

public class NotificationDTO {

    private Integer notificationID;
    private String message;
    private LocalDate dateSent;
    private String status;
    private Integer userId;
    private String userName;

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