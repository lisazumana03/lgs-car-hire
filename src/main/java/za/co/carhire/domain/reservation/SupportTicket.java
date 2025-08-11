package za.co.carhire.domain.reservation;

/*
Olwethu Tshingo - 222634383
Date: 30 2025
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import za.co.carhire.domain.authentication.User;

import java.io.Serializable;
import java.util.Date;

@Entity
public class SupportTicket implements Serializable {
    @Id
    protected int ticketID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
    protected String message;
    protected String status;
    protected int response;

    public SupportTicket() {
    }

    private SupportTicket(Builder builder) {
        this.ticketID = builder.ticketID;
        this.user = builder.user;
        this.message = builder.message;
        this.status = builder.status;
        this.response = builder.response;
    }

    public int getTicketID() {
        return ticketID;
    }

    public User getUser(){
        return user;
    }

    public String getMessage(){
        return message;
    }

    public String getStatus(){
        return status;
    }

    public int getResponse() {
        return response;
    }


    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketID=" + ticketID +
                ", userID=" + user +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", response=" + response +
                '}';
    }

    public static class Builder{
        protected int ticketID;
        protected User user;
        protected String message;
        protected String status;
        protected int response;

        public Builder setTicketID(int ticketID) {
            this.ticketID = ticketID;
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

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setResponse(int response) {
            this.response = response;
            return this;
        }

        public SupportTicket.Builder copy(SupportTicket support){
            this.ticketID = support.getTicketID();
            this.user = support.getUser();;
            this.message = support.getMessage();
            this.status = support.getStatus();
            this.response = support.getResponse();
            return this;
        }

        public SupportTicket build(){
            return new SupportTicket(this);
        }
    }

}
