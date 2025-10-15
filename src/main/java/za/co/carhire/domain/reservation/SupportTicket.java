package za.co.carhire.domain.reservation;

/*
Olwethu Tshingo - 222634383
Date: 30 2025
 */

import jakarta.persistence.*;
import za.co.carhire.domain.authentication.User;

import java.io.Serializable;

@Entity
public class SupportTicket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int ticketID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
    protected String subject;
    protected String description;

    public SupportTicket() {
    }

    private SupportTicket(Builder builder) {
        this.ticketID = builder.ticketID;
        this.user = builder.user;
        this.subject = builder.subject;
        this.description = builder.description;
    }

    public int getTicketID() {
        return ticketID;
    }

    public User getUser(){
        return user;
    }

    public String getSubject(){
        return subject;
    }

    public String getDescription(){
        return description;
    }


    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketID=" + ticketID +
                ", userID=" + user +
                ", subject='" + subject + '\'' +
                ", description='" + description +
                '}';
    }

    public static class Builder{
        protected int ticketID;
        protected User user;
        protected String subject;
        protected String description;

        public Builder setTicketID(int ticketID) {
            this.ticketID = ticketID;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public SupportTicket.Builder copy(SupportTicket support){
            this.ticketID = support.getTicketID();
            this.user = support.getUser();;
            this.subject = support.getSubject();
            this.description = support.getDescription();
            return this;
        }

        public SupportTicket build(){
            return new SupportTicket(this);
        }
    }

}
