package za.co.carhire.domain.reservation;

/*
Olwethu Tshingo - 222634383
Date: 11 May 2025
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class SupportTicket implements Serializable {
    @Id
    protected int ticketID;
    protected int userID;
    protected String message;
    protected Date createdAt;
    protected String status;
    protected int response;

    public SupportTicket() {
    }

    private SupportTicket(Builder builder) {
        this.ticketID = builder.ticketID;
        this.userID = builder.userID;
        this.message = builder.message;
        this.createdAt = builder.createdAt;
        this.status = builder.status;
        this.response = builder.response;
    }

    public int getTicketID() {
        return ticketID;
    }

    public int getUserID(){
        return userID;
    }

    public String getMessage(){
        return message;
    }

    public Date getCreatedAT() {
        return createdAt;
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
                ", userID=" + userID +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", response=" + response +
                '}';
    }

    public static class Builder{
        protected int ticketID;
        protected int userID;
        protected  String message;
        protected Date createdAt;
        protected String status;
        protected int response;

        public Builder setTicketID(int ticketID) {
            this.ticketID = ticketID;
            return this;
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
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
            this.userID = support.getUserID();;
            this.message = support.getMessage();
            this.createdAt = support.getCreatedAT();
            this.status = support.getStatus();
            this.response = support.getResponse();
            return this;
        }

        public SupportTicket build(){
            return new SupportTicket(this);
        }
    }

}
