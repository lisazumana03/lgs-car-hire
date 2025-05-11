package za.co.carhire.domain;
/* Notification.java

     Notification POJO class

     Author: Bonga Velem (220052379)

     Date: 11 May 2025 */

import java.util.Date;

public class Notification {

    private Integer notificationID;

    private Integer userID;

    private String message;

    private Date dateSent;

    private String status;

    public Notification() {
    }

    public Notification(Integer notificationID, Integer userID, String message, Date dateSent, String status) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.message = message;
        this.dateSent = dateSent;
        this.status = status;
    }

    public Integer getNotificationID() {
        return notificationID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", userID=" + userID +
                ", message='" + message + '\'' +
                ", dateSent=" + dateSent +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder{
        private Integer notificationID;

        private Integer userID;

        private String message;

        private Date dateSent;

        private String status;

        public Builder setNotificationID(Integer notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        public Builder setUserID(Integer userID) {
            this.userID = userID;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDateSent(Date dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        


    }
}
