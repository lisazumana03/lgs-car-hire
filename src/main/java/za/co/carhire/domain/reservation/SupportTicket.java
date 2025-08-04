package za.co.carhire.domain.reservation;

/*
Olwethu Tshingo - 222634383
Date: 11 May 2025
 */

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "support_ticket")
public class SupportTicket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int ticketID;
    
    @Column(name = "user_id")
    protected int userID;
    
    @Column(name = "message")
    protected String message;
    
    @Column(name = "created_at")
    protected Date createdAt;
    
    @Column(name = "status")
    protected String status;
    
    @Column(name = "response")
    protected int response;

    private SupportTicket() {
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

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAT() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
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
        protected String message;
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

        public Builder setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
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

        public Builder copy(SupportTicket supportTicket) {
            this.ticketID = supportTicket.getTicketID();
            this.userID = supportTicket.getUserID();
            this.message = supportTicket.getMessage();
            this.createdAt = supportTicket.getCreatedAT();
            this.status = supportTicket.getStatus();
            this.response = supportTicket.getResponse();
            return this;
        }

        public SupportTicket build() {
            return new SupportTicket(this);
        }
    }
}
