package za.co.carhire.domain.reservation;

/*
Notification.java
Notification domain/authentication class
Author: Bonga Velem
Student Number: 220052379
 */

import jakarta.persistence.*;
import za.co.carhire.domain.authentication.User;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    private boolean readStatus;

    private LocalDateTime dateSent;

    public Notification() {
    }

    public Notification(Builder builder) {
        this.notificationID = builder.notificationID;
        this.user = builder.user;
        this.message = builder.message;
        this.readStatus = builder.readStatus;
        this.dateSent = builder.dateSent;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", readStatus=" + readStatus +
                ", dateSent=" + dateSent +
                '}';
    }

    public static class Builder {
        private int notificationID;

        private User user;

        private String message;

        private boolean readStatus;

        private LocalDateTime dateSent;

        public Builder setNotificationID(int notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setReadStatus(boolean readStatus) {
            this.readStatus = readStatus;
            return this;
        }

        public Builder setDateSent(LocalDateTime dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public Builder copy(Notification notification) {
            this.notificationID = notification.notificationID;
            this.user = notification.user;
            this.message = notification.message;
            this.readStatus = notification.readStatus;
            this.dateSent = notification.dateSent;
            return this;

        }

        public Notification build() {
            return new Notification(this);
        }
    }

}