package za.co.carhire.domain.reservation;
/* Notification.java

     Notification POJO class

     Author: Bonga Velem (220052379)

     Date: 11 May 2025 */

import jakarta.persistence.*;
import za.co.carhire.domain.authentication.User;

import java.time.LocalDate;
@Entity
@Table(name = "Notifications")
public class Notification {

    @Id
    private Integer notificationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;


    @Column(nullable = false)
    private LocalDate dateSent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    public Notification() {
    }

    public Notification(Builder builder) {
        this.notificationID = builder.notificationID;
        this.user = builder.user;
        this.message = builder.message;
        this.dateSent = builder.dateSent;
        this.status = builder.status;
    }

    public Integer getNotificationID() {
        return notificationID;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDateSent() {
        return dateSent;
    }

    public BookingStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", userID=" + user +
                ", message='" + message + '\'' +
                ", dateSent=" + dateSent +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder{
        private Integer notificationID;

        private User user;;

        private String message;

        private LocalDate dateSent;

        private BookingStatus status;

        public Builder setNotificationID(Integer notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        public Builder setUserID(Integer user) {
            this.user= user;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDateSent(LocalDate dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public Builder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Builder copy(Notification notification){
            this.notificationID = notification.notificationID;
            this.user = notification.user;
            this.message = notification.message;
            this.dateSent = notification.dateSent;
            this.status = notification.status;
            return this;

        }

        public Notification build(){
            return new Notification(this);
        }


    }
}
